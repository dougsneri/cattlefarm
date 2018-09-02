package br.com.igorrodrigues.cattlefarm.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.igorrodrigues.cattlefarm.models.flock.Bovine;
import br.com.igorrodrigues.cattlefarm.models.flock.BovineType;

@SpringBootTest
public class BovineTest {

	private Bovine bovine;
	
	@Before
	public void iniciaBovineTeste() {
		this.bovine = new Bovine();
	}

	@Test
	public void arrobacaoVacaVsOutros() {
		Bovine vaca = new Bovine();
		vaca.setType(BovineType.VACA);
		vaca.setWeight(300);

		Bovine boi = new Bovine();
		boi.setType(BovineType.BOI);
		boi.setWeight(300);

		assertThat(vaca.getWeightArrobaFree(), equalTo(new BigDecimal(9).setScale(2, RoundingMode.HALF_EVEN)));
		assertThat(boi.getWeightArrobaFree(), equalTo(new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN)));
	}

	@Test
	public void ageAnosEMeses() {
		bovine.setBirth(LocalDate.of(2015, 10, 3));
		bovine.setAge();
		assertThat(bovine.getAge().getYears(), equalTo(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testaValueMenorQueZero() {
		bovine.setValue(new BigDecimal(-5));
	}
	
	

}
