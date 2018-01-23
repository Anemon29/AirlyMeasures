
import com.google.gson.Gson;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;


public class APICommands {


    public Sensor sensorInfo(String httpUrl, String apiKey) throws IOException{
        URL url = new URL(httpUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader jsonContent = setConnection(con, apiKey);

        Gson gson = new Gson();
        Sensor sensor = gson.fromJson(jsonContent, Sensor.class);

        jsonContent.close();
        con.disconnect();
        return sensor;

    }


    public String nearestSensor(String httpUrl, String apiKey) throws IOException{

        URL url = new URL(httpUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader jsonContent = setConnection(con, apiKey);

        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = jsonContent.readLine()) != null){
            jsonString.append(line);
        }
        String content = jsonString.toString();

        JSONObject object = new JSONObject(content);


        if (object.isNull("id")) {
            throw new IllegalArgumentException("There wasn't a sensor found in this area, try with different arguments");
        }
        else {
            Integer sensorID = object.getInt("id");
            String nearestSensor = sensorID.toString();

            jsonContent.close();
            con.disconnect();

            return nearestSensor;
        }
    }


    public BufferedReader setConnection(HttpURLConnection con, String apiKey) throws IOException {

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        con.setRequestProperty("apikey", apiKey);

        int status = con.getResponseCode();

        if (status == 404) throw new ConnectException("Error 404 - Not found");
        else if (status == 403) throw new ConnectException("Input valid API-key to get access to data");
        else if (status == 401) throw new ConnectException("Unauthorised - input valid API-Key");
        else if (status == 400) throw new ConnectException("Input validation error");
        else if (status == 500) throw new ConnectException("Unexpected error");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        return in;


    }



}
