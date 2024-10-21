package com.example.la4

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var wifiP2pManager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            initializeApp()
        } else {
            // Handle the case where permissions are not granted
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                initializeApp()
            }
            else -> {
                requestPermissions()
            }
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun initializeApp() {
        wifiP2pManager = getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
        channel = wifiP2pManager.initialize(this, mainLooper, null)

        displayWifiInfo()

        val studentIds = generateHashedStudentIds()
        val randomStudentIds = studentIds.shuffled().take(5)

        val recyclerView: RecyclerView = findViewById(R.id.rvAttendees)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AttendeesAdapter(randomStudentIds) { studentId ->
            startChatActivity(studentId)
        }

        val endClassButton: Button = findViewById(R.id.end_class_button)
        endClassButton.setOnClickListener {
        }

        checkWifiDirectState()
    }

    private fun displayWifiInfo() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val linkProperties: LinkProperties? = connectivityManager.getLinkProperties(network)
        val ipAddress = linkProperties?.linkAddresses?.firstOrNull()?.address?.hostAddress ?: "Unknown IP"
        val networkPassword = "your_network_password" // Replace with actual password retrieval logic

        val classNetworkTextView: TextView = findViewById(R.id.permissions_description3)
        val networkPasswordTextView: TextView = findViewById(R.id.permissions_description4)
        classNetworkTextView.text = getString(R.string.class_network, ipAddress)
        networkPasswordTextView.text = getString(R.string.network_password, networkPassword)
    }

    private fun checkWifiDirectState() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            val intent = Intent(this, WifiOffActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun generateHashedStudentIds(): List<String> {
        val studentIds = (816000000..816999999).map { it.toString() }
        return studentIds.map { EncryptionUtils.hashStrSha256(it) }
    }

    private fun startChatActivity(studentId: String) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("STUDENT_ID", studentId)
        startActivity(intent)
    }
}
