package br.com.igorrodrigues.cattlefarm.DAOs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.igorrodrigues.cattlefarm.models.Animal;
import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;
import br.com.igorrodrigues.cattlefarm.models.Sex;
import br.com.igorrodrigues.cattlefarm.models.StatusAnimal;

@Repository
@Transactional
public class AnimalDao {

	@PersistenceContext
	private EntityManager manager;

	/**
	 * Save a animal in DB
	 * 
	 * @param animal
	 */
	public void save(Animal animal) {
		if(animal.getId() == null) 
			manager.persist(animal);
		else
			manager.merge(animal);
	}

	/**
	 * List all Bovines in DB
	 * 
	 * @return
	 */

	public List<Bovine> listarTodosBovinos() {
		String jpql = "select b from Bovine b where b.status = :bovineStatus";
		TypedQuery<Bovine> query = manager.createQuery(jpql, Bovine.class);
		query.setParameter("bovineStatus", StatusAnimal.ACTIVE);
		return query.getResultList();
	}

	public List<Bovine> listarBovinos(Integer id, Sex sex, BovineType type, String nick) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Bovine> query = criteriaBuilder.createQuery(Bovine.class);
		Root<Bovine> root = query.from(Bovine.class);

		Path<Integer> pathId = root.get("id");
		Path<Sex> pathSex = root.get("sex");
		Path<BovineType> pathType = root.get("type");
		Path<String> pathNick = root.get("nick");
		Path<StatusAnimal> pathStatus = root.get("status");

		Predicate conjunction = criteriaBuilder.conjunction();
		conjunction = criteriaBuilder.and(criteriaBuilder.equal(pathStatus, StatusAnimal.ACTIVE));

		if (id != null) {
			Predicate IdIgual = criteriaBuilder.equal(pathId, id);
			conjunction = criteriaBuilder.and(IdIgual);
		}

		if (sex != null) {
			conjunction = criteriaBuilder.and(criteriaBuilder.equal(pathSex, sex));
		}

		if (type != null) {
			conjunction = criteriaBuilder.and(criteriaBuilder.equal(pathType, type));
		}

		if (!nick.isEmpty()) {
			conjunction = criteriaBuilder.and(criteriaBuilder.like(pathNick, "%" + nick + "%"));
		}

		TypedQuery<Bovine> typedQuery = manager.createQuery(query.where(conjunction));

		return typedQuery.getResultList();
	}

	public BigDecimal setPesoTotal(List<Bovine> animais) {
		BigDecimal pesoTotal = new BigDecimal(0);
		for (Bovine bovine : animais) {
			bovine.setAge();
			pesoTotal = pesoTotal.add(bovine.getWeightArrobaFree()).setScale(2, RoundingMode.HALF_EVEN);
		}
		return pesoTotal;
	}
	
	public void setAge(List<Bovine> animais) {
		for (Bovine bovine : animais) {
			bovine.setAge();
		}
	}

	public Bovine find(Integer id) {
		Bovine bovine = manager.find(Bovine.class, id);
		return bovine;
	}
	
	public void remove(Integer id) {
		Bovine bovine = find(id);
		manager.remove(bovine);
	}

}
