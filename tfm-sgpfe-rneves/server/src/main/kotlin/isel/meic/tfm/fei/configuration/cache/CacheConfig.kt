package isel.meic.tfm.fei.configuration.cache

import com.google.common.cache.CacheBuilder
import org.apache.tomcat.util.collections.ConcurrentCache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * Instituto Superior de Engenharia de Lisboa
 * Project: Smart Queue
 * @author Ronilton Neves - 39643
 * @mentor: Paulo Pereira
 *
 * <Description>
 */
@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager? {
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(
            listOf(
                ConcurrentMapCache(
                    "session",/*
                    CacheBuilder.newBuilder().expireAfterWrite(4, TimeUnit.HOURS).build<Any, Any>().asMap(),*/
                    false
                ),
                ConcurrentMapCache("forecast", false)
            )
        )
        return cacheManager
    }
}