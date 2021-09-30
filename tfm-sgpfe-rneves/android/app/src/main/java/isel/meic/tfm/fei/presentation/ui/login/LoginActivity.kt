package isel.meic.tfm.fei.presentation.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.location.FeiLocationManager
import isel.meic.tfm.fei.presentation.BaseActivity
import isel.meic.tfm.fei.presentation.RegistrationActivity
import isel.meic.tfm.fei.presentation.ServicesActivity
import isel.meic.tfm.fei.presentation.viewmodel.LoginViewModel
import isel.meic.tfm.fei.service.LiveUpdateService
import isel.meic.tfm.fei.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
class LoginActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_login
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel =
            ViewModelProvider(
                this,
                LoginViewModelFactory.getInstance(application as FEIApplication)
            )
                .get(LoginViewModel::class.java)

        val username = findViewById<TextInputEditText>(R.id.username)
        val password = findViewById<TextInputEditText>(R.id.password)
        val login = findViewById<MaterialButton>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                password.text?.clear()
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })

        loginViewModel.loggedInUser.observe(this@LoginActivity, Observer {
            (application as FEIApplication).addToLoggedInUser(it)
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                Utils.hideKeyboard(this@LoginActivity)
                loading.visibility = View.VISIBLE
                scrollcontainer.visibility = View.GONE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

        //TODO delete
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            FeiLocationManager.init(this)

        //TODO remove later, for testing only
        register.setOnClickListener {
            /*val intent = Intent(this, LiveUpdateService::class.java)
            intent.action = LiveUpdateService.START_FOREGROUND_SERVICE
            startService(intent)*/
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        register2.setOnClickListener {
            val intent = Intent(this, LiveUpdateService::class.java)
            intent.action = LiveUpdateService.UPDATE_FOREGROUND_SERVICE
            startService(intent)
        }

        register3.setOnClickListener {
            val intent = Intent(this, LiveUpdateService::class.java)
            intent.action = LiveUpdateService.STOP_FOREGROUND_SERVICE
            startService(intent)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
        finish()
        startActivity(Intent(this, ServicesActivity::class.java))
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        loading.visibility = View.GONE
        scrollcontainer.visibility = View.VISIBLE
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
