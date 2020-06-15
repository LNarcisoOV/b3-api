package br.com.b3.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.b3.model.User;
import br.com.b3.service.UserRepository;
import br.com.b3.service.UserService;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@PostMapping(path = "/")
	public ResponseEntity<Object> save(@Valid @RequestBody User user) {
		try {
			userService.validationRulesToSaveUser(user) ;
			User userdb = userRepository.save(user);
			return new ResponseEntity<Object>(userdb, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/")
	public ResponseEntity<Object> getAll() {
		List<User> userList = userRepository.findAll();
		return new ResponseEntity<Object>(userList, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		User user = userService.findById(id);
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
		userRepository.deleteById(id.intValue());
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PutMapping(path = "/")
	public ResponseEntity<Object> update(@Valid @RequestBody User user) {
		try {
			userService.validationRulesToSaveUser(user) ;
			User userdb = userRepository.save(user);
			return new ResponseEntity<Object>(userdb, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/company/{companyId}")
	public ResponseEntity<Object> getByCompanyId(@PathVariable Integer companyId) {
		List<User> userList = userService.findByCompanyId(companyId);
		return new ResponseEntity<Object>(userList, HttpStatus.OK);
	}

	@GetMapping(path = "/email/{email}")
	public ResponseEntity<Object> getByCompanyEmail(@PathVariable String email) {
		List<User> userList = userService.findByEmail(email);
		return new ResponseEntity<Object>(userList, HttpStatus.OK);
	}
}
