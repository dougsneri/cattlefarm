package br.com.igorrodrigues.cattlefarm.models.financial;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
/**
 * This class calculates the balance from a movement list
 * @author igor
 *
 */
public class CalculateMovements {
	
	private List<FinancialMovement> movementList;
	private BigDecimal balance;
	
	public CalculateMovements() {
		movementList = new ArrayList<>();
		balance = new BigDecimal(0);
	}
	
	public void addMovement(FinancialMovement movement) {
		movementList.add(movement);
	}
	
	public void calculateBalance() {
		movementList.forEach(m -> {
			if (m.getType() == MovementType.INCOME)
				balance = balance.add(m.getValue());
			else
				balance = balance.subtract(m.getValue());
		});
	}
	
	public BigDecimal getBalance() {
		return balance.setScale(2, RoundingMode.HALF_EVEN);
	}

}
