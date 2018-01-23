import org.apache.commons.cli.*;

import java.io.IOException;

public class CommandLineParsing {

    private org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
    private Options options = new Options();
    private String apiKey = "ad8dedfac54f4e54b915106b4bdc7a38"; //default api key


    private Options addOptions(Options opt){
        opt.addOption("lat","latitude", true, "Latitude of sensor");
        opt.addOption("long","longitude", true, "Longitude of sensor");
        opt.addOption("s","sensor-id", true, "ID of sensor you want to know about");
        opt.addOption("ak","api-key", true, "Input your api-key");
        opt.addOption("hist","history", false, "Show history");
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

        if(!cmd.hasOption("s") && !cmd.hasOption("lat")  && !cmd.hasOption("long")){
            throw new ParseException("Too few arguments. You must provide sensor ID or latitude and longitude!");
        }
        else if(cmd.hasOption("s") && cmd.hasOption("lat")  && cmd.hasOption("long")){
            throw new ParseException("Too many arguments. You must provide sensor ID or latitude and longitude!");
        }
        else if(cmd.hasOption("lat") && !cmd.hasOption("long")){
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

    private void inputSensorID(CommandLine cl, String sensorID){
        APICommands app = new APICommands();
        try {
            Sensor sensor = app.sensorInfo("https://airapi.airly.eu/v1/sensor/measurements?sensorId=" + sensorID, apiKey);
            sensor.formatData();
            AsciiBuilder asciiBuilder = new AsciiBuilder();
            if (cl.hasOption("hist")) {
                String output = asciiBuilder.measurementsHistory(sensor);
                System.out.println(output);
            } else {
                if (sensor.getCurrentMeasurements().getAirQualityIndex() == 0)
                    throw new NoSuchFieldException("Sensor doesn't have current measurements!");
                String output = asciiBuilder.currentMeasurements(sensor);
                System.out.println(output);
            }
        }
        catch (IllegalStateException ex){
            System.out.println("Error parsing JSON, try again");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private String getParam(String option, CommandLine cmd, String message){
        String param = cmd.getOptionValue(option);
        if (param == null) throw new IllegalArgumentException(message);
        else return param;
    }

}
