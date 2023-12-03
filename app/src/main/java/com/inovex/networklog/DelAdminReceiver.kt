package com.inovex.networklog

import android.app.admin.DelegatedAdminReceiver
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DeviceAdminReceiver.ACTION_NETWORK_LOGS_AVAILABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.Q)
class DelAdminReceiver : DeviceAdminReceiver() {

    override fun onNetworkLogsAvailable(
        context: Context,
        intent: Intent,
        batchToken: Long,
        networkLogsCount: Int
    ) {
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)

        val batchIntent = Intent(ACTION_NETWORK_LOGS_AVAILABLE)
        batchIntent.putExtra(EXTRA_NETWORK_LOGS_BATCH_TOKEN, batchToken)
        context.sendBroadcast(batchIntent)

    }

    companion object{
//        const val ACTION_NETWORK_LOGS_AVAILABLE =
//            "com.android.cts.deviceowner.action.ACTION_NETWORK_LOGS_AVAILABLE"
        const val EXTRA_NETWORK_LOGS_BATCH_TOKEN =
            "com.android.cts.deviceowner.extra.NETWORK_LOGS_BATCH_TOKEN"
    }
}