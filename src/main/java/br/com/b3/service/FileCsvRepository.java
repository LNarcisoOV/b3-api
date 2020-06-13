package br.com.b3.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.b3.model.FileCsv;

@Repository
public interface FileCsvRepository extends JpaRepository<FileCsv, Integer> {

}
