package sample;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Text temperatura;

    @FXML
    private Text feelsTemp;

    @FXML
    private Text humidity;

    @FXML
    private Button check;

    @FXML
    private Text wind;

    @FXML
    private Text pressure;

    @FXML
    void initialize() {
        // push button
        check.setOnAction(event -> {

            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) {

                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=88d1d076a6767f67432fba1738625835&units=metric");

                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);

                    temperatura.setText("Temperatura:  " + obj.getJSONObject("main").getDouble("temp") + " C");
                    feelsTemp.setText("Odczuwalna:  " + obj.getJSONObject("main").getDouble("feels_like") + " C");
                    pressure.setText("Cisnienie:  " + obj.getJSONObject("main").getDouble("pressure") + " hPa");
                    humidity.setText("Wilgotnosc:  " + obj.getJSONObject("main").getDouble("humidity") + " %");
                    wind.setText("Wiatr:  " + obj.getJSONObject("wind").getDouble("speed") + " m/s");
                }
            }
        });
    }

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Nie znaleziono miasto!");
            alert.showAndWait();

        }
        return content.toString();
    }

}