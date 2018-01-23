import java.io.IOException;

public class Run {
    public static void main(String[] args) throws NoSuchFieldException,IOException, org.apache.commons.cli.ParseException{

        try
        {
            CommandLineParsing commandLineParser = new CommandLineParsing();
            commandLineParser.parseCommands(args);

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }



    }
}
