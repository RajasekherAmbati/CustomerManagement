package com.customer.management.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {
	private Long id;
	private String name;
	private String email;
	private Double annualSpend;
	private LocalDateTime lastPurchaseDate;
	private String tier;
	
	public Customer() {
	
	}

	public Customer(Long id, String name, String email, Double annualSpend, LocalDateTime lastPurchaseDate,
			String tier) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.annualSpend = annualSpend;
		this.lastPurchaseDate = lastPurchaseDate;
		this.tier = tier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

}