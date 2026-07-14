package com.semicolon.medibookuserservice.config;

import com.semicolon.medibookuserservice.event.events.UserRegisteredEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
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
   public DefaultKafkaConsumerFactory<String, UserRegisteredEvent> consumerFactory() {
      JacksonJsonDeserializer<UserRegisteredEvent> jacksonDeserializer = new JacksonJsonDeserializer<>(
              UserRegisteredEvent.class
      );

      jacksonDeserializer.addTrustedPackages("com.semicolon.*");

      ErrorHandlingDeserializer<UserRegisteredEvent> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(jacksonDeserializer);

      Map<String, Object> configProps = Map.of(
              ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
              ConsumerConfig.GROUP_ID_CONFIG, "user-service-group"
      );

      return new DefaultKafkaConsumerFactory<>(
              configProps,
              new StringDeserializer(),
              errorHandlingDeserializer
      );
   }



   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> kafkaListenerContainerFactory() {
      var factory = new ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
   }
}
