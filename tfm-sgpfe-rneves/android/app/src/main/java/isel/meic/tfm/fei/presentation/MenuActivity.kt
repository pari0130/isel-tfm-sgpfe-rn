package isel.meic.tfm.fei.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.presentation.ui.login.LoginActivity
import isel.meic.tfm.fei.presentation.viewmodel.LoginViewModel
import isel.meic.tfm.fei.presentation.ui.login.LoginViewModelFactory
import kotlinx.android.synthetic.main.settings_activity.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class MenuActivity : BaseActivity() {
    override val layoutResId: Int = R.layout.settings_activity
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel =
            ViewModelProvider(
                this,
                LoginViewModelFactory.getInstance(application as FEIApplication)
            )
                .get(LoginViewModel::class.java)

        val name = (application as FEIApplication).getLoggedInUser()?.name ?: "-"
        val splitName = name.split(" ")
        val initials: String =
            if (splitName.size > 1) "${splitName[0].first()}${splitName[splitName.size - 1].first()}" else splitName[0].first()
                .toString()

        val toolbar = findViewById<Toolbar>(R.id.app_bar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        userIconName.text = initials

        logout.setOnClickListener {
            loginViewModel.logout(
                (application as FEIApplication).getLoggedInUser()?.token!!
            )
            val intent = Intent(this, LoginActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        my_account.setOnClickListener {
            val intent = Intent(this, MyAccountActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}