package br.com.b3.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.b3.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{

}
