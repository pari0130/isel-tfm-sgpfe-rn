package isel.meic.tfm.fei.model

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import com.google.gson.Gson
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.contract.IServiceController
import isel.meic.tfm.fei.contract.ITicketController
import isel.meic.tfm.fei.contract.IUserController
import isel.meic.tfm.fei.controller.ServiceController
import isel.meic.tfm.fei.controller.TicketController
import isel.meic.tfm.fei.controller.UserController
import isel.meic.tfm.fei.inputmodel.SuccessInputModel
import isel.meic.tfm.fei.inputmodel.queue.QueueStateInputModel
import isel.meic.tfm.fei.inputmodel.service.ServicePostOfficesInputModel
import isel.meic.tfm.fei.inputmodel.service.ServicesInputModel
import isel.meic.tfm.fei.inputmodel.ticket.TicketInputModel
import isel.meic.tfm.fei.inputmodel.ticket.TicketsInputModel
import isel.meic.tfm.fei.inputmodel.user.RegisterInputModel
import isel.meic.tfm.fei.inputmodel.user.UserInformationInputModel
import isel.meic.tfm.fei.presentation.viewmodel.TicketViewModel
import isel.meic.tfm.fei.utils.Constants
import isel.meic.tfm.fei.webapi.ServiceApi
import isel.meic.tfm.fei.webapi.TicketApi
import isel.meic.tfm.fei.webapi.UserApi

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 * //TODO make my own generic AsyncTask so i can stop repeating the creation all the time
 * //TODO STOP using AsyncTask due to the fact that it has a memory leak problem
 * //TODO remove the db saving, i don't need it for the tickets
 */
