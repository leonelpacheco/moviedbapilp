package com.leonel.pruebasmoviedbbottom.services

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leonel.pruebasmoviedbbottom.MainActivity
import com.leonel.pruebasmoviedbbottom.model.locationuser
import com.leonel.pruebasmoviedbbottom.utils.sendNotification
import java.util.*


class LocationService : Service() {
    private var mLocationManager: LocationManager? = null
    var context: Context = this
    var mLocationListeners = arrayOf(LocationListener(LocationManager.GPS_PROVIDER, context), LocationListener(
        LocationManager.NETWORK_PROVIDER, context))

    class LocationListener(provider: String, contextlocation: Context) : android.location.LocationListener {
        internal var mLastLocation: Location
        var context: Context = contextlocation
        private lateinit var notificationManager : NotificationManager
        init {
            Log.e(TAG, "LocationListener $provider")
            mLastLocation = Location(provider)

        }

        override fun onLocationChanged(location: Location) {
            Log.e(TAG, "onLocationChanged: $location")
            mLastLocation.set(location)
            notificationManager = ContextCompat.getSystemService(context,
                NotificationManager::class.java) as NotificationManager
            Log.v("LastLocation", mLastLocation.latitude.toString() +"  " + mLastLocation.longitude.toString())

            //*******************Envio de gps a firebase*****************
            val date = Date()
            val location = locationuser(mLastLocation.latitude.toString(),mLastLocation.longitude.toString(), date.toString())


            val db = Firebase.firestore

            db.collection("locationsUser")
                .add(location)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                   //***************
                    createChannel("1",
                        "localizacion")
                    //TODO(Send Notification using extention function)
                    notificationManager.sendNotification(
                        "Aviso",
                        "Geolocalización enviada al servidor",
                        "1",
                        context.applicationContext
                    )
                    //***************
                }
                .addOnFailureListener { e->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
            //***********************************************************

        }

        //Write a createChannel Method inside MainActivity.kt
        private fun createChannel(channelId : String, channelName : String){
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    //TODO ("Change Importance as per requirement")
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    enableLights(true)
                    enableVibration(true)
                    lightColor = Color.GREEN
                }

                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        override fun onProviderDisabled(provider: String) {
            Log.e(TAG, "onProviderDisabled: $provider")
        }

        override fun onProviderEnabled(provider: String) {
            Log.e(TAG, "onProviderEnabled: $provider")
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            Log.e(TAG, "onStatusChanged: $provider")
        }
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")
        super.onStartCommand(intent, flags, startId)
        return Service.START_STICKY
    }

    override fun onCreate() {
        Log.e(TAG, "onCreate")
        context = this
        initializeLocationManager()

        //****************
        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL.toLong(), LOCATION_DISTANCE,
                mLocationListeners[1])
        } catch (ex: java.lang.SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "network provider does not exist, " + ex.message)
        }

        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL.toLong(), LOCATION_DISTANCE,
                mLocationListeners[0])
        } catch (ex: java.lang.SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "gps provider does not exist " + ex.message)
        }

        //**********************************

    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
        if (mLocationManager != null) {
            for (i in mLocationListeners.indices) {
                try {
                    mLocationManager!!.removeUpdates(mLocationListeners[i])
                } catch (ex: Exception) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex)
                }

            }
        }
    }

    private fun initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager")

        //****************
        if (mLocationManager == null) {
            mLocationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        //******************
    }

    companion object {
        private val TAG = "Envio de informacion"
        private val LOCATION_INTERVAL = 300000
        private val LOCATION_DISTANCE = 0f
    }


}