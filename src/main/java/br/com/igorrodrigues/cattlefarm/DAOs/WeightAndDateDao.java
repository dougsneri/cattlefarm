package br.com.igorrodrigues.cattlefarm.DAOs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.igorrodrigues.cattlefarm.models.WeightAndDate;

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
	
	public List<WeightAndDate> listAll() {
		String jpql = "select w from WeightAndDate w";
		TypedQuery<WeightAndDate> query = manager.createQuery(jpql, WeightAndDate.class);
		return query.getResultList();
	}
}
