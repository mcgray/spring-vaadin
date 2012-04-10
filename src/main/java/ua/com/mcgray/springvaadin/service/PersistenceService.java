package ua.com.mcgray.springvaadin.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true, dependencyCheck = true)
public class PersistenceService {

	private EntityManager entityManager;

	@SuppressWarnings("unused")
	@PersistenceContext
	private void provideEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
