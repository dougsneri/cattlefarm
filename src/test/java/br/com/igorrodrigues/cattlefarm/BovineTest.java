package br.com.igorrodrigues.cattlefarm;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BovineTest {

	@Test
	public void arrobacaoVacaVsOutros() {
		Bovine vaca = new Bovine();
		vaca.setType(BovineType.VACA);
		vaca.setWeight(300);

		Bovine boi = new Bovine();
		boi.setType(BovineType.BOI);
		boi.setWeight(300);

		assertEquals(new BigDecimal(9).setScale(2, RoundingMode.HALF_EVEN), vaca.getWeightArrobaFree());
		assertEquals(new BigDecimal(10).setScale(2, RoundingMode.HALF_EVEN), boi.getWeightArrobaFree());
	}
	
	@Test
	public void ageAnosEMeses() {
		Bovine bovine = new Bovine();
		bovine.setBirth(LocalDate.of(2015, 10, 3));
		bovine.setAge();
		Period age = bovine.getAge();
		System.out.println(age);
		System.out.println(age.getYears());
		System.out.println(age.getMonths());
	}
	
}
