package isel.meic.tfm.fei.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import isel.meic.tfm.fei.FEIApplication
import isel.meic.tfm.fei.R
import isel.meic.tfm.fei.presentation.ui.login.LoginActivity

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutResId: Int
    protected var pausedActivity: Boolean = true //todo delete this

    private fun initComponents() {
        setContentView(layoutResId)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initComponents()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initComponents()
    }

    override fun onResume() {
        super.onResume()
        pausedActivity = false
        (application as FEIApplication).setApplicationState(true)
    }

    override fun onPause() {
        super.onPause()
        pausedActivity = true
        (application as FEIApplication).setApplicationState(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (layoutResId != R.layout.settings_activity)
            menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun clearBackStackAndGoBackToLogin() {
        val intent = Intent(this, LoginActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    protected fun showLongSnackbar(resId: Int, colorId: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Snackbar.make(
                findViewById(android.R.id.content),
                resId,
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(resources.getColor(colorId, null))
                .show()
        } else {
            @Suppress("DEPRECATION")
            Snackbar.make(
                findViewById(android.R.id.content),
                resId,
                Snackbar.LENGTH_LONG
            ).setBackgroundTint(resources.getColor(colorId))
                .show()
        }
    }

    protected fun setInputLayoutError(
        textInputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
        errorId: Int
    ) {
        textInputLayout.error = getString(errorId)
        textInputEditText.setOnFocusChangeListener { _, _ ->
            run {
                textInputLayout.error = null
                textInputEditText.onFocusChangeListener = null
            }
        }
    }
}