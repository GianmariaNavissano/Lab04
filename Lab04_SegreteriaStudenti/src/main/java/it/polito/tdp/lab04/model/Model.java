package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.DAO.CorsoDAO;

public class Model {
	
	private CorsoDAO DAO;
	
	public Model() {
		DAO = new CorsoDAO();
	}
	
	public List<Corso> getTuttiICorsi(){
		return DAO.getTuttiICorsi();
	}
	
	public List<String> getNomiCorsi(){
		List<Corso> corsi = DAO.getTuttiICorsi();
		List<String> nomiCorsi = new LinkedList<String>();
		for(Corso c : corsi) {
			nomiCorsi.add(c.getNome());
		}
		return nomiCorsi;
	}
	
	public Corso getCorso(String codins) {
		return DAO.getCorso(codins);
	}
	
	public Studente getStudente(int matricola) {
		Map<Integer, Studente> studenti = DAO.getStudenti();
		return studenti.get(matricola);
	}
	
	public List<Studente> getStudentiCorso(String nomeCorso){
		List<Corso> corsi = DAO.getTuttiICorsi();
		for(Corso c : corsi) {
			if(c.getNome().equals(nomeCorso))
				return DAO.getStudentiIscrittiAlCorso(c);
		}
		return null;
	}
	
	public List<Corso> getCorsiStudente(Studente s){
		return DAO.getCorsiStudente(s);
	}
	
	public boolean isIscritto(Studente s, String nomeCorso) {
		
		List<Corso> corsi = this.getCorsiStudente(s);
		for(Corso c : corsi) {
			if(c.getNome().equals(nomeCorso))
				return true;
		}
		return false;
	}

}
