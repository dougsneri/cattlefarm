package br.com.igorrodrigues.cattlefarm.models.flock;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.igorrodrigues.cattlefarm.config.LocalDateAdapter;

@Entity
@DynamicUpdate
public class WeightAndDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	private LocalDate date;
	private Double weight;
	private BigDecimal weightArrobaFree;
	@JsonIgnore
	@ManyToOne
	private Bovine bovine;

	@Deprecated
	public WeightAndDate() {

	}

	public WeightAndDate(LocalDate date, Double weight, Bovine bovine) {
		this.date = date;
		this.weight = weight;
		this.bovine = bovine;
		this.weightArrobaFree = bovine.getWeightArrobaFree();
	}

	public LocalDate getDate() {
		return date;
	}

	public Double getWeight() {
		return weight;
	}

	public void setBovine(Bovine bovine) {
		this.bovine = bovine;
	}

	public Bovine getBovine() {
		return bovine;
	}

	public BigDecimal getWeightArrobaFree() {
		return weightArrobaFree;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bovine == null) ? 0 : bovine.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeightAndDate other = (WeightAndDate) obj;
		if (bovine == null) {
			if (other.bovine != null)
				return false;
		} else if (!bovine.equals(other.bovine))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

}
