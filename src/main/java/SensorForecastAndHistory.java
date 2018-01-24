public class SensorForecastAndHistory {

    private String fromDateTime;
    private SensorMeasurements measurements;
    private String tillDateTime;


    public SensorMeasurements getMeasurements() {
        return measurements;
    }

    public String getFromDateTime() {
        return fromDateTime;
    }

    public String getTillDateTime() {
        return tillDateTime;
    }

    public void formatData() {
        this.fromDateTime = fromDateTime.substring(11, 19);
        this.tillDateTime = tillDateTime.substring(11, 19);
    }
}
