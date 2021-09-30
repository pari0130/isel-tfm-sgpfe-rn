package isel.meic.tfm.fei.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.dto.LoggedInUserInformationDto
import isel.meic.tfm.fei.presentation.ui.login.LoginActivity
import isel.meic.tfm.fei.presentation.viewmodel.LoginViewModel
import isel.meic.tfm.fei.presentation.ui.login.LoginViewModelFactory
import isel.meic.tfm.fei.utils.Constants.Companion.FEI_PREFERENCES
import isel.meic.tfm.fei.utils.Constants.Companion.LOGGED_IN_USER

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
class SplashActivity : BaseActivity() {

    companion object {
        private const val SPLASH_DISPLAY_LENGTH: Long = 1750
        private const val SPLASH_DISPLAY_SHORT_LENGTH: Long = 750
    }

    override val layoutResId: Int = R.layout.activity_splash
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory.getInstance(application as FEIApplication))
                .get(LoginViewModel::class.java)


        loginViewModel.loggedInUser.observe(this@SplashActivity, Observer {
            (application as FEIApplication).addToLoggedInUser(it)
        })

        loginViewModel.loginResult.observe(this@SplashActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                Handler(mainLooper).postDelayed({
                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )
                }, SPLASH_DISPLAY_SHORT_LENGTH)
            }
            if (loginResult.success != null) {
                Handler(mainLooper).postDelayed({
                    startActivity(
                        Intent(
                            this,
                            ServicesActivity::class.java
                        )
                    )
                }, SPLASH_DISPLAY_SHORT_LENGTH)
            }
            setResult(RESULT_OK)

            //Complete and destroy login activity once successful
            //finish()
        })
        attemptAutoLogin()
    }

    fun runtimeEnableAutoInit() {
        // [START fcm_runtime_enable_auto_init]
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        // [END fcm_runtime_enable_auto_init]
    }

    private fun attemptAutoLogin() {
        val sharedPreferences = getSharedPreferences(FEI_PREFERENCES, Context.MODE_PRIVATE)
        val loggedInUser = sharedPreferences.getString(LOGGED_IN_USER, null)
        if (loggedInUser == null) {
            Handler(mainLooper).postDelayed(
                {
                    startActivity(Intent(this, LoginActivity::class.java))
                    overridePendingTransition(0, 0)
                },
                SPLASH_DISPLAY_LENGTH
            )
        } else {
            val gson = Gson()
            val loggedIn = gson.fromJson(loggedInUser, LoggedInUserInformationDto::class.java)
            loginViewModel.isTokenValid(loggedIn)
        }
    }
}