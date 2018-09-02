package br.com.igorrodrigues.cattlefarm.models;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.igorrodrigues.cattlefarm.models.financial.CalculateMovements;
import br.com.igorrodrigues.cattlefarm.models.financial.FinancialMovement;
import br.com.igorrodrigues.cattlefarm.models.financial.MovementType;

@SpringBootTest
public class FinancialMovementTest {
	
	@Test
	public void deveCalcularResultante() {
		FinancialMovement movement = new FinancialMovement();
		movement.setType(MovementType.EXPENSIVE);
		movement.setValue(new BigDecimal(1500.00));
		movement.setDescription("Salário Vaqueiro");
		movement.setDate(LocalDate.now());
		
		FinancialMovement movement2 = new FinancialMovement();
		movement2.setType(MovementType.INCOME);
		movement2.setValue(new BigDecimal(3000.00));
		movement2.setDescription("Venda de novilhos");
		movement2.setDate(LocalDate.now());
		
		FinancialMovement movement3 = new FinancialMovement();
		movement3.setType(MovementType.EXPENSIVE);
		movement3.setValue(new BigDecimal(3000.00));
		movement3.setDescription("Gastos e mais gastos");
		movement3.setDate(LocalDate.now());
		
		CalculateMovements calculateMovements = new CalculateMovements();
		calculateMovements.addMovement(movement);
		calculateMovements.addMovement(movement2);
		calculateMovements.addMovement(movement3);
		calculateMovements.calculateBalance();
		BigDecimal balance = calculateMovements.getBalance();
		
		assertThat(balance, equalTo(new BigDecimal(-1500.00).setScale(2, RoundingMode.HALF_EVEN)));
	}
	
}
