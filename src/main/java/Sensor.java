
import java.util.List;

public class Sensor {

    private SensorMeasurements currentMeasurements;
    private List<SensorForecastAndHistory> forecast;
    private List<SensorForecastAndHistory> history;

    public void formatData(){
        currentMeasurements.formatData();
        for (SensorForecastAndHistory sf : forecast){
            sf.formatData();
            sf.getMeasurements().formatData();
        }
        for (SensorForecastAndHistory sh : history){
            sh.formatData();
            sh.getMeasurements().formatData();
        }
    }

    public SensorMeasurements getCurrentMeasurements() {
        return currentMeasurements;
    }

    public List<SensorForecastAndHistory> getForecast() {
        return forecast;
    }

    public List<SensorForecastAndHistory> getHistory() {
        return history;
    }

}
