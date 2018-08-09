package br.com.igorrodrigues.cattlefarm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.igorrodrigues.cattlefarm.models.Bovine;

public class TesteSetValueInList {
	
	public static void main(String[] args) {
		Bovine bovine = new Bovine();
		bovine.setWeight(150.0);
		Bovine bovine1 = new Bovine();
		bovine1.setWeight(150.0);
		Bovine bovine2 = new Bovine();
		bovine2.setWeight(150.0);
		
		List<Bovine> lista = new ArrayList<>();
		
		lista.add(bovine);
		lista.add(bovine1);
		lista.add(bovine2);
		BigDecimal valueListTotal = new BigDecimal(0);
		for (Bovine bovinelista : lista) {
			bovinelista.setValue(new BigDecimal(130));
			System.out.println(bovinelista.getValue());
		}
		valueListTotal = Bovine.setValueListTotal(lista);
		System.out.println(valueListTotal);
	}

}
