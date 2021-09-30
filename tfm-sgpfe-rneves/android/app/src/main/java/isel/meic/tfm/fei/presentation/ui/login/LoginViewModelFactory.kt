package isel.meic.tfm.fei.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.presentation.data.LoginDataSource
import isel.meic.tfm.fei.presentation.data.LoginRepository
import isel.meic.tfm.fei.presentation.viewmodel.LoginViewModel

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory private constructor(val application: FEIApplication) :
    ViewModelProvider.Factory {

    companion object {
        private var instance: LoginViewModelFactory? = null

        fun getInstance(application: FEIApplication): LoginViewModelFactory {
            if (instance == null)
                instance = LoginViewModelFactory(application)
            return instance!!
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository.getInstance(
                    dataSource = LoginDataSource.getInstance(application)
                ),
                context = application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
