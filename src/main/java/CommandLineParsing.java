import org.apache.commons.cli.*;

import java.io.IOException;

public class CommandLineParsing {

    private org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
    private Options options = new Options();
    private String apiKey = System.getenv("API_KEY");

    private Options addOptions(Options opt){
        opt.addOption("lat","latitude", true, "Latitude of the place on the map, where the nearest sensor will be searched for");
        opt.addOption("long","longitude", true, "Longitude of the place on the map, where the nearest sensor will be searched for");
        opt.addOption("s","sensor-id", true, "ID of sensor you want to know about");
        opt.addOption("ak","api-key", true, "Input your api-key");
        opt.addOption("hist","history", false, "Optional parameter, changes viewing style to a table of measurements in the last 24 hours");
        opt.addOption("h", "help", false, "Show help");

        return opt;
    }

    public void parseCommands(String[] args) throws ParseException, IOException, NoSuchFieldException{
        CommandLine cmd = parser.parse(addOptions(options), args);

        if(checkHelp(cmd)){
            return;
        }

        if(cmd.hasOption("ak")){
            this.apiKey = getParam("ak", cmd, "No api key provided!");
        }

        if(!cmd.hasOption("s") && !cmd.hasOption("lat")  && !cmd.hasOption("long")){
            throw new ParseException("Too few parameters. You must provide sensor ID or latitude and longitude! Add -h when running a program to see list of available parameters.");
        }
        else if(cmd.hasOption("s") && cmd.hasOption("lat")  && cmd.hasOption("long")){
            throw new ParseException("Too many parameters. You must provide sensor ID or latitude and longitude!");
        }
        else if(cmd.hasOption("lat") && !cmd.hasOption("long")){
            throw new ParseException("Two parameters needed! Add a longitude parameter!");
        }
        else if (!cmd.hasOption("lat") && cmd.hasOption("long")){
            throw new ParseException("Two parameters needed! Add a latitude parameter!");
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

    private void inputLongLat(CommandLine cl) throws IOException, NoSuchFieldException{

            String latitude = getParam("lat", cl, "No latitude parameter");
            String longitude = getParam("long", cl, "No longitude parameter");
            APICommands api = new APICommands();
            String sensorID = api.nearestSensor("https://airapi.airly.eu/v1/nearestSensor/measurements?latitude=" + latitude +"&longitude="+ longitude +"&maxDistance=1000", apiKey);
            inputSensorID(cl, sensorID);

    }

    private void inputSensorID(CommandLine cl, String sensorID) throws IOException, NoSuchFieldException{

        APICommands api = new APICommands();
        Sensor sensor = api.sensorInfo("https://airapi.airly.eu/v1/sensor/measurements?sensorId=" + sensorID, apiKey);
        sensor.formatData();
        AsciiBuilder asciiBuilder = new AsciiBuilder();
        if (cl.hasOption("hist")) {
            String output = asciiBuilder.measurementsHistory(sensor);
            System.out.println(output);
        }
        else {
            if (sensor.getCurrentMeasurements().getAirQualityIndex() == 0) {
                throw new NoSuchFieldException("Sensor doesn't have current measurements!");
            }
            String output = asciiBuilder.currentMeasurements(sensor);
            System.out.println(output);
        }
    }

    private String getParam(String option, CommandLine cmd, String message){
        String param = cmd.getOptionValue(option);
        if (param == null) throw new IllegalArgumentException(message);
        else return param;
    }

}
