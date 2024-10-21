package com.example.la4

import EncryptionUtils
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec


class ChatActivity : AppCompatActivity() {
    private lateinit var aesKey: SecretKey
    private lateinit var aesIv: IvParameterSpec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        aesKey = getAesKey()
        aesIv = getAesIv()

        val messageInput: EditText = findViewById(R.id.message_input)
        val sendButton: Button = findViewById(R.id.send_button)
        val chatLog: TextView = findViewById(R.id.chat_log)

        sendButton.setOnClickListener {
            val message = messageInput.text.toString()
            val encryptedMessage = EncryptionUtils.encryptMessage(message, aesKey, aesIv)
            sendMessage(encryptedMessage, chatLog)
        }

        val receivedEncryptedMessage = "..."
        val decryptedMessage =
            EncryptionUtils.decryptMessage(receivedEncryptedMessage, aesKey, aesIv)
        chatLog.append("Student: $decryptedMessage\n")
    }

    private fun sendMessage(encryptedMessage: String, chatLog: TextView) {
        chatLog.append("Lecturer: $encryptedMessage\n")
    }

    private fun getAesKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    private fun getAesIv(): IvParameterSpec {
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        return IvParameterSpec(iv)
    }
}
