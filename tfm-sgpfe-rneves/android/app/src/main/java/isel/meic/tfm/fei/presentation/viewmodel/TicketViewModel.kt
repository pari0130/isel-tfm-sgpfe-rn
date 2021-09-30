package isel.meic.tfm.fei.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import com.google.gson.Gson
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.model.QueueState
import isel.meic.tfm.fei.model.Ticket
import isel.meic.tfm.fei.service.LiveUpdateService
import isel.meic.tfm.fei.utils.Constants

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - October 2019
 * @mentor: Paulo Pereira
 *
 * <Description>
 *     TODO read this to remove the callback and use only live data and normal requests
 * https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54
 * https://developer.android.com/codelabs/kotlin-android-training-live-data-transformations#4
 * //TODO cleanup
 */
class TicketViewModel(private val application: FEIApplication, private val repo: FeiRepository) :
    AndroidViewModel(application) {

    companion object {
        const val TAKEN_TICKET = "taken_ticket"
    }

    val tickets: MutableLiveData<List<Ticket>> = MutableLiveData()
    val queuesState: MutableLiveData<List<QueueState>> = MutableLiveData()
    val queueState: MutableLiveData<QueueState> = MutableLiveData()

    val onError: MutableLiveData<VolleyError> = MutableLiveData()

    fun updateTickets(postOfficeId: Int, token: String?) {
        repo.getPostOfficeQueuesStates(
            postOfficeId,
            token,
            { queuesState.value = it },
            { onError(it) })
    }

    fun getQueueStateForList(): LiveData<QueueState> {
        return repo.getQueueStateForList()
    }

    //THIS IS TEMPORARY - MARTELADA - TODO FIX IT
    fun addToQueueState(queueState: QueueState) {
        val newList = ArrayList<QueueState>()
        val list = queuesState.value ?: ArrayList()
        for (item in list) {
            if (item.queueId == queueState.queueId) {
                newList.add(queueState)
            } else
                newList.add(item)
        }
        queuesState.value = newList
    }

    private fun onError(error: VolleyError) {
        //todo
        Toast.makeText(getApplication(), "TicketViewModel - Error!", Toast.LENGTH_LONG).show()
        onError.postValue(error)
    }

    fun takeTicket(ticketId: Int, userId: Int, token: String?) {
        repo.takeTicket(ticketId, userId, token, { queueState.value = it }, { onError(it) })
    }

    //i don't need the result for now
    fun subscribeForUpdates(ticketId: Int, userId: Int, token: String?) {
        repo.subscribeForUpdates(ticketId, userId, token, {}, { onError(it) })
    }

    fun subscribeForPostOfficeTicketsUpdates(postOfficeId: Int, userId: Int, token: String?) {
        repo.subscribeForPostOfficeTicketsUpdates(postOfficeId, userId, token, {}, { onError(it) })
    }

    fun unsubscribePostOfficeTicketsUpdates(postOfficeId: Int, userId: Int, token: String?) {
        repo.unsubscribePostOfficeTicketsUpdates(postOfficeId, userId, token, {}, {onError(it)})
    }

    override fun onCleared() {
        //TODO remove subscription
        super.onCleared()
    }
}