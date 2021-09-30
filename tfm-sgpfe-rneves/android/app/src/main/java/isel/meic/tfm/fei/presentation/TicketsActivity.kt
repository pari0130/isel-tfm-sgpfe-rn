package isel.meic.tfm.fei.presentation

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.QueueState
import isel.meic.tfm.fei.model.ServicePostOffice
import isel.meic.tfm.fei.subscribe.SubscriptionManager
import isel.meic.tfm.fei.subscribe.SubscriptionKeys
import isel.meic.tfm.fei.utils.adapter.TicketsAdapter
import isel.meic.tfm.fei.presentation.viewmodel.TicketViewModel
import kotlinx.android.synthetic.main.activity_tickets.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 */
class TicketsActivity : TicketBaseActivity() {

    override val layoutResId: Int = R.layout.activity_tickets

    private lateinit var model: TicketViewModel

    private lateinit var servicePostOffice : ServicePostOffice

    companion object {
        const val CURRENT_TICKET = "current_ticket"
        const val POST_OFFICE = "post_office"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val intent = intent
        servicePostOffice =
            intent.extras?.getSerializable(ServicePostOfficesActivity.SERVICE_POST_OFFICE) as ServicePostOffice

        ticketsView.setHasFixedSize(true)
        ticketsView.layoutManager = LinearLayoutManager(this)

        ticketsView.addItemDecoration(SpacesItemDecoration(15))

        model =
            ViewModelProvider(this, getViewModelFactory((application as FEIApplication).repo))
                .get(TicketViewModel::class.java)

        ticketsView.adapter = TicketsAdapter(model) { onClickListener(it, servicePostOffice) }

        model.updateTickets(
            servicePostOffice.id,
            (application as FEIApplication).getLoggedInUser()?.token
        )
        //TODO add get tickets by postoffice


        swipeContainer.setOnRefreshListener {
            model.updateTickets(
                servicePostOffice.id,
                (application as FEIApplication).getLoggedInUser()?.token
            )
        }

        /*model.tickets.observe(this, Observer {
            ticketsView.adapter = TicketsAdapter(model) { onClickListener(it, servicePostOffice) }
            swipeContainer.isRefreshing = false
        })*/

        model.queuesState.observe(this, {
            ticketsView.adapter = TicketsAdapter(model) { onClickListener(it, servicePostOffice) }
            swipeContainer.isRefreshing = false
            loading.visibility = View.GONE
            swipeContainer.visibility = View.VISIBLE
        })

        //THIS IS TEMPORARY - MARTELADA TODO FIX
        model.getQueueStateForList().observe(this, {
            model.addToQueueState(it)
        })

        model.subscribeForPostOfficeTicketsUpdates(
            servicePostOffice.id,
            (application as FEIApplication).getLoggedInUser()?.userId!!,
            (application as FEIApplication).getLoggedInUser()?.token
        )
        SubscriptionManager.getInstance(application as FEIApplication)
            .addSubscription(SubscriptionKeys.POST_OFFICE_SUBSCRIPTION)

        model.onError.observe(this, {
            swipeContainer.isRefreshing = false
        })
    }

    override fun onStop() {
        model.subscribeForPostOfficeTicketsUpdates(
            servicePostOffice.id,
            (application as FEIApplication).getLoggedInUser()?.userId!!,
            (application as FEIApplication).getLoggedInUser()?.token
        )
        super.onStop()
    }

    private fun onClickListener(queueState: QueueState?, postOffice: ServicePostOffice) {
        val intent = Intent(this, CurrentTicketActivity::class.java)
        intent.putExtra(CURRENT_TICKET, queueState)
        intent.putExtra(POST_OFFICE, postOffice)

        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class SpacesItemDecoration(private val space: Int) : ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space

            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space
            }
        }
    }
}

