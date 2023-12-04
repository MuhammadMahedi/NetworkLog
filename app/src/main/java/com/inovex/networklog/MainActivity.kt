package com.inovex.networklog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DeviceAdminReceiver.ACTION_NETWORK_LOGS_AVAILABLE
import android.app.admin.DevicePolicyManager
import android.app.admin.NetworkEvent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.inovex.networklog.DelAdminReceiver.Companion.ACTION_NETWORK_LOGS_AVAILABLEE
import com.inovex.networklog.DelAdminReceiver.Companion.EXTRA_NETWORK_LOGS_BATCH_TOKEN
import com.inovex.networklog.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var batchToken = 0L
    private val batchTokenReceiver = object : BroadcastReceiver() {

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(this@MainActivity, "Received Something", Toast.LENGTH_SHORT).show()
            Log.d("batchTokenOnReceive", "Received Something")
            if (ACTION_NETWORK_LOGS_AVAILABLEE == intent?.action) {
                val token = intent.getLongExtra(EXTRA_NETWORK_LOGS_BATCH_TOKEN, 0) // 0 is the default value
                // Use the batch token here
                batchToken= token
                Log.d("batchTokenOnReceive", "$token")
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("batchTokenInt", "${intent?.action}")
        //Log.d("batchToken", "$ACTION_NETWORK_LOGS_AVAILABLE")

        val devicePolicyManager =
            getSystemService(Activity.DEVICE_POLICY_SERVICE) as DevicePolicyManager?

        devicePolicyManager?.setNetworkLoggingEnabled(null,true)


        // val adminComponent = ComponentName(this, MyDeviceAdminReceiver::class.java)
        //val deviceAdminReceiver= DeviceAdminReceiver()
        //intent.getLongExtra("com.android.cts.deviceowner.extra.NETWORK_LOGS_BATCH_TOKEN")
//        val d = deviceAdminReceiver.onNetworkLogsAvailable(this,intent,0L,100)


        binding.btnCheckEnabled.setOnClickListener {
            /*if (devicePolicyManager != null) {
                if (devicePolicyManager.isAdminActive(adminComponent)) {
                    devicePolicyManager.setNetworkLoggingEnabled(null,true)
                    val isEnable = devicePolicyManager.isNetworkLoggingEnabled(adminComponent)
                    Toast.makeText(this@MainActivity, "$isEnable", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@MainActivity, "Admin is not Active", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "No Device Policy Manager found", Toast.LENGTH_SHORT).show()
            }*/

            val isEnable = devicePolicyManager?.isNetworkLoggingEnabled(null)
            Toast.makeText(this@MainActivity, "$isEnable", Toast.LENGTH_SHORT).show()

        }
        binding.btnRetrieveLogs.setOnClickListener {
            /* if (devicePolicyManager != null) {
                if (devicePolicyManager.isAdminActive(adminComponent)) {
                    val networkLogs = devicePolicyManager.retrieveNetworkLogs(componentName ,batchToken)
                    Toast.makeText(this@MainActivity, "$networkLogs", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Admin is not Active", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "No Device Policy Manager found", Toast.LENGTH_SHORT).show()
            }*/
            //val initialBatchToken = -666L

            //after update batchtoken will start with 0 but we cant retrei the logs bcz the batch token is not provided
// we need to trigger the batch in the terminal with  "adb path shell dpm force-network-logs"
            //this will trigger the batchToken and we will get 1 as batchtoken.
            //then clicking the button it works
            Log.d("batchToken1", "$batchToken")
            val initialLogs = devicePolicyManager?.retrieveNetworkLogs(null, 1)

            Toast.makeText(this, "$initialLogs", Toast.LENGTH_SHORT).show()
            Log.d("retrieveData", initialLogs.toString())
            binding.tvShowLogs.text = initialLogs.toString()
           // val nextBatchToken = getNextBatchToken(initialLogs) // Logic to extract the next batch token

            /*if (nextBatchToken != 0L) {
                val nextLogs = devicePolicyManager?.retrieveNetworkLogs(null, nextBatchToken)
                Toast.makeText(this, "$nextLogs", Toast.LENGTH_SHORT).show()
                Log.d("retrieveNextData", "$nextLogs")
            }*/

        }

    }
   /* private fun getNextBatchToken(logs: List<NetworkEvent>?): Long {

        val token = intent.getLongExtra(
            BasicAdminReceiver.EXTRA_NETWORK_LOGS_BATCH_TOKEN,
            -666
        )

        if (logs !=null) {
            val lastLog = logs.last() // Assuming the last log contains the batch token
            //return lastLog.batchToken
            return 0L // replace this with "return lastLog.batchToken" or Adjust this according to your data structure
        }

        return 0L // If no more logs or batch token is not available
    }*/


    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onResume() {
        super.onResume()
        Log.d("status", "OnResume")
        val intentFilter = IntentFilter(ACTION_NETWORK_LOGS_AVAILABLEE)
        registerReceiver(batchTokenReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        Log.d("status", "OnPause")
        //unregisterReceiver(batchTokenReceiver)
    }


}