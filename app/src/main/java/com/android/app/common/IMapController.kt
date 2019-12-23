package com.android.app.common

import android.content.Context
import com.google.android.gms.maps.SupportMapFragment


class IMapController(context: Context, supportMapFragment: SupportMapFragment){

    var mContext = context
    var mSupportMapFragment = supportMapFragment
    var mIMapLocationController = IMapLocationController(mContext)

    companion object {
        val TAG = IMapController::class.java.simpleName
    }

    init {
        mSupportMapFragment.getMapAsync(mIMapLocationController)
    }

    fun update() {
        mIMapLocationController.locationStart()
    }

    fun destory() {
        mIMapLocationController.locationStop()
    }
}