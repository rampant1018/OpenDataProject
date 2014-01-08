package JUnit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.Month;
import org.jfree.data.time.Year;
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
		boolean successful = true;
		Map<Year, Integer> map = new HashMap<Year, Integer>();
    	map.put(new Year(2013), 520);
    	map.put(new Year(2012), 302);
    	map.put(new Year(2011), 132);
    	map.put(new Year(2010), 3456);
    	map.put(new Year(2009), 1342);
    	map.put(new Year(2008), 1672);
    	map.put(new Year(2007), 3333);
    	map.put(new Year(2006), 5382);
    	map.put(new Year(2005), 238);
    	
    	try {
    		chart.GenerateTimeSeriesChart(map);
    	}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			successful = false;
		}
    	
    	assertEquals(true, successful);
	}
	
	@Test
	public void testGenerateMap() {
		/*
		try {
			//chart.GenerateMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
