package br.com.igorrodrigues.cattlefarm.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * This class represents one bovine of the flock
 * 
 * @author igor
 *
 */

@Entity
@DynamicUpdate
@JsonRootName(value = "bovine")
@JacksonXmlRootElement(localName="bovine")
public class Bovine extends Animal{

	@Enumerated(EnumType.STRING)
	private BovineType type;
	private BigDecimal weightArrobaFree;

	public BigDecimal getWeightArroba() {
		return new BigDecimal(this.weight / 15).setScale(2, RoundingMode.HALF_EVEN);
	}
	
	@OneToMany(mappedBy = "bovine")
	@JacksonXmlElementWrapper(localName="ListaPesosEDatas")
	private List<PesoEData> pesoEDataList;

	/**
	 * Return free carcass weight; Consider 50% of weight;
	 * 
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

	public static BigDecimal setPesoListTotal(List<Bovine> animais) {
		BigDecimal pesoTotal = new BigDecimal(0);
		for (Bovine bovine : animais) {
			bovine.setAge();
			pesoTotal = pesoTotal.add(bovine.getWeightArrobaFree()).setScale(2, RoundingMode.HALF_EVEN);
		}
		return pesoTotal;
	}
	
	public static BigDecimal setValueListTotal(List<Bovine> animais) {
		BigDecimal valueTotal = new BigDecimal(0);
		for (Bovine bovine : animais) {
			valueTotal = valueTotal.add(bovine.getValue()).setScale(2, RoundingMode.HALF_EVEN);
		}
		return valueTotal;
	}

	@Override
	public void setWeight(double weight) {
		super.setWeight(weight);
	}

	public List<PesoEData> getPesoEDataList() {
		return pesoEDataList;
	}
}
