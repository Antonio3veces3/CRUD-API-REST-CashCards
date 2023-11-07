package com.example.cashcard;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface CashCardRepository extends CrudRepository<CashCard, Integer>{
	// Add el findById Method
	public Optional<CashCard> findById(Integer Id);
}
