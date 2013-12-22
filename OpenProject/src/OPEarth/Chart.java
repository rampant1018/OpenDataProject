package OPEarth;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.omg.CORBA_2_3.portable.OutputStream;

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
	
	public void GenerateMapStat(List<String> longitude, List<String> latitude) throws IOException {
		String generateURL = "http://maps.googleapis.com/maps/api/staticmap?center=17.12207,179.39887&zoom=2&size=640x640&sensor=false&maptype=satellite&format=jpg&markers=";
		
		for(int i = 0; i < longitude.size(); i++) {
			if(i != 0) {
				generateURL += "%7C";
			}
			generateURL += longitude.get(i);
			generateURL += ",";
			generateURL += latitude.get(i);
		}
		
		URL url = new URL(generateURL);
		Image image = ImageIO.read(url);
		ImageIO.write((RenderedImage) image, "jpg", new File("Map.jpg"));
	}
}
