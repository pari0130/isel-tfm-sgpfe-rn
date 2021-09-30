package isel.meic.tfm.fei.security.utils

import java.nio.charset.Charset
import javax.crypto.Cipher

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class EncryptionDecryptionManager {

    private val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private val SECRET_KEY_HASH_TRANSFORMATION = "SHA-256"
    private val CHARSET = Charset.forName("UTF-8")

    private var encryptKeys: Boolean = false
    private lateinit var writer: Cipher
    private lateinit var reader: Cipher


    companion object{


    }
}