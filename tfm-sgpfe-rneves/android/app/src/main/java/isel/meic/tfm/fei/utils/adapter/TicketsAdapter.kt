package isel.meic.tfm.fei.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.QueueState
import isel.meic.tfm.fei.presentation.viewmodel.TicketViewModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class TicketViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val ticketIdView: TextView = view.findViewById(R.id.ticketId)
    private val ticketForecastView: TextView = view.findViewById(R.id.ticketForecast)
    private val ticketStateView: TextView = view.findViewById(R.id.ticketState)
    private val ticketAttendedView: TextView = view.findViewById(R.id.ticketBAttended)
    private val ticketIcon: TextView = view.findViewById(R.id.ticketIcon)

    fun bindTo(ticket: QueueState?, onClickListener: (QueueState?) -> Unit) {
        ticketIcon.text = ticket?.letter
        ticketIdView.text = ticket?.name
        ticketStateView.text = itemView.resources.getString(R.string.line, ticket?.stateNumber)
        ticketAttendedView.text =
            itemView.resources.getString(R.string.at_service_desk, ticket?.attendedNumber)
        val hour = ticket?.forecast?.div(60) ?: 0
        val minutes = ticket?.forecast?.rem(60) ?: 0
        ticketForecastView.text = if (hour > 0) "${hour}hr:${minutes}min" else "$minutes minutes"

        //todo add logic to avoid multiple clicks
        itemView.setOnClickListener {
            onClickListener(ticket)
        }
    }
}

class TicketsAdapter(
    private val viewModel: TicketViewModel,
    private val onClickListener: (QueueState?) -> Unit
) : RecyclerView.Adapter<TicketViewHolder>() {

    override fun getItemCount(): Int = viewModel.queuesState.value?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder =
        TicketViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.ticket_item, parent, false) as ViewGroup
        )

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bindTo(viewModel.queuesState.value?.get(position), onClickListener)
    }
}