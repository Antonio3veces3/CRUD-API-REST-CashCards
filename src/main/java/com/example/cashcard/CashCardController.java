package com.example.cashcard;

import java.lang.reflect.*;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.util.*;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

	@Autowired
	private CashCardRepository cashCardRepository;

	public CashCardController(CashCardRepository cashCardRepository) {
		this.cashCardRepository = cashCardRepository;
	}

	@GetMapping("/")
	public ResponseEntity<String> rootPath() {
		String response = """
				{
					"message": "Welcome to cash cards API REST"
				}
				""";

		return ResponseEntity.ok(response);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CashCard>> findAll(Pageable pageable) {
		Page<CashCard> page = cashCardRepository.findAll(
				PageRequest.of(
						pageable.getPageNumber(),
						pageable.getPageSize(),
						pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))));

		return ResponseEntity.ok(page.getContent());
		// return ResponseEntity.ok(cashCardRepository.findAll());
	}

	@GetMapping("/{requestedId}")
	public ResponseEntity<Optional<CashCard>> findById(@PathVariable Integer requestedId) {
		Optional<CashCard> cashCard = cashCardRepository.findById(requestedId);
		if (cashCard.isPresent()) {
			return ResponseEntity.ok(cashCard);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("")
	private ResponseEntity<Void> AddNewCashCard(@RequestParam String username, @RequestParam Double amount,
			UriComponentsBuilder ucb) {
		try {
			CashCard newCashCard = new CashCard();
			Optional<CashCard> cashCardSearched;
			Integer newId;
			do {
				newId = (int) (Math.random() * 10000000 + 1);
				cashCardSearched = cashCardRepository.findById(newId);
			} while (cashCardSearched.isPresent());

			// newCashCard.setId((int) (Math.random() * 100000 + 1));
			newCashCard.setId(newId);
			newCashCard.setUsername(username);
			newCashCard.setAmount(amount);
			CashCard savedCashCard = cashCardRepository.save(newCashCard);
			URI locationOfNewCashCard = ucb.path("/cashcards/{id}").buildAndExpand(savedCashCard.getId()).toUri();
			return ResponseEntity.created(locationOfNewCashCard).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	// PUT
	@PutMapping("/{requestedId}")
	public ResponseEntity<Void> putCashCard(@PathVariable Integer requestedId, @RequestBody CashCard cashCardUpdate) {
		Optional<CashCard> cashCard = cashCardRepository.findById(requestedId);
		if (cashCard.isPresent()) {
			CashCard updatedCashCard = new CashCard();
			updatedCashCard.setId(cashCard.get().getId());
			updatedCashCard.setUsername(cashCardUpdate.getUsername());
			updatedCashCard.setAmount(cashCardUpdate.getAmount());
			try {
				cashCardRepository.save(updatedCashCard);
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	// PATCH
	@PatchMapping("/{requestedId}")
	public ResponseEntity<CashCard> patchCashCard(@PathVariable Integer requestedId,
			@RequestBody Map<String, Object> fields) {
		Optional<CashCard> cashCard = cashCardRepository.findById(requestedId);
		if (cashCard.isPresent()) {
			try {
				fields.forEach((key, value) -> {
					Field field = ReflectionUtils.findField(CashCard.class, (String) key);
					field.setAccessible(true);
					ReflectionUtils.setField(field, cashCard.get(), value);
				});
				cashCardRepository.save(cashCard.get());
				return ResponseEntity.ok(cashCard.get());
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}

		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCashCard(@PathVariable Integer id) {
		Optional<CashCard> cashCard = cashCardRepository.findById(id);
		if (cashCard.isPresent()) {
			cashCardRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

}
