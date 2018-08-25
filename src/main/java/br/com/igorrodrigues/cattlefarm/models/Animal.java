package br.com.igorrodrigues.cattlefarm.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.igorrodrigues.cattlefarm.config.LocalDateAdapter;
import br.com.igorrodrigues.cattlefarm.config.PeriodAdapter;

/**
 * This Class represent a animal of the farm
 * 
 * @author igor rodrigues
 *
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Animal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	protected Integer id;
	protected String nick;
	protected BigDecimal value;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	protected LocalDate birth;
	@XmlJavaTypeAdapter(value = PeriodAdapter.class)
	protected Period age;
	protected double weight;
	@Enumerated(EnumType.STRING)
	protected Sex sex;
	@Enumerated(EnumType.STRING)
	protected StatusAnimal status;

	public Animal() {
		status = StatusAnimal.ACTIVE;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public void setAge() {
		this.age = Period.between(birth, LocalDate.now());
	}

	public LocalDate getBirth() {
		return this.birth;
	}

	public Period getAge() {
		return age;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setStatus(StatusAnimal status) {
		this.status = status;
	}

	public StatusAnimal getStatus() {
		return status;
	}

	/**
	 * Set age for a list of animals
	 * 
	 * @param animals
	 */
	public static void setAgeOfList(List<? extends Animal> animals) {
		for (Animal animal : animals) {
			animal.setAge();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Animal other = (Animal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (sex != other.sex)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
