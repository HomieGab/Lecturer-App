package com.example.la4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings
import android.widget.Button

class WifiOffActivity : AppCompatActivity() {
    private lateinit var wifiStateReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_off)

        val turnOnWifiButton: Button = findViewById(R.id.permissions_button2)
        turnOnWifiButton.setOnClickListener {
            // Use Settings.Panel to prompt the user to enable WiFi
            val panelIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(panelIntent)
        }

        wifiStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val wifiState = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
                if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
                    val goIntent = Intent(this@WifiOffActivity, WifiDirectGoActivity::class.java)
                    startActivity(goIntent)
                    finish()
                }
            }
        }

        registerReceiver(wifiStateReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(wifiStateReceiver)
    }
}
