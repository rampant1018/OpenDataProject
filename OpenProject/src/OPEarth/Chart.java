package OPEarth;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

public class Chart {
	/**
	 * Generate series chart with given earthquakes information
	 * @param statData earthquake statistics data
	 * @throws IOException
	 */
	public void GenerateTimeSeriesChart(Map<Year, Integer> statData) throws IOException {
		TimeSeries earthquakes = new TimeSeries("Earthquakes");
		
		for(Year key : statData.keySet()) {
			earthquakes.add(key, statData.get(key));
		}
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(earthquakes);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Earthquake Stats", 
				"Year", 
				"Count", 
				dataset);
		
		ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 800, 500);
	}
	
	/**
	 * Plot point on the map with given earthquake information
	 * @param longitude a list of longitude
	 * @param latitude a list of latitude
	 * @param magnitude a list of magnitude
	 * @throws IOException
	 */
	public void GenerateMap(List<Integer> longitude, List<Integer> latitude, List<Integer> magnitude) throws IOException {
		BufferedImage image = ImageIO.read(new File("origin_map.jpg"));
		Graphics2D g = image.createGraphics();
		
		for(int i = 0; i < longitude.size(); i++) {		
			int tmpLon, tmpLat;
			tmpLon = longitude.get(i) > 0 ? longitude.get(i) - 70 : (360 + longitude.get(i) - 70);
			tmpLat = 110 - latitude.get(i);
			
			switch(magnitude.get(i)){
				case 4:
					g.setPaint(new Color(0, 0, 255));
					g.fillOval((int)((float)tmpLon / 240.0f * 640.0f), (int)((float)tmpLat / 180.0f * 640.0f), 5, 5);
					break;
				case 5:
					g.setPaint(new Color(255, 0, 0));
					g.fillOval((int)((float)tmpLon / 240.0f * 640.0f), (int)((float)tmpLat / 180.0f * 640.0f), 5, 5);
					break;
				case 6:
					g.setPaint(new Color(0, 255, 0));
					g.fillOval((int)((float)tmpLon / 240.0f * 640.0f), (int)((float)tmpLat / 180.0f * 640.0f), 5, 5);
					break;
				case 7:
				case 8:
				case 9:
					g.setPaint(new Color(255, 255, 255));
					g.fillOval((int)((float)tmpLon / 240.0f * 640.0f), (int)((float)tmpLat / 180.0f * 640.0f), 5, 5);
					break;
				default:
			}				
		}
		
		ImageIO.write((RenderedImage) image, "jpg", new File("stat_map.jpg"));
	}
}
