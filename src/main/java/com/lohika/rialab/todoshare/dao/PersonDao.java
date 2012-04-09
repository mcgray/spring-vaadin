package com.lohika.rialab.todoshare.dao;

import java.util.List;

import com.lohika.rialab.todoshare.domain.Person;

public interface PersonDao {

	List<Person> findAll();

	void save(Person person);

	void update(Person person);

	void delete(Person person);

	Person find(Long id);

}
