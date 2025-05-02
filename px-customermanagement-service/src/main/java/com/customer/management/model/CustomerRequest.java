package com.customer.management.model;

import java.time.LocalDateTime;

import lombok.Builder;



@Builder
public class CustomerRequest {

	private String name;
	private String email;
	private Double annualSpend;
	private LocalDateTime lastPurchaseDate;

	public CustomerRequest() {

	}

	public CustomerRequest(String name, String email, Double annualSpend, LocalDateTime lastPurchaseDate) {
		super();
		this.name = name;
		this.email = email;
		this.annualSpend = annualSpend;
		this.lastPurchaseDate = lastPurchaseDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getAnnualSpend() {
		return annualSpend;
	}

	public void setAnnualSpend(Double annualSpend) {
		this.annualSpend = annualSpend;
	}

	public LocalDateTime getLastPurchaseDate() {
		return lastPurchaseDate;
	}

	public void setLastPurchaseDate(LocalDateTime lastPurchaseDate) {
		this.lastPurchaseDate = lastPurchaseDate;
	}

}
