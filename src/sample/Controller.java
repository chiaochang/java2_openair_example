package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
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

    @FXML //fx:id="line_chart";
    LineChart line_chart;

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
            if (selected.equals(c.getName()))
            {
                String txt = Integer.toString(c.getCities());
                citiesCount.setText(txt);
                this.drawChart(c.getCode());
                break;
            }
        }
    }

    public void drawChart(String city_code)
    {
        ArrayList<Latest> latest_array = Latest.getLatest(city_code);

        //By the time we get to this point we have our data parsed
        //and clean in an object that can be accessed like obj.value
        //and obj.lastUpdated

        //Here we will format the data to the way that the chart likes it
        String label = "";
        String ylabel = "";
        XYChart.Series<String, Float> series = new XYChart.Series();
        for (Latest l:latest_array){
            series.getData().add(new XYChart.Data(l.lastUpdated.toString(), l.value));
            label = l.parameter;
        }

        line_chart.getData().clear();
        line_chart.getData().add(series);
        line_chart.getXAxis().setLabel("Time");
        line_chart.getYAxis().setLabel("Value " +label);
    }
    public Controller(){
        countries = Country.getCountries();
    }
}
