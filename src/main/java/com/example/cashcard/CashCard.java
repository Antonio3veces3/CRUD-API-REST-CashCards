package com.example.cashcard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CashCard {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String username;
	private Double amount;
	
	public Integer getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	
	public void setId(Integer newId) {
		this.id = newId;
	}
	
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	public void setAmount(Double newAmount) {
		this.amount = newAmount;
	}
	
}
