package com.appdeveloperblog.ws.products.model;

public class CreateListRestModel {
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

	public CreateListRestModel(String productId, String title) {
		super();
		this.productId = productId;
		this.title = title;
	}

}
