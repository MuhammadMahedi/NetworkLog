package com.inovex.networklog

import android.app.admin.DeviceAdminReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.UserHandle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyDeviceAdminReceiver  :  DeviceAdminReceiver(){
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
    }
    override fun onNetworkLogsAvailable(
        context: Context, intent: Intent, batchToken: Long,
        networkLogsCount: Int
    ) {

        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)
        // send the broadcast, the rest of the test happens in NetworkLoggingTest
        val batchIntent = Intent(ACTION_NETWORK_LOGS_AVAILABLE)
        batchIntent.putExtra(EXTRA_NETWORK_LOGS_BATCH_TOKEN, batchToken)
       // DeviceOwnerHelper.sendBroadcastToTestAppReceivers(context, batchIntent)
    }

    companion object {
        private val TAG = MyDeviceAdminReceiver::class.java.simpleName
        const val ACTION_USER_ADDED = "com.android.cts.deviceowner.action.USER_ADDED"
        const val ACTION_USER_REMOVED = "com.android.cts.deviceowner.action.USER_REMOVED"
        const val ACTION_USER_STARTED = "com.android.cts.deviceowner.action.USER_STARTED"
        const val ACTION_USER_STOPPED = "com.android.cts.deviceowner.action.USER_STOPPED"
        const val ACTION_USER_SWITCHED = "com.android.cts.deviceowner.action.USER_SWITCHED"
        const val EXTRA_USER_HANDLE = "com.android.cts.deviceowner.extra.USER_HANDLE"
        const val ACTION_NETWORK_LOGS_AVAILABLE =
            "com.android.cts.deviceowner.action.ACTION_NETWORK_LOGS_AVAILABLE"
        const val EXTRA_NETWORK_LOGS_BATCH_TOKEN =
            "com.android.cts.deviceowner.extra.NETWORK_LOGS_BATCH_TOKEN"

        fun getComponentName(context: Context?): ComponentName {
            return ComponentName(context!!, MyDeviceAdminReceiver::class.java)
        }
    }

}

/*import com.android.bedstead.dpmwrapper.DeviceOwnerHelper
import com.android.cts.devicepolicy.OperationSafetyChangedCallback
import com.android.cts.devicepolicy.OperationSafetyChangedEvent


class BasicAdminReceiver : DeviceAdminReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (DeviceOwnerHelper.runManagerMethod(this, context, intent)) return
        val action = intent.action
        Log.d(TAG, "onReceive(userId=" + context.getUserId() + "): " + action)
        super.onReceive(context, intent)
    }

    override fun onUserAdded(context: Context, intent: Intent, userHandle: UserHandle) {
        sendUserBroadcast(context, ACTION_USER_ADDED, userHandle)
    }

    override fun onUserRemoved(context: Context, intent: Intent, userHandle: UserHandle) {
        sendUserBroadcast(context, ACTION_USER_REMOVED, userHandle)
    }

    override fun onUserStarted(context: Context, intent: Intent, userHandle: UserHandle) {
        sendUserBroadcast(context, ACTION_USER_STARTED, userHandle)
    }

    override fun onUserStopped(context: Context, intent: Intent, userHandle: UserHandle) {
        sendUserBroadcast(context, ACTION_USER_STOPPED, userHandle)
    }

    override fun onUserSwitched(context: Context, intent: Intent, userHandle: UserHandle) {
        sendUserBroadcast(context, ACTION_USER_SWITCHED, userHandle)
    }

    override fun onNetworkLogsAvailable(
        context: Context, intent: Intent, batchToken: Long,
        networkLogsCount: Int
    ) {
        Log.d(
            TAG,
            "onNetworkLogsAvailable() on user " + context.getUserId() + ": token=" + batchToken + ", count=" + networkLogsCount
        )
        super.onNetworkLogsAvailable(context, intent, batchToken, networkLogsCount)
        // send the broadcast, the rest of the test happens in NetworkLoggingTest
        val batchIntent = Intent(ACTION_NETWORK_LOGS_AVAILABLE)
        batchIntent.putExtra(EXTRA_NETWORK_LOGS_BATCH_TOKEN, batchToken)
        DeviceOwnerHelper.sendBroadcastToTestAppReceivers(context, batchIntent)
    }

    override fun onOperationSafetyStateChanged(context: Context, reason: Int, isSafe: Boolean) {
        val event = OperationSafetyChangedEvent(reason, isSafe)
        Log.d(TAG, "onOperationSafetyStateChanged() on user " + context.getUserId() + ": " + event)
        val intent: Intent = OperationSafetyChangedCallback.intentFor(event)
        DeviceOwnerHelper.sendBroadcastToTestAppReceivers(context, intent)
    }

    private fun sendUserBroadcast(context: Context, action: String, userHandle: UserHandle) {
        Log.d(
            TAG,
            "sendUserBroadcast(): action=$action, user=$userHandle"
        )
        val intent = Intent(action).putExtra(EXTRA_USER_HANDLE, userHandle)

        // NOTE: broadcast locally as user-related tests on headless system user always run on
        // system user, as current user is stopped on switch.
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }


}*/


