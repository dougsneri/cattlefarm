package br.com.igorrodrigues.cattlefarm.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class PesoEData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDate data;
	private Double peso;

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
