package com.example.la4

import java.security.MessageDigest

object EncryptionUtils {
    private const val SECRET_KEY = "my_secret_key" // Replace with a secure key

    fun encryptMessage(plaintext: String): String {
        return xorWithKey(plaintext)
    }

    fun decryptMessage(encryptedText: String): String {
        return xorWithKey(encryptedText)
    }

    fun hashStrSha256(str: String): String {
        val algorithm = "SHA-256"
        val hashedString = MessageDigest.getInstance(algorithm).digest(str.toByteArray(Charsets.UTF_8))
        return hashedString.joinToString("") { "%02x".format(it) }
    }

    private fun xorWithKey(input: String): String {
        val key = SECRET_KEY
        val output = StringBuilder()
        for (i in input.indices) {
            output.append(input[i].code.xor(key[i % key.length].code).toChar())
        }
        return output.toString()
    }
}
