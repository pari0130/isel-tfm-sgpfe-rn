package isel.meic.tfm.fei.security.utils

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * Temp Class used for password encoding and decoding
 */
class SecurityUtils {

    companion object{
        private const val OFFSET = 4

        fun encodeBase64(input: String): String {
            //return Base64.encodeToString(input.toByteArray(StandardCharsets.UTF_8),  Base64.DEFAULT)
            return Base64.encodeToString(input.toByteArray(StandardCharsets.UTF_8),  Base64.NO_WRAP)
            //return Base64.getEncoder().encodeToString(input.toByteArray())
        }

        fun decodeBase64(encoded: String?): String {
            val decodedBytes = Base64.decode(encoded, Base64.NO_WRAP)
            //val decodedBytes = Base64.getDecoder().decode(encoded)
            return String(decodedBytes, StandardCharsets.UTF_8)
        }

        fun caesarCipherEncrypt(input: String): String {
            val reverse = StringBuffer(encodeBase64(input)).reverse().toString()
            val tmp = StringBuilder()
            for (element in reverse) tmp.append((element.toInt() + OFFSET).toChar())
            return tmp.toString()
        }

        fun caesarCipherDecrypt(encoded: String): String {
            val tmp = StringBuilder()
            for (element in encoded) tmp.append((element.toInt() - OFFSET).toChar())
            val reversed = StringBuffer(tmp.toString()).reverse().toString()
            return decodeBase64(reversed)
        }

        fun computeSHA256Hash(input: String): String {
            return try {
                val messageDigest = MessageDigest.getInstance("SHA-256")
                val hash = messageDigest.digest(input.toByteArray(StandardCharsets.UTF_8))
                val stringBuilder = java.lang.StringBuilder()
                for (byte in hash) {
                    stringBuilder.append(String.format("%02x", byte))
                }
                return stringBuilder.toString().toUpperCase(Locale.getDefault())
            } catch (e: NoSuchAlgorithmException) {
                input
            }
        }
    }
}