import org.apache.commons.cli.*;

import java.io.IOException;

public class CommandLineParsing {

    private org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
    private Options options = new Options();
    private String apiKey = "ad8dedfac54f4e54b915106b4bdc7a38"; //default api key
    // output builder


    private Options addOptions(Options opt){
        opt.addOption("lat","latitude", true, "Latitude of sensor");
        opt.addOption("long","longitude", true, "Longitude of sensor");
        opt.addOption("s","sensor-id", true, "ID of sensor you want to know about");
        opt.addOption("ak","api-key", true, "Input your api-key");
        opt.addOption("hist","history", true, "Show history");
        opt.addOption("h", "help", false, "Show help");

        return opt;
    }

    public void parseCommands(String[] args) throws ParseException, IOException{
        CommandLine cmd = parser.parse(addOptions(options), args);

        if(checkHelp(cmd)){
            return;
        }

        if(cmd.hasOption("ak")){
            this.apiKey = getParam("ak", cmd, "No api key provided!");
        }

        if(cmd.hasOption("lat") && !cmd.hasOption("long")){
            throw new ParseException("Two arguments needed! Add a longitude parameter!");
        }
        else if (!cmd.hasOption("lat") && cmd.hasOption("long")){
            throw new ParseException("Two arguments needed! Add a latitude parameter!");
        }
        else if (cmd.hasOption("lat") && cmd.hasOption("long")){
            inputLongLat(cmd);
        }
        else if (cmd.hasOption("s")) {
            String sensorID = getParam("s", cmd, "No sensor ID provided");
            inputSensorID(cmd, sensorID);
        }

    }

    private boolean checkHelp(CommandLine cl){
        if (cl.hasOption("h")){
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("AirlyMeasures", options, true);
            return true;
        }
        return false;
    }


    private void inputLongLat(CommandLine cl) throws IOException{

            String latitude = getParam("lat", cl, "No latitude parameter");
            String longitude = getParam("long", cl, "No longitude parameter");
            APICommands app = new APICommands();
            String sensorID = app.nearestSensor("https://airapi.airly.eu/v1/nearestSensor/measurements?latitude=" + latitude +"&longitude="+ longitude +"&maxDistance=1000", apiKey);
            inputSensorID(cl, sensorID);

    }

    private void inputSensorID(CommandLine cl, String sensorID) throws IOException{
        APICommands app = new APICommands();
        Sensor sensor = app.sensorInfo("https://airapi.airly.eu/v1/sensor/measurements?sensorId=" + sensorID, apiKey);
        sensor.formatData();
         if (cl.hasOption("hist")){
             //draw current meas with history
         }
         else{
             //draw current measurements
         }



    }


    private String getParam(String option, CommandLine cmd, String message){
        String param = cmd.getOptionValue(option);
        if (param == null) throw new IllegalArgumentException(message);
        else return param;
    }

}
