package isel.meic.tfm.fei.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.KeyguardManager
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.IOException
import java.util.*


/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - October 2019
 * @mentor: Paulo Pereira
 */
class Utils {

    //TODO create Properties class and initiate it and load the properties when the app starts

    companion object {
        fun getServerAddress(context: Context): String {
            return getProperty("server-address", context, "FEI-android-server.properties")
        }

        private fun getProperty(key: String, context: Context, filename: String): String {
            return try {
                val properties = Properties()
                val assetManager = context.assets
                val inputStream = assetManager.open(filename)
                properties.load(inputStream)
                properties.getProperty(key)
            } catch (e: IOException) {
                ""
            }
        }

        fun isApplicationForeground(context: Context): Boolean {
            val keyguardManager =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?
            if (keyguardManager != null && keyguardManager.isKeyguardLocked) {
                return false
            }
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
                    ?: return false
            val appProcesses = activityManager.runningAppProcesses ?: return false
            val packageName = context.packageName

            for (appProcess in appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName == packageName
                ) {
                    return true
                }
            }
            return false
        }

        //if i decide to use fragments i'll need to change this to hideKeyboard(activity: Activity, view: View), so i can pass the proper view
        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view: View? = activity.currentFocus
            if (view == null)
                view = View(activity)

            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}