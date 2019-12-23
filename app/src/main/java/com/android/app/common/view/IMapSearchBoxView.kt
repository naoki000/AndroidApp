package com.android.app.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.SearchView
import com.android.app.common.IMapMainActivity

class IMapSearchBoxView(context: Context?, attributeSet: AttributeSet?) :
        SearchView(context, attributeSet) {
    companion object {
        val TAG = IMapMainActivity::class.java.simpleName
    }

    var mContext: Context?

    init {
        mContext = context
    }
}