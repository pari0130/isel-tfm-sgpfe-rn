package isel.meic.tfm.fei.presentation

import android.os.Bundle
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.utils.Constants.Companion.MESSAGE
import isel.meic.tfm.fei.utils.Constants.Companion.TITLE
import kotlinx.android.synthetic.main.activity_register.close
import kotlinx.android.synthetic.main.activity_success.*

class SuccessActivity : BaseActivity() {
    override val layoutResId: Int
        get() = R.layout.activity_success

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val title =
            intent.extras?.getString(TITLE) ?: resources.getString(R.string.default_success_title)
        val message = intent.extras?.getString(MESSAGE)
            ?: resources.getString(R.string.default_success_message)

        title_success.text = title
        message_success.text = message

        close.setOnClickListener {
            clearBackStackAndGoBackToLogin()
            onBackPressed()
        }
    }
}