public class Run {
    public static void main(String[] args){

        try {
            CommandLineParse commandLineParser = new CommandLineParse();
            commandLineParser.parseCommands(args);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
