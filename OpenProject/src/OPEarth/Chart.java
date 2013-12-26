package OPEarth;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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
	
	public void GenerateMapStat(List<String> longitude, List<String> latitude, ArrayList<Integer> magnitude) throws IOException {
		String generateURL = "http://maps.googleapis.com/maps/api/staticmap?center=17.12207,179.39887&zoom=2&size=640x640&sensor=false&maptype=satellite&format=jpg";
		String[] tmpURL = {"&markers=size:mid%7Ccolor:green", "&markers=size:mid%7Ccolor:blue", "&markers=icon:http://ppt.cc/ccLo"};
		for(int i = 0; i < longitude.size(); i++) {		
			
			switch(magnitude.get(i)){
				case 3:
					tmpURL[0] += "%7C"+longitude.get(i)+","+latitude.get(i);
					break;
				case 4:
					tmpURL[1] += "%7C"+longitude.get(i)+","+latitude.get(i);
					break;
				case 5:
					tmpURL[2] += "%7C"+longitude.get(i)+","+latitude.get(i);
					break;
				default:
			}				
		}
		generateURL += tmpURL[0]+tmpURL[1]+tmpURL[2];
		URL url = new URL(generateURL);
		System.out.println(url.toString());
		Image image = ImageIO.read(url);
		ImageIO.write((RenderedImage) image, "jpg", new File("Map.jpg"));
	}
}
