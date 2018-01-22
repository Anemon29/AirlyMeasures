
import java.util.List;

public class Sensor {

    private SensorMeasurements currentMeasurements;
    private List<SensorForecastAndHistory> forecast;
    private List<SensorForecastAndHistory> history;

    public void formatData(){
        currentMeasurements.formatData();
        for (SensorForecastAndHistory sf : forecast){
            sf.getMeasurements().formatData();
        }
        for (SensorForecastAndHistory sh : history){
            sh.getMeasurements().formatData();
        }
    }

}
