package it.polito.TdP.country;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.TdP.country.model.Country;
import it.polito.TdP.country.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CountryController {
	
	private Model model;
	
	 public void setModel(Model model) {
			this.model = model;
			
			//riempo la tendina con elenco completo delle nazioni
			List<Country> list = model.getCountries();
			list.sort(Comparator.comparing(Country::getStateNme));
			boxPartenza.getItems().addAll(list);
		}

    @FXML
    private ResourceBundle resources;

	@FXML
    private URL location;

    @FXML
    private ComboBox<Country> boxPartenza;

    @FXML
    private ComboBox<Country> boxDestinazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void handlePercorso(ActionEvent event) {
    	Country destinazione = boxDestinazione.getValue();
    	if(destinazione == null) {
    		txtResult.appendText("Selezionare uno stato di destinazione.\n");
    		return;
    	}
    	List<Country> percorso = model.getPercorso(destinazione);
    	txtResult.clear();
    	txtResult.appendText(percorso.toString());

    }

    @FXML
    void handleRaggiungibili(ActionEvent event) {
    	Country partenza = boxPartenza.getValue();
    	if(partenza == null) {
    		txtResult.appendText("Selezionare lo stato di partenza.\n");
    		return;
    	}
    	List<Country> raggiungibili = model.getRaggiungibili(partenza);
//    	System.out.println(raggiungibili.toString());
    	boxDestinazione.getItems().clear();
    	boxDestinazione.getItems().addAll(raggiungibili); 

    }

    @FXML
    void initialize() {
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'Country.fxml'.";
        assert boxDestinazione != null : "fx:id=\"boxDestinazione\" was not injected: check your FXML file 'Country.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Country.fxml'.";

    }
}
