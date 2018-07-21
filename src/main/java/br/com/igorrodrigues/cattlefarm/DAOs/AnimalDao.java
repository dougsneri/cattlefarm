package br.com.igorrodrigues.cattlefarm.DAOs;

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
		manager.persist(animal);
	}

	/**
	 * List all Bovines in DB
	 * 
	 * @return
	 */

	public List<Bovine> listarTodosBovinos() {
		System.out.println("Buscando todos Bovinos");
		return manager.createQuery("select b from Bovine b", Bovine.class).getResultList();
	}

	public List<Bovine> listarBovinos(Integer id, Sex sex, BovineType type, String nick) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Bovine> query = criteriaBuilder.createQuery(Bovine.class);
		Root<Bovine> root = query.from(Bovine.class);

		Path<Integer> pathId = root.get("id");
		Path<Sex> pathSex = root.get("sex");
		Path<BovineType> pathType = root.get("type");
		Path<String> pathNick = root.get("nick");

		Predicate conjunction = criteriaBuilder.conjunction();

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

}
