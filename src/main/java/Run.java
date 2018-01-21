
import java.io.IOException;

import com.google.gson.*;

public class Run {
    public static void main(String[] args) throws IOException{

        APICommands app = new APICommands();
        String id = app.nearestSensor("https://airapi.airly.eu/v1/nearestSensor/measurements?latitude=50.06&longitude=20&maxDistance=1000");

        app.sensorInfo("https://airapi.airly.eu/v1/sensor/measurements?sensorId=" + id);
        //int status = con.getResponseCode();




    }
}
