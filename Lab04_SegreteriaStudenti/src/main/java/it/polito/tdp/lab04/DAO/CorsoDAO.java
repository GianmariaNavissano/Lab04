package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				//System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(c);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codins) {
		// TODO
		String sql = "SELECT * FROM corso"
				+ " where codins = ?";
		
		Corso corso;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
	
			rs.next();
			corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
			conn.close();
			return corso;
			
		} catch(SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
		
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		String sql = "SELECT s.matricola, s.nome, s.cognome, s.CDS"
				+ " FROM studente s, iscrizione i"
				+ " WHERE s.matricola = i.matricola and i.codins = ?";
		
		List<Studente> studenti = new LinkedList<Studente>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("s.matricola"), rs.getString("s.cognome"), rs.getString("s.nome"), rs.getString("s.CDS"));
				studenti.add(s);
			}
			
			conn.close();
			return studenti;
			
		} catch(SQLException e) {
			throw new RuntimeException ("Error db", e);
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		return false;
	}
	
	public Map<Integer, Studente> getStudenti() {
		
		String sql = "SELECT * FROM studente";
		Map<Integer, Studente> studenti = new HashMap<Integer, Studente>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				studenti.put(rs.getInt("matricola"), s);
			}
			conn.close();
			
			return studenti;
			
		} catch(SQLException e) {
			throw new RuntimeException("Errore db", e);
		}
	}
	
	public List<Corso> getCorsiStudente(Studente s){
		String sql = "SELECT c.codins, c. crediti, c.nome, c.pd"
				+ " FROM corso c, iscrizione i"
				+ " WHERE c.codins = i.codins and i.matricola = ?";
		
		List<Corso> corsiStudente = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, s.getMatricola());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("c.codins"), rs.getInt("c.crediti"), rs.getString("c.nome"), rs.getInt("c.pd"));
				corsiStudente.add(c);
			}
			
			conn.close();
			return corsiStudente;
			
		}catch(SQLException e) {
			throw new RuntimeException ("Error db", e);
		}
	}

}
