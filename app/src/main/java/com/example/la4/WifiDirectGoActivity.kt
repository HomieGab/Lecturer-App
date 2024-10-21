package com.example.la4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class WifiDirectGoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_direct_go)

        val startClassButton: Button = findViewById(R.id.permissions_button3)
        startClassButton.setOnClickListener {
            // Start class logic
        }
    }
}
