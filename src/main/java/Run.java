
public class Run {
    public static void main(String[] args){

        try {
            CommandLineParsing commandLineParser = new CommandLineParsing();
            commandLineParser.parseCommands(args);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
