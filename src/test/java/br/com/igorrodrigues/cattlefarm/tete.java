package br.com.igorrodrigues.cattlefarm;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.igorrodrigues.cattlefarm.models.Bovine;

public class tete {
	public static void main(String[] args) {
		Bovine bovine = new Bovine();
		bovine.setWeight(1500);
		bovine.setValue(new BigDecimal(130));
		bovine.setBirth(LocalDate.of(2010, 10, 10));
		bovine.setAge();
		System.out.println("Idade: " + bovine.getAge());
		System.out.println("Peso em KG: " + bovine.getWeight());
		System.out.println("Peso em @: " + bovine.getWeightArroba());
		System.out.println("Peso em @ Livre: " + bovine.getWeightArrobaFree());
		System.out.println("Valor: " + bovine.getValue());
	}
}
