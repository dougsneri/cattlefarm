package br.com.igorrodrigues.cattlefarm.DAOs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.igorrodrigues.cattlefarm.models.flock.Bovine;
import br.com.igorrodrigues.cattlefarm.models.flock.WeightAndDate;

@Transactional
@Repository
public class WeightAndDateDao {

	@PersistenceContext
	private EntityManager manager;

	/**
	 * Save a Weight and Date on DB
	 * 
	 * @param weightAndDate
	 */
	
	public void save(WeightAndDate weightAndDate) {
		if (weightAndDate.getId() == null)
			manager.persist(weightAndDate);
		else
			manager.merge(weightAndDate);;
	}
	
	public WeightAndDate find(Integer id) {
		return manager.find(WeightAndDate.class, id);
	}
	
	public void remove(Integer id) {
		WeightAndDate weightAndDate = find(id);
		manager.remove(weightAndDate);
	}
	
	/**
	 * Remove all WeightAndDate from a bovine
	 * @param Bovine id
	 */
	public void removeAllFromBovine(Bovine bovine) {
		String jpql = "delete from WeightAndDate w where w.bovine.id = :pBovineId";
		Query query = manager.createQuery(jpql);
		query.setParameter("pBovineId", bovine.getId());
		query.executeUpdate();
	}
	
	public List<WeightAndDate> selectAllFromBovine(Integer id) {
		String jpql = "select w from WeightAndDate w where w.bovine.id = :pBovineId";
		TypedQuery<WeightAndDate> query = manager.createQuery(jpql, WeightAndDate.class);
		query.setParameter("pBovineId", id);
		return query.getResultList();
	}
	
	public List<WeightAndDate> listAll() {
		String jpql = "select w from WeightAndDate w";
		TypedQuery<WeightAndDate> query = manager.createQuery(jpql, WeightAndDate.class);
		return query.getResultList();
	}
}
