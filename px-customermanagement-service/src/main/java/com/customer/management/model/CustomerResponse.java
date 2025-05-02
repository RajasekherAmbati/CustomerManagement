package com.customer.management.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;


@Builder
@JsonInclude
public class CustomerResponse {
	
	public CustomerResponse() {
	
	}

	public CustomerResponse(Customer customer, String message) {
		super();
		this.customer = customer;
		this.message = message;
	}



	private Customer customer;
	private String message;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
