package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboCorsi;

    @FXML
    private TextField matricolaTxt;

    @FXML
    private TextField nomeStudenteTxt;

    @FXML
    private TextField cognomeTxt;

    @FXML
    private TextArea resultTxt;
    
    /*@FXML
    void handleMatricola(ActionEvent event) {
    	if(this.matricolaTxt.getText()!=null)
    		this.comboCorsi.setDisable(false);
    }*/

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	if(!this.matricolaTxt.getText().isEmpty()) {
    		
    		this.resultTxt.clear();
    		try {
    			int matricola = Integer.parseInt(this.matricolaTxt.getText());
    			Studente s = model.getStudente(matricola);
    			if(s!=null) {
    				List<Corso> corsiStudente = this.model.getCorsiStudente(s);
    				
    				
    				//Caso corso non selezionato nel menu a tendina
    				if(this.comboCorsi.getValue()==null) {
    					if(corsiStudente!=null) {
    						for(Corso c : corsiStudente) {
            					this.resultTxt.appendText(c.toString()+"\n");
            				}
    					}
    					else this.resultTxt.setText("Studente non iscritto ad alcun corso");
    				}
    				
    				//Caso corso selezionato nel menu a tendina
    				else { 
    					if(this.model.isIscritto(s, this.comboCorsi.getValue()))
    						this.resultTxt.setText("Studente già iscritto al corso");
    					else this.resultTxt.setText("Studente non iscritto al corso");
    				}
    			} else {
    				this.resultTxt.setText("Matricola non corretta");
    			}
    		} catch(NumberFormatException nfe) {
    			this.resultTxt.setText("Inserire una matricola numerica");
    			throw new RuntimeException("Error matricola", nfe);
    		}
    		
    	} else this.resultTxt.setText("Matricola assente");

    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	if(this.comboCorsi.getValue()!=null) {
    		List<Studente> studenti = this.model.getStudentiCorso(this.comboCorsi.getValue());
    		if(studenti==null) 
    			this.resultTxt.setText("Il corso non ha iscritti");
    		else {
    			this.resultTxt.clear();
    			for(Studente s : studenti) {
    				this.resultTxt.appendText(s.toString()+"\n");
    			}
    		}
    	}else {
    		this.resultTxt.setText("Selezionare un corso");
    	}
    }

    @FXML
    void doCompletamento(ActionEvent event) {
    	if(!this.matricolaTxt.getText().isEmpty()) {
    		try {
    			int matricola = Integer.parseInt(matricolaTxt.getText());
    			Studente s = model.getStudente(matricola);
    			if(s!=null) {
    				this.cognomeTxt.setText(s.getCognome());
        			this.nomeStudenteTxt.setText(s.getNome());
    			} else {
    				this.resultTxt.setText("Matricola non corretta");
    			}
    		} catch(NumberFormatException nfe) {
    			this.resultTxt.setText("Errore: inserire una matricola numerica");
    			throw new RuntimeException("Error input matricola", nfe);
    		}
    		
    	} else {
    		this.resultTxt.setText("Matricola assente");
    	}
    }

    @FXML
    void doIscrivi(ActionEvent event) {

    }

    @FXML
    void doReset(ActionEvent event) {
    	this.comboCorsi.setValue(null);
    	this.matricolaTxt.clear();
    	this.nomeStudenteTxt.clear();
    	this.cognomeTxt.clear();
    	this.resultTxt.clear();
    }

    @FXML
    void initialize() {
        assert comboCorsi != null : "fx:id=\"comboCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert matricolaTxt != null : "fx:id=\"matricolaTxt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert nomeStudenteTxt != null : "fx:id=\"nomeStudenteTxt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cognomeTxt != null : "fx:id=\"cognomeTxt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert resultTxt != null : "fx:id=\"resultTxt\" was not injected: check your FXML file 'Scene.fxml'.";
        
        

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
    
    public void setCorsi() {
    	List<String> nomiCorsi = model.getNomiCorsi();
        for(String nome : nomiCorsi) {
        	comboCorsi.getItems().add(nome);
        }
        this.comboCorsi.getItems().add("");
    }
}
