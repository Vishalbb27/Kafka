package com.appdeveloperblog.ws.products.service;

import com.appdeveloperblog.ws.products.model.CreateListRestModel;
import com.appdeveloperblog.ws.products.model.CreateProductRestModel;

public interface ProductService {
	String createProduct(CreateProductRestModel productRestModel) throws Exception;
	
	String createProductList(CreateListRestModel listModel) throws Exception;
}
