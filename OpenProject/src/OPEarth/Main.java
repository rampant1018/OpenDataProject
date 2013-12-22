package OPEarth;

import java.util.Date;
import java.util.HashMap;
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
        	Map<Date, Integer> map = new HashMap<Date, Integer>();
        	map.put(new Date(1356998400000L), 520);
        	map.put(new Date(1359676800000L), 129);
        	map.put(new Date(1362096000000L), 3029);
        	map.put(new Date(1364774400000L), 2039);
        	map.put(new Date(1370044800000L), 11);
        	map.put(new Date(1372636800000L), 2249);
        	map.put(new Date(1375315200000L), 35);
        	map.put(new Date(1377993600000L), 600);
        	Chart chart = new Chart();
        	chart.GenerateTimeSeriesChart(map);
        }
        
        
        
        
}
