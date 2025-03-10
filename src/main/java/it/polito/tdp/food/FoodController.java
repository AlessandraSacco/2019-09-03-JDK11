/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Portion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<Portion> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	
    	if(this.model.getGrafo()==null) {
    		txtResult.appendText("il grafo è vuoto");
    	}
    	
    	Portion p = boxPorzioni.getValue();
    	txtResult.appendText("\n Porzioni connesse: \n");
    	for(Portion p1: this.model.getPorzioniCollegate(p)) {
    		txtResult.appendText(p1.getPortion_display_name()+" "+p1.getPeso()+"\n");
    	}
    
    	
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String calorieDouble=txtCalorie.getText();
    	Double calories ;
    	
    	if(calorieDouble==null) {
    		txtResult.appendText("Il campo è vuoto Inserisci un numero\n");
    		
    	}
    	
    	try {
    		calories= Double.parseDouble(calorieDouble);
    		this.model.creaGrafo(calories);
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserisci un numero\n");
    	}
    	
    	
    	
    	this.boxPorzioni.getItems().clear();
    	this.boxPorzioni.getItems().addAll(this.model.listaPorzioni());
    	
    	txtResult.appendText("Grafo creato: \n");
    	txtResult.appendText("#VERTICI: "+this.model.getVertici()+"\n");
    	txtResult.appendText("#ARCHI: "+this.model.getArchi()+"\n");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
