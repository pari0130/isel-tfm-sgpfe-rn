package isel.meic.tfm.fei

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * Entry point of the application
 */
@SpringBootApplication
@ImportResource("applicationContext.xml")
@EnableAsync
class FEIApplication


@Bean
fun taskExecutor(): Executor? {
    val executor = ThreadPoolTaskExecutor()
    executor.corePoolSize = 20
    executor.maxPoolSize = 200
    executor.setQueueCapacity(1000)
    executor.setThreadNamePrefix("Async-")
    executor.initialize()
    return executor
}

fun main(args: Array<String>) {
    runApplication<FEIApplication>(*args)
}