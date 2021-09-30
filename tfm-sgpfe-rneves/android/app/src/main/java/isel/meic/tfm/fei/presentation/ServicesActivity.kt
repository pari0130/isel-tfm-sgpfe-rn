package isel.meic.tfm.fei.presentation

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.location.FeiLocationManager
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.model.Service
import isel.meic.tfm.fei.utils.adapter.ServicesAdapter
import isel.meic.tfm.fei.presentation.viewmodel.ServiceViewModel
import kotlinx.android.synthetic.main.activity_services.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class ServicesActivity : BaseActivity() {

    companion object {
        const val SERVICE = "service"
    }

    override val layoutResId: Int = R.layout.activity_services

    private var viewModelFactory: ViewModelProvider.Factory? = null

    private fun getViewModelFactory(repo: FeiRepository): ViewModelProvider.Factory {
        if (viewModelFactory == null) {
            viewModelFactory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return ServiceViewModel(
                        this@ServicesActivity.application as FEIApplication,
                        repo
                    ) as T
                }
            }
        }
        return viewModelFactory!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //TODO send it to the server
            FeiLocationManager.init(this) //this is TEMPORARY
            FeiLocationManager.getCurrentLocation(this) { location ->
                Log.d(
                    "Services Pomps",
                    "POMPS The location: ${location?.latitude} ${location?.longitude}"
                )
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)

        val model =
            ViewModelProvider(this, getViewModelFactory((application as FEIApplication).repo))
                .get(ServiceViewModel::class.java)

        //todo delete - for testing only
        //model.addToService()
        model.getServices((application as FEIApplication).getLoggedInUser()?.token)

        model.services.observe(this, {
            services_grid.setHasFixedSize(true)
            val spanCount = 2
            services_grid.layoutManager =
                GridLayoutManager(this, spanCount, RecyclerView.VERTICAL, false)
            val adapter = ServicesAdapter(model) { onClickListener(it) }
            services_grid.adapter = adapter
            services_grid.addItemDecoration(ServiceGridItemDecoration(16, 4))
            loading.visibility = View.GONE
            container.visibility = View.VISIBLE
        })
    }

    private fun onClickListener(service: Service?) {
        val intent = Intent(this, ServicePostOfficesActivity::class.java)
        intent.putExtra(SERVICE, service)
        startActivity(intent)
    }
}

class ServiceGridItemDecoration(
    private val largePadding: Int,
    private val smallPadding: Int/*, private val spanCount : Int, private val spacing : Int*/
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = smallPadding
        outRect.right = smallPadding
        outRect.top = largePadding
        outRect.bottom = largePadding
    }
}
