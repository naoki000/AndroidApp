package com.android.app.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.*
import android.location.Criteria
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.common.util.Strings
import com.google.android.gms.location.LocationServices


class IMapLocationController(context: Context):
        OnMapReadyCallback, LocationListener{


    var mContext = context
    var mLocationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager
    var mBestProvider = "null"
    var mMap: GoogleMap? = null

    companion object {
        val TAG = IMapLocationController::class.java.simpleName
    }

    init {
        checkPermission()
        setFusedLocation()
        initLocationManager()
    }

    private fun initLocationManager() {
        // 詳細設定
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        criteria.powerRequirement = Criteria.POWER_HIGH
        criteria.isSpeedRequired = false
        criteria.isAltitudeRequired = false
        criteria.isBearingRequired = false
        criteria.isCostAllowed = true
        criteria.horizontalAccuracy = Criteria.ACCURACY_HIGH
        criteria.verticalAccuracy = Criteria.ACCURACY_HIGH
        mBestProvider = mLocationManager.getBestProvider(criteria,true)
    }

    fun locationStart() {
        checkPermission()
        mLocationManager.requestLocationUpdates(mBestProvider, 10, 0.01f, this)
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
                    // Got last known location. In some rare situations this can be null.
                    setCurrentPositionAndMoveCamera(location)
                }
    }

    private fun setCurrentPosition(location: Location?) {
        val sydney = LatLng(location!!.latitude, location!!.longitude)
        mMap?.addMarker(MarkerOptions().position(sydney).title("Here"))
    }

    private fun setCurrentPositionAndMoveCamera(location: Location?) {
        val sydney = LatLng(location!!.latitude, location!!.longitude)
        mMap?.addMarker(MarkerOptions().position(sydney).title("Here"))
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
        setCurrentPosition(location)
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun checkPermission() {
        if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // パーミッションの許可を取得する
            requestPermissions(mContext as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION), 1000)
        }
    }
}