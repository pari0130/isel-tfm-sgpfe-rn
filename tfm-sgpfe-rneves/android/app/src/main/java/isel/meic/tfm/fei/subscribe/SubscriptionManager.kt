package isel.meic.tfm.fei.subscribe

import isel.meic.tfm.fei.FEIApplication

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
class SubscriptionManager private constructor(val application: FEIApplication) {

    private var subscriptions: HashMap<String, Int> = HashMap()

    companion object {

        private var instance: SubscriptionManager? = null

        fun getInstance(application: FEIApplication): SubscriptionManager {
            if (instance == null)
                instance = SubscriptionManager(application)
            return instance!!
        }
    }

    fun addSubscription(key: String) {
        if (subscriptions.containsKey(key))
            subscriptions[key] = subscriptions[key]!! + 1
        else
            subscriptions[key] = 1
    }

    fun removeSubscription(key: String, remove: () -> Unit) {
        if (subscriptions.containsKey(key) && subscriptions[key]!! > 1) {
            subscriptions[key] = subscriptions[key]!! - 1
        } else {
            subscriptions.remove(key)
            remove()
        }
    }
}