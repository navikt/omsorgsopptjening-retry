package no.nav.pensjon.opptjening.omsorgsopptjening.retry.kafka

import io.micrometer.core.instrument.MeterRegistry
import no.nav.pensjon.opptjening.omsorgsopptjening.felles.domene.kafka.KafkaMessageType
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class OmsorgsarbeidListener(
    registry: MeterRegistry,
    val omsorgsOpptjeningProducer: OmsorgsOpptjeningProducer
) {

    private val antallLesteMeldinger = registry.counter("omsorgsArbeidListener", "antall", "lest")

    @KafkaListener(
        id = "omsorgsarbeidListener",
        autoStartup = "true",
        containerFactory = "omsorgsArbeidKafkaListenerContainerFactory",
        idIsGroup = false,
        topics = ["\${OMSORGSOPPTJENING_TOPIC}"],
        groupId = "\${OMSORGSOPPTJENING_RETRY_GROUP_ID}"
    )
    fun consumeOmsorgPGodskriving(
        hendelse: String,
        consumerRecord: ConsumerRecord<String, String>,
        acknowledgment: Acknowledgment
    ) {
        antallLesteMeldinger.increment()
        SECURE_LOG.info("Konsumerer omsorgsmelding: ${consumerRecord.key()}, ${consumerRecord.value()}")

        if (consumerRecord.kafkaMessageType() == KafkaMessageType.RETRY) {
            omsorgsOpptjeningProducer.publiserOmsorgsopptejning(consumerRecord)
        }
        acknowledgment.acknowledge()
    }

    companion object {
        private val SECURE_LOG = LoggerFactory.getLogger("secure")
    }
}