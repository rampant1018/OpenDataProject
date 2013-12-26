package JUnit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import OPEarth.Chart;

public class ChartTest {
	Chart chart;
	

	@Before
	public void setUp() throws Exception {
		chart = new Chart();
	}

	@After
	public void tearDown() throws Exception {
		chart = null;
	}

	@Test
	public void testGenTimeSeriesChart() {
		Map<Date, Integer> map = new HashMap<Date, Integer>();
    	map.put(new Date(1356998400000L), 520);
    	map.put(new Date(1359676800000L), 129);
    	map.put(new Date(1362096000000L), 3029);
    	map.put(new Date(1364774400000L), 2039);
    	map.put(new Date(1370044800000L), 11);
    	map.put(new Date(1372636800000L), 2249);
    	map.put(new Date(1375315200000L), 35);
    	map.put(new Date(1377993600000L), 600);
    	
    	try {
    		chart.GenerateTimeSeriesChart(map);
    	}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGenMapStat() {
		List<String> longitude = new ArrayList<String>();
    	List<String> latitude = new ArrayList<String>();
    	ArrayList<Integer> magnitude = new ArrayList<Integer>();
    	longitude.add("14.29891");
    	latitude.add("145.2153");
    	magnitude.add(5);
    	longitude.add("30.21218");
    	latitude.add("-179.6285");
    	magnitude.add(4);
    	longitude.add("14.9454");
    	latitude.add("-160.17769");
    	magnitude.add(3);
    	longitude.add("39.16982");
    	latitude.add("-147.6754");
    	magnitude.add(4);
    	longitude.add("50.59983");
    	latitude.add("-174.21519");
    	magnitude.add(5);
    	longitude.add("49.46012");
    	latitude.add("155.24419");
    	magnitude.add(4);
    	
    	try {
			chart.GenerateMapStat(longitude, latitude, magnitude);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
