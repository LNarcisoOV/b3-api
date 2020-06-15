package br.com.b3.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b3.model.User;
import br.com.b3.service.UserRepository;
import br.com.b3.service.UserService;

@Service
public class UserImpl implements UserService {

	private static final List<Integer> VALID_COMPANY_IDS = Arrays.asList(new Integer[] { 1, 2, 5, 7, 10 });
	private static final int MAX_EMAIL_SIZE = 255;
	private static final String DATE_PATTERN = "dd/MM/yyyy";

	@Autowired
	private UserRepository userRepository;

	public User findById(Integer id) {
		List<User> userList = userRepository.findAll();
		return userList.stream().filter(u -> u.getUserId().equals(id)).findFirst().get();
	}

	public List<User> findByCompanyId(Integer companyId) {
		List<User> userList = userRepository.findAll();
		return userList.stream().filter(u -> u.getCompanyId().equals(companyId)).collect(Collectors.toList());
	}

	public List<User> findByEmail(String email) {
		List<User> userList = userRepository.findAll();
		return userList.stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList());
	}

	public User findByEmailAndCompany(User user) {
		List<User> userList = userRepository.findAll();
		Optional<User> optionalUser = userList.stream()
				.filter(u -> u.getEmail().equals(user.getEmail()) && u.getCompanyId().equals(user.getCompanyId()))
				.findAny();
		return optionalUser.orElse(null);
	}

	public void validationRulesToSaveUser(User user) throws Exception {
		duplicityValidation(user);
		companyIdsValidation(user);
		emailSizeValidation(user);
		datePatternValidation(user);
	}

	private void datePatternValidation(User user) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
		try {
			df.parse(df.format(user.getBirthdate()));
		} catch (ParseException ex) {
			throw new Exception("Birthdate out of the pattern.");
		}
	}

	private void duplicityValidation(User user) throws Exception {
		User userDB = findByEmailAndCompany(user);
		if (userDB != null) {
			throw new Exception("User '"+ user.getEmail() +"' is already registered in the company '" + user.getCompanyId() + "'.");
		}
	}

	private void companyIdsValidation(User user) throws Exception {
		if (!VALID_COMPANY_IDS.contains(user.getCompanyId())) {
			throw new Exception("Invalid company id: '"+ user.getCompanyId() +"'.");
		}
	}

	private void emailSizeValidation(User user) throws Exception {
		if (user.getEmail().length() > MAX_EMAIL_SIZE) {
			throw new Exception("Email bigger than permited.");
		}
	}

}
