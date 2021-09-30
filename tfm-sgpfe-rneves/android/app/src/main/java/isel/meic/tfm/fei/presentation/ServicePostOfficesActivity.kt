package isel.meic.tfm.fei.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.model.Service
import isel.meic.tfm.fei.model.ServicePostOffice
import isel.meic.tfm.fei.utils.adapter.ServicePostOfficesAdapter
import isel.meic.tfm.fei.presentation.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.activity_service_postoffices.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServicePostOfficesActivity : BaseActivity() {

    companion object {
        const val SERVICE_POST_OFFICE = "service_post_office"
    }

    override val layoutResId: Int = R.layout.activity_service_postoffices

    private var viewModelFactory: ViewModelProvider.Factory? = null

    private fun getViewModelFactory(repo: FeiRepository): ViewModelProvider.Factory {
        if (viewModelFactory == null) {
            viewModelFactory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ServiceViewModel(
                        this@ServicePostOfficesActivity.application as FEIApplication,
                        repo
                    ) as T
                }
            }
        }
        return viewModelFactory!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val intent = intent
        val service = intent.extras?.getSerializable(ServicesActivity.SERVICE) as Service

        val model =
            ViewModelProvider(this, getViewModelFactory((application as FEIApplication).repo))
                .get(ServiceViewModel::class.java)

        model.getServicePostOffices(
            service.id,
            (application as FEIApplication).getLoggedInUser()?.token
        )

        model.servicePostOffices.observe(this, {
            service_postoffice_grid.setHasFixedSize(true)
            service_postoffice_grid.layoutManager =
                GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
            val adapter = ServicePostOfficesAdapter(model) { onClickListener(it) }
            service_postoffice_grid.adapter = adapter

            service_postoffice_grid.addItemDecoration(
                ServiceGridItemDecoration(
                    16,
                    4
                )
            )
            loading.visibility = View.GONE
            container.visibility = View.VISIBLE
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onClickListener(servicePostOffice: ServicePostOffice?) {
        val intent = Intent(this, TicketsActivity::class.java)
        intent.putExtra(SERVICE_POST_OFFICE, servicePostOffice)
        startActivity(intent)
    }
}