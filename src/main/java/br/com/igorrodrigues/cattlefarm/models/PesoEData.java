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
public class PesoEData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	private LocalDate data;
	private Double peso;
	@JsonIgnore
	@ManyToOne
	private Bovine bovine;

	@Deprecated
	public PesoEData() {

	}

	public PesoEData(LocalDate data, Double peso, Bovine bovine) {
		this.data = data;
		this.peso = peso;
		this.bovine = bovine;
	}

	public LocalDate getData() {
		return data;
	}

	public Double getPeso() {
		return peso;
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
