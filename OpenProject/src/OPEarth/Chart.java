package OPEarth;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class Chart {
	public void GenerateTimeSeriesChart(Map<Date, Integer> statData) {
		TimeSeries earthquakes = new TimeSeries("Earthquakes", Month.class);
		
		earthquakes.add(new Month(1, 2013), 100);
		earthquakes.add(new Month(3, 2013), 120);
		earthquakes.add(new Month(2, 2013), 80);
		earthquakes.add(new Month(4, 2013), 500);
		earthquakes.add(new Month(5, 2013), 20);
		earthquakes.add(new Month(7, 2013), 1);
		earthquakes.add(new Month(9, 2013), 588);
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(earthquakes);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Earthquake Stats", 
				"Month", 
				"Count", 
				dataset);
		
		try {
			ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);
		} catch(IOException e) {
			System.err.println("Problem occurres creating chart.");
		}
	}
}
