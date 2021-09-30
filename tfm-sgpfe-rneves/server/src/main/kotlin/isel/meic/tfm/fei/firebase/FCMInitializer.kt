package isel.meic.tfm.fei.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import isel.meic.tfm.fei.controllers.HomePageController
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException
import org.springframework.core.io.ClassPathResource
import javax.annotation.PostConstruct

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 */
@Service
class FCMInitializer {

    private val log = LoggerFactory.getLogger(FCMInitializer::class.java)

    @Value("\${app.firebase-configuration-file}")
    private val firebaseConfigPath: String? = null

    @PostConstruct
    fun initialize() {
        try {
            val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(ClassPathResource(firebaseConfigPath!!).inputStream))
                .setDatabaseUrl("https://fei-tfm-rn-ae7cc.firebaseio.com") //todo verify if i need this?
                .build()
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: IOException) {
            log.error(e.message)
        }
    }
}