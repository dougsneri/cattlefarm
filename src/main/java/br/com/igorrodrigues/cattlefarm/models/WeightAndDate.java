package br.com.igorrodrigues.cattlefarm.models;

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

	public Integer getId() {
		return id;
	}

}
