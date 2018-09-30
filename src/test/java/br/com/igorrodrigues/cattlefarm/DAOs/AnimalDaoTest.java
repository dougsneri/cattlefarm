package br.com.igorrodrigues.cattlefarm.DAOs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.igorrodrigues.cattlefarm.models.flock.Bovine;
import br.com.igorrodrigues.cattlefarm.models.flock.BovineType;
import br.com.igorrodrigues.cattlefarm.models.flock.Sex;
import br.com.igorrodrigues.cattlefarm.models.flock.WeightAndDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AnimalDaoTest {

	@Autowired
	private AnimalDao animalDao;
	@Autowired
	private WeightAndDateDao weightAndDateDao;

	private Bovine bovineDeTeste1;
	private Bovine bovineDeTeste2;
	private WeightAndDate weightAndDateDeTeste1;
	private WeightAndDate weightAndDateDeTeste2;

	@Before
	public void bovineAndWeightAndDateTest() {
		bovineDeTeste1 = new Bovine();
		bovineDeTeste1.setId(12);
		bovineDeTeste1.setBirth(LocalDate.of(2017, 7, 20));
		bovineDeTeste1.setNick("Doze");
		bovineDeTeste1.setSex(Sex.MALE);
		bovineDeTeste1.setType(BovineType.BOI);
		bovineDeTeste1.setWeight(500);
		bovineDeTeste1.setWeightArrobaFree();

		bovineDeTeste2 = new Bovine();
		bovineDeTeste2.setId(13);
		bovineDeTeste2.setBirth(LocalDate.of(2015, 7, 20));
		bovineDeTeste2.setNick("Treze");
		bovineDeTeste2.setSex(Sex.MALE);
		bovineDeTeste2.setType(BovineType.BOI);
		bovineDeTeste2.setWeight(600);
		bovineDeTeste2.setWeightArrobaFree();

		animalDao.saveBovine(bovineDeTeste1);
		animalDao.saveBovine(bovineDeTeste2);

		weightAndDateDeTeste1 = new WeightAndDate(LocalDate.now(), bovineDeTeste1.getWeight(), bovineDeTeste1);
		weightAndDateDeTeste2 = new WeightAndDate(LocalDate.now(), bovineDeTeste2.getWeight(), bovineDeTeste2);

		weightAndDateDao.save(weightAndDateDeTeste1);
		weightAndDateDao.save(weightAndDateDeTeste2);

	}

	@After
	public void deletaBovinosAdicionados() {
		animalDao.remove(bovineDeTeste1.getId());
		animalDao.remove(bovineDeTeste2.getId());
		weightAndDateDao.removeAllFromBovine(bovineDeTeste1);
		weightAndDateDao.removeAllFromBovine(bovineDeTeste2);
	}

	@Test
	public void testaSaveComMock() {
		AnimalDao mockAnimalDao = Mockito.mock(AnimalDao.class);
		Mockito.when(mockAnimalDao.find(bovineDeTeste1.getId())).thenReturn(bovineDeTeste1);

		WeightAndDateDao mockWeightAndDateDao = Mockito.mock(WeightAndDateDao.class);
		Mockito.when(mockWeightAndDateDao.find(1)).thenReturn(weightAndDateDeTeste1);

		Bovine bovine2 = mockAnimalDao.find(bovineDeTeste1.getId());
		assertThat(bovine2, equalTo(bovineDeTeste1));
		assertThat(mockWeightAndDateDao.find(1).getBovine().getId(), equalTo(bovineDeTeste1.getId()));

	}

	@Test
	public void testaRemoveComMock() {
		AnimalDao mockAnimalDao = Mockito.mock(AnimalDao.class);
		mockAnimalDao.remove(bovineDeTeste1.getId());
		Mockito.verify(mockAnimalDao, Mockito.times(1)).remove(bovineDeTeste1.getId());
	}

	@Test
	public void testaSaveNoDB() {
		animalDao.saveBovine(bovineDeTeste1);
		Bovine bovine2 = animalDao.find(bovineDeTeste1.getId());
		assertThat(bovine2, equalTo(bovineDeTeste1));

	}

	@Test
	public void testaRemoveNoDB() {
		animalDao.remove(bovineDeTeste1.getId());
		Bovine bovine = animalDao.find(bovineDeTeste1.getId());
		assertNull(bovine);
	}

	@Test
	public void testaBuscaEntreDuasDatas() {

		List<Bovine> listaComNascimentoEntre = animalDao.listaComNascimentoEntre(LocalDate.of(2014, Month.JANUARY, 5),
				LocalDate.now());
		assertEquals(2, listaComNascimentoEntre.size());

		List<Bovine> listaComNascimentoEntre2 = animalDao.listaComNascimentoEntre(LocalDate.of(2016, Month.JANUARY, 5),
				LocalDate.now());
		assertEquals(1, listaComNascimentoEntre2.size());

	}
	
	@Test
	public void testeBuscandoListChaveValor() {
		List<?> teste = animalDao.teste();
		teste.forEach(s -> System.out.println(s.toString()));
	}

}
