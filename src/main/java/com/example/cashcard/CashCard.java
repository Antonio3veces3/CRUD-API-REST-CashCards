package com.example.cashcard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CashCard {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	private String Username;
	private Double Amount;
	
	public Integer getId() {
		return this.Id;
	}
	
	public String getUsername() {
		return this.Username;
	}
	
	public Double getAmount() {
		return this.Amount;
	}
	
	public void setId(Integer newId) {
		this.Id = newId;
	}
	
	public void setUsername(String newUsername) {
		this.Username = newUsername;
	}
	
	public void setAmount(Double newAmount) {
		this.Amount = newAmount;
	}
	
}
