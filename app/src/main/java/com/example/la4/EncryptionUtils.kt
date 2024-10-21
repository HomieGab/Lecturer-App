import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.text.Charsets.UTF_8

object EncryptionUtils {
    private const val SECRET_KEY = "my_secret_key" // Replace with a secure key

    @OptIn(ExperimentalEncodingApi::class)
    fun encryptMessage(plaintext: String, aesKey: SecretKey, aesIv: IvParameterSpec): String {
        val plainTextByteArr = plaintext.toByteArray()
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, aesIv)
        val encrypt = cipher.doFinal(plainTextByteArr)
        return Base64.Default.encode(encrypt)
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun decryptMessage(encryptedText: String, aesKey: SecretKey, aesIv: IvParameterSpec): String {
        val textToDecrypt = Base64.Default.decode(encryptedText)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, aesKey, aesIv)
        val decrypted = cipher.doFinal(textToDecrypt)
        return String(decrypted, UTF_8)
    }

    fun hashStrSha256(str: String): String {
        val algorithm = "SHA-256"
        val hashedString = MessageDigest.getInstance(algorithm).digest(str.toByteArray(UTF_8))
        return hashedString.joinToString("") { "%02x".format(it) }
    }

    fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

    fun getFirstNChars(str: String, n: Int) = str.substring(0, n)
}
