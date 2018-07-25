package br.com.igorrodrigues.cattlefarm.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * This Class represent a animal of the farm
 * @author igor rodrigues
 *
 */

@Entity 
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Animal {
	
	@Id
	protected Integer id;
	protected String nick;
	protected BigDecimal value;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	protected LocalDate birth;
	protected Period age;
	protected double weight;
	protected Sex sex;
	protected StatusAnimal status = StatusAnimal.ACTIVE;
	
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
		this.age = Period.between(birth, LocalDate.now());;
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

}
