package by.danilov.wow.guild.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.redisson.Redisson
import org.redisson.config.Config
import org.redisson.spring.cache.RedissonSpringCacheManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import java.util.concurrent.TimeUnit

@Profile("!cache-disable")
@EnableCaching
@Configuration
open class ApplicationCacheConfiguration {

    @Bean
    open fun defaultCacheManager(): CacheManager {
        val config = Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES)
        val cacheManager = CaffeineCacheManager()
        cacheManager.setCaffeine(config)
        return cacheManager
    }

    @Profile("redis")
    @Primary
    @Bean
    open fun redisCacheManager(@Value("\${cache.redis.address}") address: String): CacheManager {
        val config = Config()
        config.useSingleServer().address = address
        return RedissonSpringCacheManager(Redisson.create(config))
    }
}