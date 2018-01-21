import org.apache.commons.cli.*;

public class CommandLineParser {

    private org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
    private Options options = new Options();
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

    public void parseCommands(String[] args) throws ParseException{
        CommandLine cmd = parser.parse(addOptions(options), args);

        checkHelp(cmd);
        //checkers todo

    }

    private void checkHelp(CommandLine cl){
        if (cl.hasOption("h")){
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("AirlyMeasures", options, true);
        }
    }

    private void checkHistory(CommandLine cl){
        if (cl.hasOption("hist")){
            //todo
            //depends if history only for sensor or for lat/longitude
        }
    }

    private void checkLongLat(CommandLine cl) throws ParseException{
        if(cl.hasOption("lat") && !cl.hasOption("long")){
            throw new ParseException("Two arguments needed! Add a longitude parameter!");
        }
        else if (!cl.hasOption("lat") && cl.hasOption("long")){
            throw new ParseException("Two arguments needed! Add a latitude parameter!");
        }
        else if (cl.hasOption("lat") && cl.hasOption("long")){
            String latitude = getParam("lat", cl, "No latitude parameter");
            String longitude = getParam("long", cl, "No longitude parameter");


            checkHistory(cl);
            //todo


        }
    }

    private void checkSensor(CommandLine cl){
        if (cl.hasOption("s")){
            String sensorID = getParam("s", cl, "No sensor ID provided");


            checkHistory(cl);
            //ascii for sensor todo


        }
    }

    private void checkApiKey(CommandLine cl){
        if (cl.hasOption("ak")){
            String apiKey = getParam("ak", cl, "No API key provided");

            // change apikey variable todo
        }
    }

    private String getParam(String option, CommandLine cmd, String message){
        String param = cmd.getOptionValue(option);
        if (param == null) throw new IllegalArgumentException(message);
        else return param;
    }

}
