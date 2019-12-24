package com.android.app.common

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.heatmaps.HeatmapTileProvider

class HeatMapController(context: Context, googleMap: GoogleMap) {
    var mContext = context
    var mGoogleMap = googleMap
    var mHeatMapTileProvier = HeatmapTileProvider.Builder().build()
}