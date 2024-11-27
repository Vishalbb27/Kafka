package com.appdeveloperblog.ws.products;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.appdeveloperblog.ws.core.ProductCreatedEvent;
import com.appdeveloperblog.ws.core.ProductListEvent;



@Configuration
public class KafkaConfig {
	
	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${spring.kafka.producer.key-serializer}")
	private String keySerializer;
	
	@Value("${spring.kafka.producer.value-serializer}")
	private String valueSerializer;

	@Value("${spring.kafka.producer.acks}")
	private String acks;

	@Value("${spring.kafka.producer.properties.delivery.timout.ms}")
	private String deliveryTimeout;

	@Value("${spring.kafka.producer.properties.linger.ms}")
	private String Linger;

	@Value("${spring.kafka.producer.properties.request.timeout.ms}")
	private String requestTimeout;
	
//	@Value("${spring.kafka.producer.properties.enable.idempotence}")
//	private boolean idempotence;
	
	@Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection}")
	private Integer inflightRequests;

	Map<String,Object> producerConfigs(){
		Map<String,Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer );
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
		config.put(ProducerConfig.ACKS_CONFIG, "all");
		config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
		config.put(ProducerConfig.LINGER_MS_CONFIG, Linger);
		config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
		config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
		config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, inflightRequests);
		return config;
	}
	
	@Bean
	ProducerFactory<String, ProductCreatedEvent> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
	@Bean
	KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate(){
		return new KafkaTemplate<String, ProductCreatedEvent>(producerFactory());
	}
	
	@Bean
	ProducerFactory<String, ProductListEvent> producerFactoryList(){
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
	@Bean
	KafkaTemplate<String, ProductListEvent> kafkaTemplateList(){
		return new KafkaTemplate<String, ProductListEvent>(producerFactoryList());
	}
	
	@Bean //Adds to application context inorder to be available for other services
	NewTopic createTopic() {
		return TopicBuilder.name("product-created-events-topics")
				.partitions(3)
				.replicas(3) //Increase the storage 
				.configs(Map.of("min.insync.replicas","2"))//Minimum 2 replicas must acknowledge read and write operations
				.build();
	}
}
