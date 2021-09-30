package isel.meic.tfm.fei.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.presentation.viewmodel.MyAccountViewModel
import kotlinx.android.synthetic.main.activity_account.*

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class MyAccountActivity : BaseActivity() {
    override val layoutResId: Int
        get() = R.layout.activity_account

    private var viewModelFactory: ViewModelProvider.Factory? = null

    private fun getViewModelFactory(repo: FeiRepository): ViewModelProvider.Factory {
        if (viewModelFactory == null) {
            viewModelFactory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return MyAccountViewModel(
                        this@MyAccountActivity.application as FEIApplication,
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

        val model =
            ViewModelProvider(this, getViewModelFactory((application as FEIApplication).repo))
                .get(MyAccountViewModel::class.java)

        model.getUserInformation((application as FEIApplication).getLoggedInUser()?.token!!)

        model.userInformation.observe(this) {
            val splitName = it.name.split(" ")
            val initials: String =
                if (splitName.size > 1) "${splitName[0].first()}${splitName[splitName.size - 1].first()}" else splitName[0].first()
                    .toString()

            userIconName.text = initials
            username.setText(it.username)
            name.setText(it.name)
            phone.setText(it.phone.toString())
            notification.isChecked = it.notification
            loading.visibility = View.GONE
            container.visibility = View.VISIBLE
            save.isEnabled = false
        }

        model.userInformationUpdated.observe(this) {
            enableViews()
            val splitName = name.text.toString().split(" ")
            val initials: String =
                if (splitName.size > 1) "${splitName[0].first()}${splitName[splitName.size - 1].first()}" else splitName[0].first()
                    .toString()
            userIconName.text = initials
            Toast.makeText(application, "User Updated!", Toast.LENGTH_SHORT).show()
        }

        model.userInformationUpdateFailed.observe(this) {
            enableViews()
        }

        val textWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                save.isEnabled = true
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
            }
        }

        name.addTextChangedListener(textWatcher)
        notification.addTextChangedListener(textWatcher)
        notification.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            save.isEnabled = true
        }

        //todo put this in xml - start
        //todo maybe add this to login
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        )
        val colors = intArrayOf(
            Color.parseColor("#125184"),
            Color.parseColor("#4584b7")
        )
        val colorStates = ColorStateList(states, colors)

        save.backgroundTintList = colorStates

        //todo put this in xml - end

        save.setOnClickListener {
            disableViews()
            model.editUserInformation(
                (application as FEIApplication).getLoggedInUser()?.userId!!,
                name.text.toString(),
                phone.text.toString().toInt(),
                notification.isChecked,
                (application as FEIApplication).getLoggedInUser()?.token!!
            )
        }
    }

    private fun enableViews() {
        progress_bar_save.visibility = View.GONE
        name.isEnabled = true
        notification.isEnabled = true
    }

    private fun disableViews() {
        name.isEnabled = false
        notification.isEnabled = false
        save.isEnabled = false
        progress_bar_save.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}