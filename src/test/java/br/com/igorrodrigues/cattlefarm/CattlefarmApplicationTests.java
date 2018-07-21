package br.com.igorrodrigues.cattlefarm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.igorrodrigues.cattlefarm.models.Bovine;
import br.com.igorrodrigues.cattlefarm.models.BovineType;
import br.com.igorrodrigues.cattlefarm.models.Sex;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CattlefarmApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testaBovino() {
		Bovine bovine = new Bovine();
		bovine.setId(123);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		bovine.setBirth(LocalDate.parse("10/10/2010", formatter));
		bovine.setSex(Sex.FEMALE);
		bovine.setType(BovineType.VACA);
		bovine.setWeight(1500);
		bovine.setWeightArrobaFree();
		System.out.println(bovine.getValue());
		Assert.assertEquals(1500, bovine.getWeight(), 0);
		Assert.assertEquals(new BigDecimal(50.00), bovine.getWeightArrobaFree());
	}

}
