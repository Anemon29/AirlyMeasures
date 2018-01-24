public class SensorMeasurements {

    private double airQualityIndex;
    private double pm1;
    private double pm25;
    private double pm10;
    private double pressure;
    private double humidity;
    private double temperature = 100;   //Default value essential to verify if API provided valid data
    private int pollutionLevel;


    public int getPollutionLevel() {
        return pollutionLevel;
    }

    public double getPm25() {
        return pm25;
    }

    public double getPm10() {
        return pm10;
    }

    public double getAirQualityIndex() {
        return airQualityIndex;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPm1() {
        return pm1;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void formatData() {
        this.airQualityIndex = Math.round(airQualityIndex);
        this.humidity = Math.round(humidity);
        this.pm1 = Math.round(pm1);
        this.pm10 = Math.round(pm10);
        this.pm25 = Math.round(pm25);
        this.pressure = Math.round(pressure) / 100;
        this.temperature = Math.round(temperature);
    }
}
