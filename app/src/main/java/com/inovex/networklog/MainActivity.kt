package com.inovex.networklog

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.inovex.networklog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var batchToken = 100L  //set the batch token here
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val devicePolicyManager = getSystemService(Activity.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
        val adminComponent = ComponentName(this, MyDeviceAdminReceiver::class.java)

        binding.btnCheckEnabled.setOnClickListener {
            if (devicePolicyManager != null) {
                if (devicePolicyManager.isAdminActive(adminComponent)) {
                    devicePolicyManager.setNetworkLoggingEnabled(adminComponent, true)
                    val isEnable = devicePolicyManager.isNetworkLoggingEnabled(adminComponent)
                    Toast.makeText(this@MainActivity, "$isEnable", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@MainActivity, "Admin is not Active", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "No Device Policy Manager found", Toast.LENGTH_SHORT).show()
            }

        }
        binding.btnRetrieveLogs.setOnClickListener {
            if (devicePolicyManager != null) {
                if (devicePolicyManager.isAdminActive(adminComponent)) {
                    val networkLogs = devicePolicyManager.retrieveNetworkLogs(componentName ,batchToken)
                    Toast.makeText(this@MainActivity, "$networkLogs", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Admin is not Active", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "No Device Policy Manager found", Toast.LENGTH_SHORT).show()
            }
        }
    }


}