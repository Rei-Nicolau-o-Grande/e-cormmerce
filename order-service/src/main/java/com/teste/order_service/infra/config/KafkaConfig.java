package com.teste.order_service.infra.config;

import com.teste.order_service.infra.dtos.OrderResponseDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    private static final Integer PARTITION_COUNT = 1;
    private static final Integer REPLICA_COUNT = 1;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic.order-created}")
    private String orderCreatedTopic;

    @Value("${spring.kafka.topic.order-confirmed}")
    private String orderConfirmedTopic;

    @Value("${spring.kafka.topic.order-fail}")
    private String orderFailTopic;

    @Bean
    public ProducerFactory<String, OrderResponseDto> producerFactory() {
        Map<String, Object> producerConfigs = new HashMap<>();

        producerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerConfigs.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaProducerFactory<>(producerConfigs);
    }

    @Bean
    public KafkaTemplate<String, OrderResponseDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    private NewTopic createTopic(String topicName) {
        return TopicBuilder
                .name(topicName)
                .partitions(PARTITION_COUNT)
                .replicas(REPLICA_COUNT)
                .compact()
                .build();
    }

    @Bean
    public NewTopic orderCreatedTopic() {
        return createTopic(orderCreatedTopic);
    }

    @Bean
    public NewTopic orderConfirmedTopic() {
        return createTopic(orderConfirmedTopic);
    }

    @Bean
    public NewTopic orderCanceledTopic() {
        return createTopic(orderFailTopic);
    }

}
