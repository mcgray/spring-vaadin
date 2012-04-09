package com.lohika.rialab.todoshare.dao;

import org.springframework.stereotype.Repository;

import com.lohika.rialab.todoshare.domain.Person;

@Repository
public class PersonDaoImpl extends AbstractDao<Person, Long> implements PersonDao {

	protected PersonDaoImpl() {
		super(Person.class);

	}

}
