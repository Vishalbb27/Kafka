package com.appdeveloperblog.ws.core;

import java.math.BigDecimal;

public class ProductListEvent {
	private String productId;
	private String title;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ProductListEvent(String productId, String title) {

		this.productId = productId;
		this.title = title;
	}

	public ProductListEvent() {

	}

}
