package ua.com.mcgray.springvaadin.dao;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDao<T, PK extends Serializable> extends JpaDaoSupport {
	protected final String entityName;
	private final Class<T> entityClass;

	protected AbstractDao(Class<T> entityClass) {
		this.entityClass = entityClass;
		entityName = getEntityName(entityClass);
	}

	private String getEntityName(Class<T> entityClass) {
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity != null) {
			String entityName = entity.name();
			if (entityName.isEmpty()) {
				return entityClass.getSimpleName();
			}

			return entityName;
		} else {
			return "";
		}
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return find("SELECT entity FROM " + entityName + " entity");
	}

	@Transactional(readOnly = true)
	public T find(PK id) {
		return getJpaTemplate().find(entityClass, id);
	}

	@Transactional
	public void save(T entity) {
		getJpaTemplate().persist(entity);
	}

	@Transactional
	public void delete(T entity) {
		getJpaTemplate().remove(entity);
	}

	@Transactional
	public void update(T entity) {
		getJpaTemplate().merge(entity);
	}

	@SuppressWarnings("unchecked")
	protected final List<T> find(String queryString, Object... values) {
		return notNullList(getJpaTemplate().find(queryString, values));
	}

	protected String getEntityName() {
		return entityName;
	}

	@SuppressWarnings("unchecked")
	protected T find(String namedQuery, Map<String, ?> params) {
		EntityManager entityManager = getJpaTemplate().getEntityManager();

		Query query = entityManager.createNamedQuery(namedQuery);
		for (String paramName : params.keySet()) {
			query.setParameter(paramName, params.get(paramName));
		}

		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByNamedParams(String query, Map<String, Object> params) {
		return notNullList(getJpaTemplate().findByNamedParams(query, params));
	}

	@SuppressWarnings("unused")
	@PersistenceContext
	private void provideEntityManager(EntityManager entityManager) {
		setEntityManager(entityManager);
	}

	private List<T> notNullList(List<T> result) {
		return result == null ? Collections.<T> emptyList() : result;
	}
}
