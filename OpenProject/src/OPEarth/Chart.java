package OPEarth;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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
		
		for(Date key : statData.keySet()) {
			System.out.println(key + " : "  + statData.get(key));
			earthquakes.add(new Month(key), statData.get(key));
		}
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(earthquakes);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Earthquake Stats", 
				"Month", 
				"Count", 
				dataset);
		
		try {
			ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 800, 500);
		} catch(IOException e) {
			System.err.println("Problem occurres creating chart.");
		}
	}
}
