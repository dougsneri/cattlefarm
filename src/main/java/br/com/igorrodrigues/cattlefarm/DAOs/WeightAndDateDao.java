package br.com.igorrodrigues.cattlefarm.DAOs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	 * @param weightAndDate
	 */
	public void save(WeightAndDate weightAndDate) {
		if (weightAndDate.getId() == null)
			manager.persist(weightAndDate);
		else
			manager.merge(weightAndDate);
	}
}
