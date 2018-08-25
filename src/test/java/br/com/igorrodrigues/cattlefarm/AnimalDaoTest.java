package br.com.igorrodrigues.cattlefarm;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.igorrodrigues.cattlefarm.DAOs.AnimalDao;
import br.com.igorrodrigues.cattlefarm.DAOs.WeightAndDateDao;
import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;
import br.com.igorrodrigues.cattlefarm.models.Sex;
import br.com.igorrodrigues.cattlefarm.models.WeightAndDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnimalDaoTest {

	@Autowired
	private AnimalDao bovineDao;
	@Autowired
	private WeightAndDateDao weightAndDateDao;

	private Bovine bovinoDeTeste() {
		Bovine bovine = new Bovine();
		bovine.setId(12);
		bovine.setBirth(LocalDate.of(2017, 7, 20));
		bovine.setNick("Doze");
		bovine.setSex(Sex.MALE);
		bovine.setType(BovineType.BOI);
		bovine.setWeight(500);
		bovine.setWeightArrobaFree();
		bovineDao.saveBovine(bovine);
		return bovine;
	}

	@Test
	public void testaSave() {

		Bovine bovine = bovinoDeTeste();

		WeightAndDate weightAndDate = new WeightAndDate(LocalDate.now(), bovine.getWeight(), bovine);
		weightAndDateDao.save(weightAndDate);

		Bovine bovine2 = bovineDao.find(12);
		int id = weightAndDateDao.find(11).getBovine().getId();
		
		assertEquals(12, id);
		assertEquals(bovine2, bovine);

	}
}
