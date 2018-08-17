package br.com.igorrodrigues.cattlefarm.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@JsonPropertyOrder({ "id", "nick", "value", "birth", "age", "weight", "sex", "status", "type", "weightArrobaFree",
		"pesoEDataList", "weightArroba" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Bovine extends Animal {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private BovineType type;

	private BigDecimal weightArrobaFree;

	public BigDecimal getWeightArroba() {
		return new BigDecimal(this.weight / 15).setScale(2, RoundingMode.HALF_EVEN);
	}

	@OneToMany(mappedBy = "bovine")
	@JacksonXmlElementWrapper(localName = "ListaPesosEDatas")
	@JsonIgnore
	private List<WeightAndDate> pesoEDataList;

	public BigDecimal getWeightArrobaFree() {
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

	public BovineType getType() {
		return type;
	}

	/**
	 * Calculates the value based in the arroba value;
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

	/**
	 * return total weight from a list of bovines
	 * 
	 * @param animals
	 * @return
	 */
	public static BigDecimal setWeightListTotal(List<Bovine> animals) {
		BigDecimal pesoTotal = new BigDecimal(0);
		for (Bovine bovine : animals) {
			pesoTotal = pesoTotal.add(bovine.getWeightArrobaFree()).setScale(2, RoundingMode.HALF_EVEN);
		}
		return pesoTotal;
	}
	
	/**
	 * return total value from a list of bovines
	 * @param animais
	 * @return
	 */

	public static BigDecimal setValueListTotal(List<Bovine> animais) {
		BigDecimal valueTotal = new BigDecimal(0);
		for (Bovine bovine : animais) {
			valueTotal = valueTotal.add(bovine.getValue()).setScale(2, RoundingMode.HALF_EVEN);
		}
		return valueTotal;
	}

	public List<WeightAndDate> getPesoEDataList() {
		return pesoEDataList;
	}
}
