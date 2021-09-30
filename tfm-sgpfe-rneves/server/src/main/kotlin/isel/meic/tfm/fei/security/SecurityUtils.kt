package isel.meic.tfm.fei.security

import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * Temp Class used for password encoding and decoding
 * //TODO clean up method that are not being used
 */
class SecurityUtils {

    companion object {
        private const val OFFSET = 4
        private val RANDOM = SecureRandom()

        private var secretKey: SecretKeySpec? = null
        private lateinit var key: ByteArray

        //TODO Change this. This is Temporary!!
        private const val secret = "thesecret"

        fun encodeBase64(input: String): String? {
            return Base64.getEncoder().encodeToString(input.toByteArray())
        }

        fun decodeBase64(encoded: String?): String? {
            val decodedBytes = Base64.getDecoder().decode(encoded)
            return String(decodedBytes)
        }

        fun caesarCipherEncrypt(plain: String): String? {
            val b64encoded = Base64.getEncoder().encodeToString(plain.toByteArray())
            val reverse = StringBuffer(b64encoded).reverse().toString()
            val tmp = StringBuilder()
            for (element in reverse) tmp.append((element.toInt() + OFFSET).toChar())
            return tmp.toString()
        }

        fun caesarCipherDecrypt(secret: String): String? {
            val tmp = StringBuilder()
            for (element in secret) tmp.append((element.toInt() - OFFSET).toChar())
            val reversed = StringBuffer(tmp.toString()).reverse().toString()
            return String(Base64.getDecoder().decode(reversed))
        }

        private fun setKey(secret: String) {
            var sha: MessageDigest? = null
            try {
                key = secret.toByteArray(charset("UTF-8"))
                sha = MessageDigest.getInstance("SHA-1")
                key = sha.digest(key)
                key = key.copyOf(16)
                secretKey = SecretKeySpec(key, "AES")
            } catch (e: NoSuchAlgorithmException) {
            } catch (e: UnsupportedEncodingException) {
            }
        }

        fun encrypt(plain: String): String? {
            try {
                setKey(secret)
                val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                return Base64.getEncoder().encodeToString(cipher.doFinal(plain.toByteArray(StandardCharsets.UTF_8)))
            } catch (e: Exception) {
            }
            return null
        }

        fun decrypt(strToDecrypt: String?, secret: String?): String? {
            try {
                setKey(secret!!)
                val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
                cipher.init(Cipher.DECRYPT_MODE, secretKey)
                return String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)))
            } catch (e: java.lang.Exception) {
            }
            return null
        }

        fun computeSHA256Hash(input: String): String {
            return try {
                val messageDigest = MessageDigest.getInstance("SHA-256")
                val hash = messageDigest.digest(input.toByteArray(StandardCharsets.UTF_8))
                DatatypeConverter.printHexBinary(hash)
            } catch (e: NoSuchAlgorithmException) {
                //TODO fix this
                input
            }
        }

        fun computePassword(salt: String, password: String): String {
            return computeSHA256Hash(password + computeSHA256Hash(salt))
        }

        fun computePasswordWithEncodedSalt(encodedSalt: String, password: String): String {
            return computeSHA256Hash(password + encodedSalt)
        }

        fun generateRandomSalt(): String {
            val salt = ByteArray(16)
            RANDOM.nextBytes(salt)
            return Base64.getEncoder().encodeToString(salt)
        }
    }
}