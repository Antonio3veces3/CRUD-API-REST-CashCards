package com.example.cashcard;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

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
					"message": "Welcome to cash cards APP"
				}
				""";

		return ResponseEntity.ok(response);
	}

	@GetMapping("/all")
	public ResponseEntity<Iterable<CashCard>> findAll() {
		return ResponseEntity.ok(cashCardRepository.findAll());
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
	private ResponseEntity<Void> AddNewCashCard(@RequestParam String username, @RequestParam Double amount, UriComponentsBuilder ucb) {
		try {
			//faltan validaciones de si ya existe ese ID
			CashCard newCashCard = new CashCard();
			newCashCard.setId((int)(Math.random()*100000+1));
			newCashCard.setUsername(username);
			newCashCard.setAmount(amount);
			System.out.println("new ID: " + newCashCard.getId());
			CashCard savedCashCard = cashCardRepository.save(newCashCard);
			URI locationOfNewCashCard = ucb.path("/cashcards/{id}").buildAndExpand(savedCashCard.getId()).toUri();
			return ResponseEntity.created(locationOfNewCashCard).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build(); 
		}

	}
	//PUT
	@PutMapping("/{requestedId}")
	public ResponseEntity<Void> putCashCard(@PathVariable Integer requestedId, @RequestBody CashCard cashCardUpdate){
		// Faltan validaciones
		Optional<CashCard> cashCard = cashCardRepository.findById(requestedId);
		CashCard updatedCashCard = new CashCard();
		updatedCashCard.setId(cashCard.get().getId());
		updatedCashCard.setUsername(cashCardUpdate.getUsername());
		updatedCashCard.setAmount(cashCardUpdate.getAmount());
		cashCardRepository.save(updatedCashCard);
		return ResponseEntity.noContent().build();
	}

	//PATCH 

	//DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCashCard(@PathVariable Integer id){
		cashCardRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	} 

}
