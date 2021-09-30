package isel.meic.tfm.fei.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.presentation.viewmodel.TicketViewModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
abstract class TicketBaseActivity : BaseActivity() {

    private var viewModelFactory: ViewModelProvider.Factory? = null

    protected fun getViewModelFactory(repo: FeiRepository): ViewModelProvider.Factory {
        if (viewModelFactory == null) {
            viewModelFactory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return TicketViewModel(
                        this@TicketBaseActivity.application as FEIApplication,
                        repo
                    ) as T
                }
            }
        }
        return viewModelFactory!!
    }
}