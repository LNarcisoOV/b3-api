package br.com.b3.service;

import java.util.List;

import br.com.b3.model.User;

public interface UserService {
	public User findById(Integer id);
	
	public List<User> findByCompanyId(Integer id);
	
	public List<User> findByEmail(String email);
	
	public User findByEmailAndCompany(User user);
	
	public void validationRulesToSaveUser(User user) throws Exception;
}
