package br.com.igorrodrigues.cattlefarm.models.financial;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * This class represent a financial movement
 * @author igor
 *
 */
@Entity
public class FinancialMovement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	private MovementType type;
	private BigDecimal value;
	private String description;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate date;
	
	public Integer getId() {
		return id;
	}
	
	public void setType(MovementType type) {
		this.type = type;
	}
	
	public MovementType getType() {
		return type;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalDate getDate() {
		return date;
	}
}
