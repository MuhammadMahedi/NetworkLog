package com.inovex.networklog

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

@RequiresApi(Build.VERSION_CODES.Q)
class DelAdminReceiver : DeviceAdminReceiver() {




    override fun onNetworkLogsAvailable(
        context: Context,
        intent: Intent,
        batchToken: Long,
        networkLogsCount: Int
    ) {
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)
        Log.d("batchTokenAvail", "$batchToken")

        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        val initialLogs = dpm.retrieveNetworkLogs(null,batchToken)

        Toast.makeText(context, "$initialLogs", Toast.LENGTH_SHORT).show()
        Log.d("retrieveData", "$initialLogs")

        val batchIntent = Intent(ACTION_NETWORK_LOGS_AVAILABLEE)
        batchIntent.putExtra(EXTRA_NETWORK_LOGS_BATCH_TOKEN, batchToken)
        context.sendBroadcast(batchIntent)

    }

    companion object{
        const val ACTION_NETWORK_LOGS_AVAILABLEE =
            "com.inovex.networklog.ACTION_NETWORK_LOGS_AVAILABLE"
        const val EXTRA_NETWORK_LOGS_BATCH_TOKEN =
            "com.inovex.networklog.extra.NETWORK_LOGS_BATCH_TOKEN"
    }
}