package p.lawniczek.harvester;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HelloController {

    private final KafkaProducer kafkaProducer;

    @GetMapping(value = "/{msg}")
    public void sendMessageToKafkaTopic(@PathVariable("msg") String message) {
        log.info("msg: {}", message);
        kafkaProducer.sendMessage(message);
    }
}
