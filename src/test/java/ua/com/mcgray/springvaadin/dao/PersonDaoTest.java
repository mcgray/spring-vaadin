package ua.com.mcgray.springvaadin.dao;

import static org.fest.assertions.Assertions.assertThat;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.mcgray.springvaadin.dao.PersonDao;
import ua.com.mcgray.springvaadin.domain.Person;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
@Transactional
public class PersonDaoTest {

	@Resource
	private PersonDao personDaoImpl;

	private Person testPerson;

	@Before
	public void setUp() {
		testPerson = new Person();
		testPerson.setFirstName("Alex");
		testPerson.setLastName("McGray");
		testPerson.setPhoneNumber("2222222");
		testPerson.setCity("Kyiv");
		testPerson.setStreet("Street");
		testPerson.setZipCode("02131");
	}

	@Test
	public void saveTest() {
		personDaoImpl.save(testPerson);
	}

	@Test
	public void loadTest() {
		Person person = personDaoImpl.find(2l);
		assertThat(person).isNotNull();
		assertThat(person.getFirstName()).isEqualTo("Marc");
		assertThat(person.getBoss()).isNotNull();
		assertThat(person.getBoss().getId()).isEqualTo(1l);

	}

}
