package com.android.app.common.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings

class DialogUtil(context: Context) {
    private var mContext = context
    var mPositiveButtonMessageId = android.R.string.ok
    var mNegativeButtonMessageId = android.R.string.cancel


    fun showGpsSettingsDialog(messageId: Int) {
        showGpsSettingsDialog(messageId, mPositiveButtonMessageId, mNegativeButtonMessageId)
    }

    fun showGpsSettingsDialog(messageId: Int, positiveButtonMessageId: Int) {
        showGpsSettingsDialog(messageId, positiveButtonMessageId, mNegativeButtonMessageId)
    }

    fun showGpsSettingsDialog(messageId: Int, positiveButtonMessageId: Int, negativeButtonMessageId: Int) {
        val alertDialogBuilder = AlertDialog.Builder(mContext)
        alertDialogBuilder.setMessage(messageId)
                .setCancelable(false)
                .setPositiveButton(positiveButtonMessageId,
                        DialogInterface.OnClickListener { dialog, id ->
                            val callGPSSettingIntent = Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            mContext.startActivity(callGPSSettingIntent)
                        })
        //キャンセルボタン処理
        alertDialogBuilder.setNegativeButton(negativeButtonMessageId,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
        val alert = alertDialogBuilder.create()
        // 設定画面へ移動するかの問い合わせダイアログを表示
        alert.show()
    }
}