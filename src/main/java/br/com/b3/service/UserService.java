package br.com.b3.service;

import java.util.List;

import br.com.b3.model.User;

public interface UserService {
	public User findById(Long id);
	
	public List<User> findByCompanyId(Long id);
	
	public List<User> findByEmail(String email);
	
	public User findByEmailAndCompany(User user);
	
	public void validationRulesToSaveUser(User user) throws Exception;
}
