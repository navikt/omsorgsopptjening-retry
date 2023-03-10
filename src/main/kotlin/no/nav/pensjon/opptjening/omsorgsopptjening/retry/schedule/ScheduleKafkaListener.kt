package no.nav.pensjon.opptjening.omsorgsopptjening.retry.schedule

import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalTime


@Component
class ScheduleKafkaListener(private val kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry)
{
    @Scheduled(cron = "20 10 * * * *")
    fun startListener() {
        print("Starter omsorgsarbeidListener ${LocalTime.now()}")
        kafkaListenerEndpointRegistry.getListenerContainer("omsorgsarbeidListener")!!.start()
    }

    @Scheduled(cron = "25 10 * * * *")
    fun stopListener() {
        print("Starter omsorgsarbeidListener ${LocalTime.now()}")
        kafkaListenerEndpointRegistry.getListenerContainer("omsorgsarbeidListener")!!.stop()
    }
}