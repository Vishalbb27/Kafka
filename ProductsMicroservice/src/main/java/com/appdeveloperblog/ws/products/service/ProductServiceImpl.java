package com.appdeveloperblog.ws.products.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.appdeveloperblog.ws.core.ProductCreatedEvent;
import com.appdeveloperblog.ws.core.ProductListEvent;
import com.appdeveloperblog.ws.products.model.CreateListRestModel;
import com.appdeveloperblog.ws.products.model.CreateProductRestModel;

import org.springframework.kafka.support.SendResult;

@Service
public class ProductServiceImpl implements ProductService {
	
	KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate;
	KafkaTemplate<String, ProductListEvent> kafkaTemplateList;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate, KafkaTemplate<String, ProductListEvent> kafkaTemplateList) {
		this.kafkaTemplate = kafkaTemplate;
		this.kafkaTemplateList = kafkaTemplateList;
	}

	@Override
	public String createProduct(CreateProductRestModel productRestModel) throws Exception {
		System.out.println("************************************* inside create product **********************************");
		String productId = UUID.randomUUID().toString();
		
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,productRestModel.getTitle(),productRestModel.getPrice(),productRestModel.getQuantity());
		
		SendResult<String, ProductCreatedEvent> result = 
				kafkaTemplate.send("product-created-events-topic",productId,productCreatedEvent).get(); // Sending messages synchronously
		
		
		LOGGER.info((result.getRecordMetadata().toString()));
		//To implement asynchronous feature implement this
		
//		CompletableFuture<SendResult<String,ProductCreatedEvent>> future = kafkaTemplate.send("product-created-events-topics",productId,productCreatedEvent); //Asynchronous event and sends the acknowledgement when the event is completed
		
//		future.whenComplete((result,exception)-> {
//			if(exception !=null) {
//				LOGGER.error("Failed to send message: "+exception.getMessage());
//			} else {
//				LOGGER.info("Message send successfully: "+result.getRecordMetadata());
//			}
//		});
//		
//		future.join(); //To make the event synchronous
		
		return productId;
	}
	
	@Override
	public String createProductList(CreateListRestModel listModel) throws Exception {
		String productId = UUID.randomUUID().toString();
		ProductListEvent event = new ProductListEvent(productId, listModel.getTitle());
		SendResult<String, ProductListEvent> result = kafkaTemplateList.send("product-created-events-topic",productId,event).get();
		LOGGER.info((result.getRecordMetadata().toString()));
		return productId;
	}
}
