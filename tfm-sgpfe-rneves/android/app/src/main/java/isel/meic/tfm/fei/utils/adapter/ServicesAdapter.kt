package isel.meic.tfm.fei.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.Service
import isel.meic.tfm.fei.presentation.viewmodel.ServiceViewModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - August 2020
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServiceViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val serviceDesignation: TextView = view.findViewById(R.id.service_title)

    fun bindTo(service: Service?, onClickListener: (Service?) -> Unit) {
        serviceDesignation.text = service?.name

        //todo add logic to avoid multiple clicks
        itemView.setOnClickListener {
            onClickListener(service)
        }
    }
}

class ServicesAdapter(
    private val viewModel: ServiceViewModel,
    private val onClickListener: (Service?) -> Unit
) : RecyclerView.Adapter<ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(
            R.layout.service_card_item,
            parent,
            false
        ) as ViewGroup
        return ServiceViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bindTo(viewModel.services.value?.get(position), onClickListener)
    }

    override fun getItemCount(): Int = viewModel.services.value?.size ?: 0
}