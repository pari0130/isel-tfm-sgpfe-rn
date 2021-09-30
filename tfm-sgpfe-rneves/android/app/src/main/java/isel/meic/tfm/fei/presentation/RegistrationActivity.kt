package isel.meic.tfm.fei.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.presentation.viewmodel.RegisterViewModel
import isel.meic.tfm.fei.utils.Constants
import isel.meic.tfm.fei.utils.Utils
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class RegistrationActivity : BaseActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_register

    private var viewModelFactory: ViewModelProvider.Factory? = null

    private fun getViewModelFactory(repo: FeiRepository): ViewModelProvider.Factory {
        if (viewModelFactory == null) {
            viewModelFactory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return RegisterViewModel(
                        this@RegistrationActivity.application as FEIApplication,
                        repo
                    ) as T
                }
            }
        }
        return viewModelFactory!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model =
            ViewModelProvider(this, getViewModelFactory((application as FEIApplication).repo))
                .get(RegisterViewModel::class.java)

        model.userRegisterFailed.observe(this) {
            enableViews()
        }

        model.userRegister.observe(this) {
            if (it.success) {
                finish()
                val intent = Intent(this, SuccessActivity::class.java)
                intent.putExtra(
                    Constants.TITLE,
                    resources.getString(R.string.register_success_title)
                )
                intent.putExtra(
                    Constants.MESSAGE,
                    resources.getString(R.string.register_success_message)
                )
                startActivity(intent)
            } else {
                enableViews()
                showLongSnackbar(
                    if (it.username) R.string.username_exists else R.string.email_exists,
                    R.color.snackbarError
                )
            }
        }

        model.userRegisterFailed.observe(this) {
            enableViews()
            showLongSnackbar(
                R.string.register_failed,
                R.color.snackbarError
            )
        }

        close.setOnClickListener {
            onBackPressed()
        }

        save.setOnClickListener {
            Utils.hideKeyboard(this)
            //TODO In the view model validate each field
            //THIS SHOULD BE DONE IN THE VIEW MODEL TODO fixme
            if (password.text.toString().isNotEmpty() && confirm_password.text.toString()
                    .isNotEmpty() && password.text.toString() == confirm_password.text.toString()
            ) {
                disableViews()
                model.register(
                    username.text.toString(),
                    password.text.toString(),
                    name.text.toString(),
                    email.text.toString(),
                    phone.text.toString().toInt(),
                    notification.isChecked,
                )
            } else {
                setInputLayoutError(password_layout, password, R.string.register_password_not_equal)
                setInputLayoutError(
                    confirm_password_layout,
                    confirm_password,
                    R.string.register_password_not_equal
                )
            }
        }
    }

    private fun disableViews() {
        username.isEnabled = false
        email.isEnabled = false
        password.isEnabled = false
        confirm_password.isEnabled = false
        name.isEnabled = false
        phone.isEnabled = false
        notification.isEnabled = false
        save.isEnabled = false
        progress_bar_save.visibility = View.VISIBLE
    }

    private fun enableViews() {
        progress_bar_save.visibility = View.GONE
        name.isEnabled = true
        email.isEnabled = true
        confirm_password.isEnabled = true
        notification.isEnabled = true
        username.isEnabled = true
        password.isEnabled = true
        phone.isEnabled = true
    }
}