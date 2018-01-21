
//import com.google.gson.Gson;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;


public class APICommands {


    public void sensorInfo(String httpUrl) throws IOException{
        URL url = new URL(httpUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader jsonContent = setConnection(con);

        Gson gson = new Gson();
        Sensor sensor = gson.fromJson(jsonContent, Sensor.class);

        System.out.println(sensor);

    }


    public String nearestSensor(String httpUrl) throws IOException{

        URL url = new URL(httpUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader jsonContent = setConnection(con);

        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = jsonContent.readLine()) != null){
            jsonString.append(line);
        }
        String content = jsonString.toString();

        JSONObject object = new JSONObject(content);

        Integer sensorID = object.getInt("id");
        String nearestSensor = sensorID.toString();

        jsonContent.close();
        con.disconnect();

        return nearestSensor;
    }


    public BufferedReader setConnection(HttpURLConnection con) throws IOException {

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        con.setRequestProperty("apikey", "ad8dedfac54f4e54b915106b4bdc7a38");

        int status = con.getResponseCode();

        if (status == 404) throw new ConnectException("");
        else if (status == 403) throw new ConnectException("Forbidden");
        else if (status == 401) throw new ConnectException("Unauthorised - input valid API-Key");
        else if (status == 400) throw new ConnectException("Input validation error");
        else if (status == 500) throw new ConnectException("Unexpected error");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        return in;


    }



}
