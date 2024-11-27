package com.appdeveloperblog.ws.products.rest;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdeveloperblog.ws.products.model.CreateListRestModel;
import com.appdeveloperblog.ws.products.model.CreateProductRestModel;
import com.appdeveloperblog.ws.products.model.ErrorMessage;
import com.appdeveloperblog.ws.products.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping
	public ResponseEntity<Object> createProduct(@RequestBody CreateProductRestModel Product){
		String productId;
		try {
			productId = productService.createProduct(Product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(),e.getMessage(),"/products"));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productId);
	}
	
	@PostMapping("/productList")
	public ResponseEntity<Object> createList(@RequestBody CreateListRestModel Product){
		String productListId;
		try {
			productListId = productService.createProductList(Product);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(),e.getMessage(),"/products"));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(productListId);
	}
}
