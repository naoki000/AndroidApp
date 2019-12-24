package com.android.app.common

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.android.app.R
import com.google.android.gms.maps.SupportMapFragment

class IMapMainActivity : FragmentActivity() {

    var mIMapController: IMapController? = null

    companion object {
        val TAG = IMapMainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imap_main_activity)
        var supportFragmentManager = supportFragmentManager.findFragmentById(R.id.intereseted_map)
                as SupportMapFragment
        mIMapController = IMapController(this, supportFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        mIMapController?.update()
    }

    override fun onDestroy() {
        super.onDestroy()
        mIMapController?.destory()
    }
}