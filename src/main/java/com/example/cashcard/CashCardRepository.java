package com.example.cashcard;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;


public interface CashCardRepository extends CrudRepository<CashCard, Integer>, PagingAndSortingRepository<CashCard, Integer>{
	// Add el findById Method
	public Optional<CashCard> findById(Integer Id);

	
}
