package ua.com.mcgray.springvaadin.dao;

import java.util.List;

import ua.com.mcgray.springvaadin.domain.Person;


public interface PersonDao {

	List<Person> findAll();

	void save(Person person);

	void update(Person person);

	void delete(Person person);

	Person find(Long id);

}
