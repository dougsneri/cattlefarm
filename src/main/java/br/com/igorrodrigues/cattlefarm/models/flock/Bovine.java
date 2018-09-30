package br.com.igorrodrigues.cattlefarm.models.flock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JacksonXmlRootElement(namespace = "bovine")
@JsonRootName(value = "bovine")
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "id", "nick", "value", "birth", "age", "weight", "sex", "status", "type", "weightArrobaFree",
		"pesoEDataList", "weightArroba", "countChild" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Bovine extends Animal {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private BovineType type;
	private BigDecimal weightArrobaFree;
	private int countChild;
	@OneToMany(mappedBy = "bovine")
	@JacksonXmlElementWrapper(localName = "ListaPesosEDatas")
	@JsonIgnore
	private List<WeightAndDate> pesoEDataList;

	/**
	 * Return weigth in Arroba
	 * 
	 * @return
	 */
	public BigDecimal getWeightArroba() {
		// vaca desconta 30kg
		if (this.type == BovineType.VACA) {
			return new BigDecimal((this.weight - 30) / 15).setScale(2, RoundingMode.HALF_EVEN);
		} else {
			return new BigDecimal(this.weight / 15).setScale(2, RoundingMode.HALF_EVEN);
		}
	}

	public BigDecimal getWeightArrobaFree() {
		this.setWeightArrobaFree();
		return this.weightArrobaFree;
	}

	/**
	 * Calculates the carcass free weight, carcass: Considered 50% of weight;
	 */

	public void setWeightArrobaFree() {
		this.weightArrobaFree = getWeightArroba().divide(new BigDecimal(2)).setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setType(BovineType type) {
		this.type = type;
	}

	/**
	 * set Type according to age
	 */
	public void setType() {
		long months = ChronoUnit.MONTHS.between(birth, LocalDate.now());

		if (this.type == BovineType.TOURO) {
			return;
		}

		if (months <= 18) {
			this.type = BovineType.BEZERRO;
		} else if (months < 30 && this.countChild == 0) {
			this.type = BovineType.NOVILHO;
		} else if (months >= 30 && (this.countChild > 0 || this.sex == Sex.FEMALE)) {
			this.type = BovineType.VACA;
		} else {
			this.type = BovineType.BOI;
		}
	}

	public BovineType getType() {
		return type;
	}

	/**
	 * Calculates the value based in the arroba value;
	 */

	@Override
	public void setValue(BigDecimal valuePerArroba) {
		if (valuePerArroba.compareTo(BigDecimal.ZERO) >= 0) {
			this.setWeightArrobaFree();
			this.value = getWeightArrobaFree().multiply(valuePerArroba);
		} else
			throw new IllegalArgumentException("Valor da arroba menor que Zero!");
	}

	public List<WeightAndDate> getPesoEDataList() {
		return pesoEDataList;
	}

	public void setCountChild(int countChild) {
		this.countChild = countChild;
	}

	public int getCountChild() {
		return countChild;
	}

	/**
	 * return total value from a list of bovines
	 * 
	 * @param animals
	 * @return
	 */
	public static BigDecimal getValueListTotal(List<Bovine> animals) {
		double valueTotal = animals.stream().mapToDouble(b -> b.getValue().doubleValue()).sum();
		return new BigDecimal(valueTotal).setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * return total weight from a list of bovines
	 * 
	 * @param animals
	 * @return
	 */
	public static BigDecimal getWeightListTotal(List<Bovine> animals) {
		double pesoTotalDouble = animals.stream().mapToDouble(b -> b.getWeightArrobaFree().doubleValue()).sum();
		return new BigDecimal(pesoTotalDouble).setScale(2, RoundingMode.HALF_EVEN);
	}
}
