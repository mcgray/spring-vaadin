package ua.com.mcgray.springvaadin.dao;

import org.springframework.stereotype.Repository;

import ua.com.mcgray.springvaadin.domain.Person;


@Repository
public class PersonDaoImpl extends AbstractDao<Person, Long> implements PersonDao {

	protected PersonDaoImpl() {
		super(Person.class);

	}

}
