package com.example.la4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val messageInput: EditText = findViewById(R.id.message_input)
        val sendButton: Button = findViewById(R.id.send_button)
        val chatLog: TextView = findViewById(R.id.chat_log)

        sendButton.setOnClickListener {
            val message = messageInput.text.toString()
            val encryptedMessage = EncryptionUtils.encryptMessage(message)
            // Send the encrypted message
            sendMessage(encryptedMessage, chatLog)
        }

        // Example of receiving an encrypted message
        val receivedEncryptedMessage = "..." // Replace with actual received message
        val decryptedMessage = EncryptionUtils.decryptMessage(receivedEncryptedMessage)
        chatLog.append("Student: $decryptedMessage\n")
    }

    private fun sendMessage(encryptedMessage: String, chatLog: TextView) {
        // Logic to send the encrypted message
        chatLog.append("Lecturer: $encryptedMessage\n")
    }
}
