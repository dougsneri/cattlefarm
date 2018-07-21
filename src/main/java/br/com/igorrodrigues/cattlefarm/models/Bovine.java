package br.com.igorrodrigues.cattlefarm.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicUpdate;

/**
 * This class represents one bovine of the flock
 * @author igor
 *
 */

@Entity
@DynamicUpdate
public class Bovine extends Animal  {
	
	private BovineType type;
	private BigDecimal weightArrobaFree;
	
	public BigDecimal getWeightArroba() {
		return  new BigDecimal(this.weight/15).setScale(2, RoundingMode.HALF_EVEN);
	}
	
	
	/**
	 * Return free carcass weight;
	 * Consider 50% of weight;
	 * @return
	 */
	
	public BigDecimal getWeightArrobaFree() {
		return this.weightArrobaFree;
	}
	
	public void setWeightArrobaFree() {
		this.weightArrobaFree = getWeightArroba().divide(new BigDecimal(2)).setScale(2, RoundingMode.HALF_EVEN);
	}
	
	public void setType(BovineType type) {
		this.type = type;
	}
	
	public BovineType getType() {
		return type;
	}
	
	/**
	 * return the value based in the arroba value; 
	 */
	
	@Override
	public void setValue(BigDecimal valuePerArroba) {
		this.setWeightArrobaFree();
		this.value = getWeightArrobaFree().multiply(valuePerArroba);		
	}
	
	@Override
	public BigDecimal getValue() {
		return this.value;
	}
	
}
