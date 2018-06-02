package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Observable;

public class Controller {

    List<Country> countries;

    @FXML // fx:id="list";
    ListView<String> list;

    @FXML //fx:id="filter_field";
    TextField filter_field;

    @FXML //fx:id="citiesCount";
    Label citiesCount;

    String filter = "";

    public void loadList()
    {
        ObservableList<String> bridge_list = FXCollections.observableArrayList();
        for (Country c : countries)
        {
            if (!this.filter.equals(""))
            {
                if (!c.getName().toLowerCase().contains(this.filter.toLowerCase())){
                    continue;
                }
            }
            bridge_list.add(c.getName());
        }
        list.setItems(bridge_list);
    }

    public void filter(Event e)
    {
        TextField t = (TextField) e.getSource();
        this.filter =t.getText();
        this.loadList();
    }

    public void selected(Event e)
    {
        String selected = list.getSelectionModel().getSelectedItem();

        for (Country c:countries)
        {
            if (selected == c.getName())
            {
                citiesCount.setText(Integer.toString(c.getCities()));
            }
        }
    }

    public Controller(){
        countries = Country.getCountries();
    }
}
