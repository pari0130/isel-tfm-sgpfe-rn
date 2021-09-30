package isel.meic.tfm.fei.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class FeiLocationManager {

    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private var currentLocation: Location? = null
        private var locationManager: LocationManager? = null
        private const val TWO_MINUTES = 2 * 60 * 1000 //MAX AGE

        @RequiresApi(Build.VERSION_CODES.M)
        fun init(activity: Activity) {
            //GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(application) == ConnectionResult.SUCCESS)
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.OnRequestPermissionsResultCallback { requestCode: Int, _: Array<String>, grantResults: IntArray ->
                    if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getLocation(activity)
                        Log.d("FeiLocationManagerInit1", "POMPS The location: $currentLocation")
                    }
                }

                activity.requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                locationManager =
                    activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                getLocation(activity)
                Log.d("FeiLocationManagerInit2", "POMPS The location: $currentLocation")
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun getCurrentLocation(activity: Activity, callback: (Location?) -> Unit) {
            if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.OnRequestPermissionsResultCallback { requestCode: Int, _: Array<String>, grantResults: IntArray ->
                    if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getLocation(activity)
                        callback(currentLocation)
                        Log.d("getCurrentLocation1", "POMPS The location: $currentLocation")
                    } else
                        callback(null)
                }

                activity.requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                getLocation(activity)
                callback(currentLocation)
                Log.d("getCurrentLocation2", "POMPS The location: $currentLocation")

            }
        }

        private fun getLocation(activity: Activity) {
            if (isOlderThanMinutes(currentLocation)) {
                currentLocation = getLastKnownLocation(activity)
            }
        }

        private fun isOlderThanMinutes(location: Location?): Boolean {
            return location == null || !location.hasAccuracy() || TWO_MINUTES >= 0 && System.currentTimeMillis() - location.time >= TWO_MINUTES
        }

        @SuppressLint("MissingPermission")
        private fun getLastKnownLocation(context: Context): Location? {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers = locationManager.getProviders(true)

            for (i in providers.indices.reversed()) {
                val provider = providers[i]
                if (locationManager.isProviderEnabled(provider)) {
                    val location = locationManager.getLastKnownLocation(provider)
                    if (location != null && location.hasAccuracy() && (TWO_MINUTES < 0 || System.currentTimeMillis()
                                - location.time < TWO_MINUTES)
                    ) return location
                }
            }
            return null
        }
    }
}