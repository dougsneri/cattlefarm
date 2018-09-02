package br.com.igorrodrigues.cattlefarm.DAOs;

import java.time.LocalDate;
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

import br.com.igorrodrigues.cattlefarm.models.flock.Bovine;
import br.com.igorrodrigues.cattlefarm.models.flock.BovineType;
import br.com.igorrodrigues.cattlefarm.models.flock.Sex;
import br.com.igorrodrigues.cattlefarm.models.flock.StatusAnimal;

@Repository
@Transactional
public class AnimalDao {

	@PersistenceContext
	private EntityManager manager;

	/**
	 * Save or update a Bovine in DB
	 *
	 * @param bovine
	 */
	public void saveBovine(Bovine bovine) {
		if (this.find(bovine.getId()) == null)
			manager.persist(bovine);
		else
			manager.merge(bovine);
	}

	/**
	 * List all Bovines in DB
	 *
	 * @return
	 */

	public List<Bovine> listAllBovines() {
		String jpql = "select b from Bovine b where b.status = :pBovineStatus";
		TypedQuery<Bovine> query = manager.createQuery(jpql, Bovine.class);
		query.setParameter("pBovineStatus", StatusAnimal.ACTIVE);
		return query.getResultList();
	}

	/**
	 * List filtered Bovines
	 * 
	 * @param id
	 * @param sex
	 * @param type
	 * @param nick
	 * @return
	 */

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

		if (nick != null && !nick.isEmpty()) {
			conjunction = criteriaBuilder.and(criteriaBuilder.like(pathNick, "%" + nick + "%"));
		}

		TypedQuery<Bovine> typedQuery = manager.createQuery(query.where(conjunction));

		return typedQuery.getResultList();
	}

	public List<Bovine> listaComNascimentoEntre(LocalDate inicio, LocalDate fim) {
		String jpql = "select b from Bovine b where b.birth between :pInicio and :pFim and b.status = :pBovineStatus";
		TypedQuery<Bovine> query = manager.createQuery(jpql, Bovine.class);
		query.setParameter("pInicio", inicio);
		query.setParameter("pFim", fim);
		query.setParameter("pBovineStatus", StatusAnimal.ACTIVE);
		return query.getResultList();
	}

	public Bovine find(Integer id) {
		Bovine bovine = manager.find(Bovine.class, id);
		return bovine;
	}

	public void remove(Integer id) {
		Bovine bovine = find(id);
		if(bovine != null) {
			manager.remove(bovine);
		}
	}

}
