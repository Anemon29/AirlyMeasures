import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.asciitable.CWC_LongestWord;
import de.vandermeer.asciithemes.u8.U8_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class AsciiBuilder {


    public String currentMeasurements(Sensor sensor){
        SensorMeasurements sm = sensor.getCurrentMeasurements();

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(null, "Current measurements from a sensor:");
        table.addRule();
        table.addRow("Quality index", (int)sm.getAirQualityIndex());
        table.addRule();
        table.addRow("Pollution level", sm.getPollutionLevel() + "/6");
        table.addRule();
        table.addRow("Humidity", (int)sm.getHumidity() + " %");
        table.addRule();
        table.addRow("PM 1.0", (int)sm.getPm1() + " μg/m3");
        table.addRule();
        table.addRow("PM 2.5", (int)sm.getPm25() + " μg/m3");
        table.addRule();
        table.addRow("PM 10", (int)sm.getPm10() + " μg/m3");
        table.addRule();
        table.addRow("Pressure", (int)sm.getPressure() + " hPa");
        table.addRule();
        table.addRow("Temperature", (int)sm.getTemperature() + "°C");
        table.addRule();
        table = setAppearance(table);
        table.getContext().setWidth(50);

        return table.render();
    }

    public String measurementsHistory(Sensor sensor){
        List<SensorForecastAndHistory> history = sensor.getHistory();
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(null, null, null, null, null, null, null, null, null, "Last 24 hours:");
        table.addRule();
        table.addRow("From hour","Till hour","Quality index","Pollution level","Humidity","PM 1.0","PM 2.5",
                "PM 10","Pressure","Temperature");
        table.addRule();
        for (SensorForecastAndHistory sh : history){
            SensorMeasurements sm = sh.getMeasurements();
            table.addRow(sh.getFromDateTime(), sh.getTillDateTime(),
                    (int)sm.getAirQualityIndex(), sm.getPollutionLevel() + "/6",
                    (int)sm.getHumidity() + "%", (int)sm.getPm1() + " μg/m3",
                    (int)sm.getPm25() + " μg/m3", (int)sm.getPm10() + " μg/m3",
                    (int) sm.getPressure() + " hPa", (int)sm.getTemperature() + "°C");
            table.addRule();
        }

        table = setAppearance(table);
        table.getRenderer().setCWC(new CWC_LongestLine());
        return table.render();
    }

    private AsciiTable setAppearance(AsciiTable table){
        table.setTextAlignment(TextAlignment.CENTER);
        table.getContext().setFrameTopBottomMargin(2);
        table.getContext().setFrameLeftRightMargin(4);
        table.getContext().setGrid(U8_Grids.borderDouble());
        return table;
    }

}
