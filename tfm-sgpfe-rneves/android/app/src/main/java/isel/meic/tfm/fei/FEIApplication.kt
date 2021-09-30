package isel.meic.tfm.fei

import android.app.Application
import androidx.room.Room
import androidx.work.WorkManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import isel.meic.tfm.fei.model.FeiDatabase
import isel.meic.tfm.fei.model.FeiRepository
import isel.meic.tfm.fei.presentation.data.model.LoggedInUser
import isel.meic.tfm.fei.utils.LruBitmapCache
import isel.meic.tfm.fei.utils.Utils

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643 - October 2019
 * @mentor: Paulo Pereira
 *
 * Class used to customize the application context.
 */
class FEIApplication : Application() {

    private lateinit var requestQueue: RequestQueue
    lateinit var imageLoader: ImageLoader
    lateinit var workManager: WorkManager
    private lateinit var url: String

    lateinit var repo: FeiRepository
    lateinit var db: FeiDatabase

    private var loggedInUser: LoggedInUser? = null

    override fun onCreate() {
        super.onCreate()

        requestQueue = Volley.newRequestQueue(this)
        workManager = WorkManager.getInstance()
        imageLoader = ImageLoader(
            requestQueue,
            LruBitmapCache(this)
        )

        db = Room.databaseBuilder(this, FeiDatabase::class.java, "fei-db").build()

        repo = FeiRepository(this, db)
        url = Utils.getServerAddress(this)
    }

    fun addToLoggedInUser(loggedInUser: LoggedInUser) {
        this.loggedInUser = loggedInUser
    }

    fun getLoggedInUser(): LoggedInUser? {
        return loggedInUser
    }

    fun logout() {
        this.loggedInUser = null
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    fun getUrl(): String {
        return url
    }

    private var isAppActive = false

    fun isApplicationActive() = isAppActive

    fun setApplicationState(active: Boolean) {
        isAppActive = active
    }

    private var isLiveUpdateServiceStarted = false

    fun isLiveUpdateServiceStarted() = isLiveUpdateServiceStarted

    fun setLiveUpdateServiceStarted(active: Boolean) {
        isLiveUpdateServiceStarted = active
    }
}