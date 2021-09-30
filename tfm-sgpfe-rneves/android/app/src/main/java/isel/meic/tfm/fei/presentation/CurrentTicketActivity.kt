package isel.meic.tfm.fei.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.QueueState
import isel.meic.tfm.fei.model.ServicePostOffice
import isel.meic.tfm.fei.presentation.viewmodel.TicketViewModel
import isel.meic.tfm.fei.service.LiveUpdateService
import isel.meic.tfm.fei.subscribe.SubscriptionKeys
import isel.meic.tfm.fei.subscribe.SubscriptionManager
import kotlinx.android.synthetic.main.activity_current_ticket.*

//TODO cleanup - change name
class CurrentTicketActivity : TicketBaseActivity() {

    override val layoutResId: Int = R.layout.activity_current_ticket

    private lateinit var model: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val ticket = intent.extras?.getSerializable(TicketsActivity.CURRENT_TICKET) as QueueState
        val postOffice =
            intent.extras?.getSerializable(TicketsActivity.POST_OFFICE) as ServicePostOffice //todo remove this

        model =
            ViewModelProvider(this, getViewModelFactory((application as FEIApplication).repo))
                .get(TicketViewModel::class.java)

        postOfficeService.text = ticket.name //todo change postOfficeService to queueName
        lineState.text = ticket.stateNumber.toString()
        beingAttended.text = ticket.attendedNumber.toString()

        val hour = ticket.forecast.div(60)
        val minutes = ticket.forecast.rem(60)
        forecast.text = if (hour > 0) "${hour}hr:${minutes}min" else "$minutes minutes"

        /* model.queueState.observe(this){
             lineState.text = it.stateNumber.toString()
             beingAttended.text = it.attendedNumber.toString()
         }*/

        model.queueState.observe(this, {
            val intent = Intent(this, LiveUpdateService::class.java)
            intent.action = LiveUpdateService.START_FOREGROUND_SERVICE
            intent.putExtra(LiveUpdateService.QUEUE_STATE_EXTRA, it)
            startService(intent)
        })

        var ticketTaken = false

        //TODO fix this
        takeTicket.setOnClickListener {
            ticketTaken = true
            model.tickets.removeObservers(this)
            //model.ticketT.removeObservers(this)
            model.takeTicket(
                ticket.queueId,
                (application as FEIApplication).getLoggedInUser()?.userId ?: -1,
                (application as FEIApplication).getLoggedInUser()?.token
            )
            //todo make this right. only for testing purposes
            lineStateLabel.text = "Your ticket!"
            //todo when queueState is updated change the views
            takeTicket.isEnabled =
                false //in case of error enable it again. todo pass a callback to the takeTicket
        }

        model.getQueueStateForList().observe(this, {
            if (ticket.queueId == it.queueId)
                lineState.text = it.stateNumber.toString()
            beingAttended.text = it.attendedNumber.toString()
            //todo if taken, "your ticket" can't change
        })

        SubscriptionManager.getInstance(application as FEIApplication)
            .addSubscription(SubscriptionKeys.POST_OFFICE_SUBSCRIPTION)
    }

    override fun onStop() {
        super.onStop()
        //todo call unsubscribe
    }

    override fun onSupportNavigateUp(): Boolean {
        //if () from notification or app started (ticket taken)
        //startActivity(Intent(this, ServicesActivity::class.java))
        //finish()

        onBackPressed()
        return true
    }
}
