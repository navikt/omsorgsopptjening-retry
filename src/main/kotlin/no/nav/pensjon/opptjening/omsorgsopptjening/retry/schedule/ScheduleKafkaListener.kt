package no.nav.pensjon.opptjening.omsorgsopptjening.retry.schedule

import org.slf4j.LoggerFactory
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalTime


@Component
class ScheduleKafkaListener(
    private val kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry
) {
    @Scheduled(cron = "10 15 * * * *")
    fun startListener() {
        LOGGER.info("Starter omsorgsarbeidListener ${LocalTime.now()}")
        kafkaListenerEndpointRegistry.getListenerContainer("omsorgsarbeidListener")!!.start()
    }

    @Scheduled(cron = "11 15 * * * *")
    fun stopListener() {
        LOGGER.info("Starter omsorgsarbeidListener ${LocalTime.now()}")
        kafkaListenerEndpointRegistry.getListenerContainer("omsorgsarbeidListener")!!.stop()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ScheduleKafkaListener::class.java)
    }
}