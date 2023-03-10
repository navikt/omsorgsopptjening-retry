package no.nav.pensjon.opptjening.omsorgsopptjening.retry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}