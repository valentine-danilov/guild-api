package by.danilov.wow.guild.cache

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.redisson.spring.cache.RedissonSpringCacheManager
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CacheConfiguration {

    @Bean
    open fun redisson(): RedissonClient {
        val redissonConfig = Config()
        redissonConfig.useSingleServer().address = "redis://127.0.0.1:6379"
        return Redisson.create(redissonConfig)
    }

    @Bean
    open fun cacheManager(redissonClient: RedissonClient): CacheManager {
        return RedissonSpringCacheManager(redissonClient)
    }

    @Bean("apiRequestKeyGenerator")
    open fun apiRequestKeyGenerator(): KeyGenerator {
        return ApiRequestKeyGenerator()
    }

}
