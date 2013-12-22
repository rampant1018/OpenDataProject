package OPEarth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
        /**
         * @param args
         */
        public static void main(String[] args) {
                // TODO Auto-generated method stub
        	/*
                Parser parser = ProjectFactory.getParser();
                parser.test();
                */
        	/*
        	Map<Date, Integer> map = new HashMap<Date, Integer>();
        	map.put(new Date(1356998400000L), 520);
        	map.put(new Date(1359676800000L), 129);
        	map.put(new Date(1362096000000L), 3029);
        	map.put(new Date(1364774400000L), 2039);
        	map.put(new Date(1370044800000L), 11);
        	map.put(new Date(1372636800000L), 2249);
        	map.put(new Date(1375315200000L), 35);
        	map.put(new Date(1377993600000L), 600);*/
        	Chart chart = new Chart();
        	//chart.GenerateTimeSeriesChart(map);
        	
        	List<String> longitude = new ArrayList<String>();
        	List<String> latitude = new ArrayList<String>();
        	longitude.add("14.29891");
        	latitude.add("145.2153");
        	longitude.add("30.21218");
        	latitude.add("-179.6285");
        	longitude.add("14.9454");
        	latitude.add("-160.17769");
        	longitude.add("39.16982");
        	latitude.add("-147.6754");
        	longitude.add("50.59983");
        	latitude.add("-174.21519");
        	longitude.add("49.46012");
        	latitude.add("155.24419");
        	
        	try {
				chart.GenerateMapStat(longitude, latitude);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
        
        
}
