package com.appdeveloperblog.ws.emailNotification.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.appdeveloperblog.ws.core.ProductCreatedEvent;
import com.appdeveloperblog.ws.core.ProductListEvent;

@Component
@KafkaListener(topics = "product-created-events-topic") // Target for message coming from kafka topic
public class ProductCreatedEventHandler {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@KafkaHandler // This is called automatically
	public void handle(ProductCreatedEvent productCreatedEvent) {
		LOGGER.info("Received a new event: " + productCreatedEvent.getQuantity());
	}

	@KafkaHandler
	public void handleList(ProductListEvent productListEvent) {
		LOGGER.info("Received a new event" + productListEvent.getTitle());
	}
}
