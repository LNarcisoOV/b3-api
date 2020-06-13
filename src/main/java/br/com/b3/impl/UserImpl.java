package br.com.b3.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b3.model.User;
import br.com.b3.service.UserRepository;
import br.com.b3.service.UserService;

@Service
public class UserImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	public User findById(Long id) {
		List<User> userList = userRepository.findAll();
		return userList.stream().filter(u -> u.getUserId().equals(id)).findFirst().get();
	}
	
	public List<User> findByCompanyId(Long id) {
		List<User> userList = userRepository.findAll();
		return userList.stream().filter(u -> u.getCompanyId().equals(id)).collect(Collectors.toList());
	}
	
	public List<User> findByEmail(String email) {
		List<User> userList = userRepository.findAll();
		return userList.stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList());
	}

}
