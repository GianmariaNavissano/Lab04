package it.polito.tdp.lab04.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		Corso c = model.getCorso("01KSUPG");
		
		System.out.println(c);

	}

}
