package br.com.invillia.cdb.wallet.application.consumer;

import br.com.invillia.cdb.wallet.application.TradingService;
import br.com.invillia.cdb.wallet.domain.Paper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerService {

    @Value("${topic.name}")
    private static final String cdbTopic = "${topic.name}";

    private final ObjectMapper objectMapper;

    private final TradingService tradingService;

    @Autowired
    public ConsumerService(ObjectMapper objectMapper, TradingService tradingService) {
        this.objectMapper = objectMapper;
        this.tradingService = tradingService;
    }

    @KafkaListener(topics = cdbTopic)
    public void consumeMessage(String message) {
        log.info("Received message: {}", message);

        try {
            Paper paper = objectMapper.readValue(message, Paper.class);
            log.info(String.format("Deserialized Paper: {\"customerDocument\": %s, \"amount\": %s}", paper.getCustomerDocument(), paper.getAmount()));
            tradingService.persistTrading(paper);
            log.info("message consumed {}", message);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize message: {}", e.getMessage());
            log.info("message not consumed {}", message);
        }

    }
}
