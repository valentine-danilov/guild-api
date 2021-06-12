package by.danilov.wow.guild.monitoring

import io.javalin.plugin.metrics.MicrometerPlugin
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAspectJAutoProxy
open class MonitoringConfiguration {

    @Bean
    open fun prometheusRegistry(): PrometheusMeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    @Bean
    open fun prometheusPlugin(prometheusRegistry: MeterRegistry): MicrometerPlugin =
        MicrometerPlugin(prometheusRegistry)

    @Bean
    open fun timedAspect(prometheusRegistry: MeterRegistry): TimedAspect {
        return TimedAspect(prometheusRegistry)
    }
 }