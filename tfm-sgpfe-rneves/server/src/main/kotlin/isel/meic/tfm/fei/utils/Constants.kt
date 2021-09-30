package isel.meic.tfm.fei.utils

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class Constants {
    companion object{
        const val SIREN_CLASS = "\$siren4j.class"

    }
    class MediaType {
        companion object {
            const val JSON_SIREN = "application/vnd.siren+json"
        }
    }

    class ErrorType { //
        companion object{
            val TICKET_NOT_AVAILABLE = Pair(1, "The ticket is not available") //this is not quite an error
            val SERVICE_DOES_NOT_EXIST = Pair(2, "The service does not exist")
            val QUEUE_DOES_NOT_EXIST = Pair(3, "The queue does not exist")

        }
    }
}