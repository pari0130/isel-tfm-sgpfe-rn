package isel.meic.tfm.fei.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.ServicePostOffice
import isel.meic.tfm.fei.presentation.viewmodel.ServiceViewModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */

class ServicePostOfficeViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {

    private val postOfficeDescription: TextView = view.findViewById(R.id.service_postoffice_description)

    fun bindTo(
        servicePostOffice: ServicePostOffice?,
        onClickListener: (ServicePostOffice?) -> Unit
    ) {
        postOfficeDescription.text = servicePostOffice?.description

        //todo add logic to avoid multiple clicks
        itemView.setOnClickListener {
            onClickListener(servicePostOffice)
        }
    }
}

class ServicePostOfficesAdapter(
    private val viewModel: ServiceViewModel,
    private val onClickListener: (ServicePostOffice?) -> Unit
) : RecyclerView.Adapter<ServicePostOfficeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicePostOfficeViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(
            R.layout.service_postoffice_card_item,
            parent,
            false
        ) as ViewGroup
        return ServicePostOfficeViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ServicePostOfficeViewHolder, position: Int) {
        holder.bindTo(viewModel.servicePostOffices.value?.get(position), onClickListener)
    }

    override fun getItemCount(): Int = viewModel.servicePostOffices.value?.size ?: 0
}