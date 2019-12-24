package com.android.app.common

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.*
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.android.app.R
import com.android.app.common.utils.DialogUtil


class IMapLocationController(context: Context):
        OnMapReadyCallback, LocationListener{


    var mContext = context
    var mLocationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager
    var mMap: GoogleMap? = null
    var mDialogUtil = DialogUtil(mContext)

    companion object {
        val TAG = IMapLocationController::class.java.simpleName
    }

    init {
        if (isGpsEnable()) {
            checkPermission()
            locationStart()
            setFusedLocation()
        } else {
            mDialogUtil.showGpsSettingsDialog(
                    R.string.imap_main_activity_gps_settings_message,
                    R.string.imap_main_activity_gps_settings_move_message)
        }
    }

    fun locationStart() {
        if (isGpsEnable()) {
            mDialogUtil.showGpsSettingsDialog(
                    R.string.imap_main_activity_gps_settings_message,
                    R.string.imap_main_activity_gps_settings_move_message)
        }
        checkPermission()
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2f, this)
    }

    fun locationStop() {
        mLocationManager.removeUpdates(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
       mMap = googleMap
    }

    private fun setFusedLocation() {
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    setCurrentPosition(location)
                    moveCamera(location)
                }
    }

    private fun setCurrentPosition(location: Location?) {
        val latLng = LatLng(location!!.latitude, location!!.longitude)
        mMap?.clear()
        mMap?.addMarker(MarkerOptions().position(latLng).title("Here"))
    }

    private fun moveCamera(location: Location?) {
        val latLng = LatLng(location!!.latitude, location!!.longitude)
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    /**
     * Called when the location has changed.
     *
     *
     *  There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    override fun onLocationChanged(location: Location?) {
        Log.e(TAG,"onLocationChanged")
        setCurrentPosition(location)
        moveCamera(location)
    }

    /**
     * This callback will never be invoked and providers can be considers as always in the
     * [LocationProvider.AVAILABLE] state.
     *
     */
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     * update.
     */
    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     * update.
     */
    override fun onProviderDisabled(provider: String?) {
    }

    private fun checkPermission() {
        if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mContext as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION), 1000)
        }
    }

    private fun isGpsEnable(): Boolean {
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}