class FeiRepository(
    private val application: FEIApplication,
    private val db: FeiDatabase
) {

    private val ticketController: ITicketController = TicketController(TicketApi(application))
    private val serviceController: IServiceController = ServiceController(ServiceApi(application))
    private val userController: IUserController = UserController(UserApi(application))

    private fun saveTicketToDatabase(
        result: TicketsInputModel,
        onCompletion: (List<Ticket>) -> Unit
    ) {
        val ticketsResult = result.properties.tickets.toList()
        val toCreate = ticketsResult.map {
            Ticket(
                it.ticketDto.ticketId,
                it.ticketDto.ticketLetter,
                it.ticketDto.ticketNumber,
                it.ticketDto.queueId,
                it.ticketDto.name
            )
        }
        /*
        val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?): Unit =
                db.ticketDAO().insertAll(*toCreate.toTypedArray())
            //override fun onPostExecute(result: Unit) { onCompletion(toCreate) }
        }
        task.execute()*/
        onCompletion(toCreate)
    }

    fun getTickets(
        postOfficeId: Int,
        token: String?,
        success: (List<Ticket>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketController.getServiceTickets(
            postOfficeId,
            null,
            token,
            { saveTicketToDatabase(it, success) },
            onError
        )
    }

    fun getPostOfficeQueuesStates(
        postOfficeId: Int,
        token: String?,
        success: (List<QueueState>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketController.getPostOfficeQueuesStates(
            postOfficeId,
            null,
            token,
            {
                val queuesStates = it.properties.queueStates.toList()
                success(queuesStates.map { qs ->
                    QueueState(
                        qs.queueDto.queueId,
                        qs.queueDto.stateNumber,
                        qs.queueDto.attendedNumber,
                        qs.queueDto.letter,
                        qs.queueDto.name,
                        qs.queueDto.forecast
                    )
                })
            },
            onError
        )
    }

    fun getTicketById(ticketId: Int, success: (Ticket) -> Unit, onError: (VolleyError) -> Unit) {
        val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Ticket>() {
            override fun doInBackground(vararg params: Unit?): Ticket =
                db.ticketDAO().findById(ticketId)

            override fun onPostExecute(result: Ticket) {
                success(result)
            }
        }
        task.execute()
    }

    private fun saveCurrentTicketState(
        result: TicketInputModel,
        onCompletion: (TicketAttended) -> Unit
    ) {
        val ticket = result.properties
        val ticketAttended = TicketAttended(
            ticket.ticketId,
            ticket.ticketLetter,
            ticket.ticketNumber,
            ticket.queueId,
            ticket.name
        )
        /*val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                db.ticketAttendedDAO().insertAll(ticketAttended)
            }
        }
        task.execute()*/
        onCompletion(ticketAttended)
    }

    fun getCurrentTicketState(
        queueId: Int,
        token: String?,
        success: (TicketAttended) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketController.getCurrentTicketState(
            queueId,
            null,
            token,
            { saveCurrentTicketState(it, success) },
            onError
        )
    }

    private fun saveTakenTicket(result: QueueStateInputModel, onCompletion: (QueueState) -> Unit) {
        val ticket = QueueState(
            result.properties.queueId,
            result.properties.stateNumber,
            result.properties.attendedNumber,
            result.properties.letter,
            result.properties.name,
            result.properties.forecast
        )

        val gson = Gson()
        val sharedPreferences =
            application.getSharedPreferences(Constants.FEI_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(TicketViewModel.TAKEN_TICKET, gson.toJson(ticket))
        editor.apply()
        onCompletion(ticket)
    }

    fun takeTicket(
        ticketId: Int,
        userId: Int,
        token: String?,
        success: (QueueState) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketController.takeTicket(
            ticketId,
            userId,
            null,
            token,
            { saveTakenTicket(it, success) },
            onError
        )
    }

    fun subscribeForUpdates(
        ticketId: Int,
        userId: Int,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketController.subscribeForUpdates(ticketId, userId, null, token, success, onError)
    }

    fun subscribeForPostOfficeTicketsUpdates(
        postOfficeId: Int,
        userId: Int,
        token: String?,
        success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketController.subscribeForPostOfficeTicketsUpdates(
            postOfficeId,
            userId,
            null,
            token,
            success,
            onError
        )
    }

    fun unsubscribePostOfficeTicketsUpdates(
        postOfficeId: Int, userId: Int, token: String?, success: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        ticketController.unsubscribeForPostOfficeTicketsUpdates(
            postOfficeId,
            userId,
            null,
            token,
            success,
            onError
        )
    }

    fun updateTicket(ticket: Ticket) {
        /*val task = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                //if (db.ticketDAO().update(ticket) != 0)
                    ticketLD.postValue(ticket)
            }
        }*/
        ticketLD.postValue(ticket)
        //task.execute()
    }

    fun updateTicket(ticket: TicketAttended) {
        //val task = @SuppressLint("StaticFieldLeak")
        /*object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                if (db.ticketAttendedDAO().update(ticket) != 0)
                    ticketAttendedLD.postValue(ticket)
            }
        }
        task.execute()*/
        ticketAttendedLD.postValue(ticket)
    }

    fun updateTicketForList(ticket: Ticket) {
        ticketForList.postValue(ticket)
    }

    fun updateQueueStateForList(queueState: QueueState) {
        queueStateForList.postValue(queueState)
    }

    private val ticketLD: MutableLiveData<Ticket> = MutableLiveData()
    private val ticketAttendedLD: MutableLiveData<TicketAttended> = MutableLiveData()
    private val ticketForList: MutableLiveData<Ticket> = MutableLiveData()

    private val queueStateForList: MutableLiveData<QueueState> = MutableLiveData()

    fun getTicket(): LiveData<Ticket> {
        return ticketLD
    }

    fun getTicketAttended(): LiveData<TicketAttended> {
        return ticketAttendedLD
    }

    fun getTicketForList(): LiveData<Ticket> {
        return ticketForList
    }

    fun getQueueStateForList(): LiveData<QueueState> {
        return queueStateForList
    }

    fun getUserInformation(
        token: String, success: (UserInformationInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userController.getUserInformation(token, null, success, onError)
    }

    fun sendUserInformation(
        id: Int,
        name: String,
        phone: Int,
        notification: Boolean,
        token: String,
        success: (SuccessInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userController.sendUserInformation(
            id,
            name,
            phone,
            notification,
            token,
            null,
            success,
            onError
        )
    }

    fun register(
        username: String,
        password: String,
        name: String,
        email: String,
        phone: Int,
        notification: Boolean,
        success: (RegisterInputModel) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        userController.register(
            username,
            password,
            name,
            email,
            phone,
            notification,
            null,
            success,
            onError
        )
    }

    /***************************************** Services *****************************************/

    private val servicesLiveData: MutableLiveData<Collection<Service>> = MutableLiveData()

    fun getServices(
        token: String?,
        success: (List<Service>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        serviceController.getServices(null, token, { processServicesResult(it, success) }, onError)
    }

    private fun processServicesResult(
        result: ServicesInputModel,
        onCompletion: (List<Service>) -> Unit
    ) {
        val servicesInputModelResult = result.properties.services.toList()
        val services = servicesInputModelResult.map {
            Service(
                it.serviceDto.id,
                it.serviceDto.name,
                it.serviceDto.description
            )
        }
        onCompletion(services)
    }

    fun getServicePostOffice(
        serviceId: Int,
        token: String?,
        success: (List<ServicePostOffice>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        serviceController.getServicePostOffices(
            serviceId,
            null,
            token,
            { processServicePostOfficeResult(it, success) },
            onError
        )
    }

    private fun processServicePostOfficeResult(
        result: ServicePostOfficesInputModel,
        onCompletion: (List<ServicePostOffice>) -> Unit
    ) {
        val servicePostOfficeInputModelResult = result.properties.servicePostOffices.toList()
        val servicePostOffices = servicePostOfficeInputModelResult.map {
            ServicePostOffice(
                it.servicePostOffices.id,
                it.servicePostOffices.latitude,
                it.servicePostOffices.longitude,
                it.servicePostOffices.description,
                it.servicePostOffices.serviceId,
                it.servicePostOffices.address
            )
        }
        onCompletion(servicePostOffices)
    }
}
