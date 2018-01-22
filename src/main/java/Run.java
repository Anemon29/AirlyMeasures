
import org.apache.commons.cli.ParseException;

import java.io.IOException;


public class Run {
    public static void main(String[] args){

        try
        {
            CommandLineParsing commandLineParser = new CommandLineParsing();
            commandLineParser.parseCommands(args);

            //APICommands app = new APICommands();
            //String id = app.nearestSensor("https://airapi.airly.eu/v1/nearestSensor/measurements?latitude=50.06&longitude=20&maxDistance=1000");

            //Sensor sensor = app.sensorInfo("https://airapi.airly.eu/v1/sensor/measurements?sensorId=" + id);
            //System.out.println(sensor);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }



    }
}
