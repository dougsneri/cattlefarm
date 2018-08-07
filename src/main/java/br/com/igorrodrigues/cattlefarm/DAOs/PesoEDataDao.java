package br.com.igorrodrigues.cattlefarm.DAOs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.igorrodrigues.cattlefarm.models.PesoEData;

@Transactional
@Repository
public class PesoEDataDao {

	@PersistenceContext
	private EntityManager manager;

	public void save(PesoEData pesoEData) {
		if (pesoEData.getId() == null)
			manager.persist(pesoEData);
		else
			manager.merge(pesoEData);
	}
}
