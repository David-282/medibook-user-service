package com.semicolon.medibookuserservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfig {

   @Bean
   public ProducerFactory<String, Object> producerFactory() {
      return new DefaultKafkaProducerFactory<>(Map.of(
              ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
              ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
              ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class
      ));
   }

   @Bean
   public KafkaTemplate<String, Object> kafkaTemplate() {
      return new KafkaTemplate<>(producerFactory());
   }

   @Bean
   public ConsumerFactory<String, Object> consumerFactory() {
      return new DefaultKafkaConsumerFactory<>(Map.of(
              ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
              ConsumerConfig.GROUP_ID_CONFIG, "user-service-group",
              ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
              ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class,
              JacksonJsonDeserializer.TRUSTED_PACKAGES, "com.semicolon.medibookuserservice.event"
      ));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
      var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
   }
}
