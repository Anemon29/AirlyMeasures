import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.asciithemes.u8.U8_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.List;

public class AsciiBuilder {


    public String currentMeasurements(Sensor sensor) {

        SensorMeasurements sm = sensor.getCurrentMeasurements();
        AsciiTable table = new AsciiTable();

        table.addRule();
        addRowWithRule(table, null, "Current measurements from a sensor:");
        addRowWithRule(table, "Quality index", whatToDisplay(sm.getAirQualityIndex(), ""));
        addRowWithRule(table, "Pollution level", whatToDisplay(sm.getPollutionLevel(), "/6"));
        addRowWithRule(table, "Humidity", whatToDisplay(sm.getHumidity(), " %"));
        addRowWithRule(table, "PM 1.0", whatToDisplay(sm.getPm1(), " μg/m3"));
        addRowWithRule(table, "PM 2.5", whatToDisplay(sm.getPm25(), " μg/m3"));
        addRowWithRule(table, "PM 10", whatToDisplay(sm.getPm10(), " μg/m3"));
        addRowWithRule(table, "Pressure", whatToDisplay(sm.getPressure(), " hPa"));
        addRowWithRule(table, "Temperature", whatToDisplay(sm.getTemperature(), "°C"));

        table = setAppearance(table);
        table.getContext().setWidth(50);

        return table.render();
    }

    public String measurementsHistory(Sensor sensor) {
        List<SensorForecastAndHistory> history = sensor.getHistory();
        AsciiTable table = new AsciiTable();
        table.addRule();
        addRowWithRule(table, null, null, null, null, null, "Last 24 hours:");
        addRowWithRule(table, "From hour", "Till hour", "Quality index", "Pollution level", "PM 2.5",
                "PM 10");

        for (SensorForecastAndHistory sh : history) {
            SensorMeasurements sm = sh.getMeasurements();
            addRowWithRule(table, sh.getFromDateTime(), sh.getTillDateTime(),
                    whatToDisplay(sm.getAirQualityIndex(), ""), whatToDisplay(sm.getPollutionLevel(), "/6"),
                    whatToDisplay(sm.getPm25(), " μg/m3"), whatToDisplay(sm.getPm10(), " μg/m3"));

        }
        table = setAppearance(table);
        table.getRenderer().setCWC(new CWC_LongestLine());
        return table.render();
    }

    private AsciiTable setAppearance(AsciiTable table) {
        table.setTextAlignment(TextAlignment.CENTER);
        table.getContext().setFrameTopBottomMargin(2);
        table.getContext().setFrameLeftRightMargin(4);
        table.getContext().setGrid(U8_Grids.borderDouble());
        return table;
    }

    private void addRowWithRule(AsciiTable table, Object obj1, Object obj2) {
        table.addRow(obj1, obj2);
        table.addRule();
    }

    private void addRowWithRule(AsciiTable table, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        table.addRow(obj1, obj2, obj3, obj4, obj5, obj6);
        table.addRule();
    }

    private Object whatToDisplay(double input, String additional) {
        if (input == 0) {
            return "No data";
        } else return ((int) input + additional);
    }

}
