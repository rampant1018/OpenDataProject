package OPEarth;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.Year;
import org.json.JSONException;

public class Main {
        /**
         * @param args
         */
        public static void main(String[] args) {
            // TODO Auto-generated method stub
        	System.out.println("Main Thread");

        	// Get object instance
        	Fetch fetch = ProjectFactory.getFetch();
        	Parser parser = ProjectFactory.getParser();
        	Chart chart = ProjectFactory.getChart();
        	PostgreSQL database = ProjectFactory.getDatabase();
        	
        	// Connection database to server
        	database.Connect();
        	
        	// Get latest data time from database. Then set the fetcher's start time.
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	Calendar startDate = Calendar.getInstance();
        	if(database.GetLatestTime() == null) {
        		System.out.println("Database has no data");
            	startDate.set(1973, Calendar.JANUARY, 1); // 1970/01/01
        	}
        	else {
        		startDate.setTimeInMillis(database.GetLatestTime());
        		startDate.set(Calendar.DAY_OF_MONTH, 1);
        		
        		if(startDate.get(Calendar.MONTH) != Calendar.DECEMBER) {
        			startDate.add(Calendar.MONTH, 1);
				}
				else {
					startDate.add(Calendar.YEAR, 1);
					startDate.set(Calendar.MONTH, Calendar.JANUARY);
				}
        		
        		System.out.println("Current latest data end at " + formatter.format(startDate.getTime()));
        	}
        	
        	// Get calendar instance. Used to calculate start time to end time.
        	Calendar endDate = Calendar.getInstance();
        	Calendar current = Calendar.getInstance();
        	current.setTimeInMillis(System.currentTimeMillis());
        	while(true) {
        		if(startDate.get(Calendar.YEAR) == current.get(Calendar.YEAR) && startDate.get(Calendar.MONTH) >= current.get(Calendar.MONTH) - 1) {
        			System.out.println("Database has latest data");
					break;
				}
        		else if(startDate.get(Calendar.MONTH) == Calendar.DECEMBER && current.get(Calendar.YEAR) == startDate.get(Calendar.YEAR) + 1 && startDate.get(Calendar.MONTH) == Calendar.JANUARY) {
        			System.out.println("Database has latest data");
					break;
        		}
        		
        		List<Map<String, String>> eventList = null;
				endDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), 30);
				System.out.println(formatter.format(startDate.getTime()) + " ~ " + formatter.format(endDate.getTime()));

				// Parse JSON content and store data to eventList
				try {
					eventList = parser.getEventList(fetch.getJson(formatter.format(startDate.getTime()), formatter.format(endDate.getTime())));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	    			
				// Convert earthquake event to entry and store to entryList
            	List<PostgreSQL.Entry> entryList = new ArrayList<PostgreSQL.Entry>();
            	for(Map<String, String> event : eventList) {
        			Calendar cal = Calendar.getInstance();
        			cal.setTimeInMillis(Long.parseLong(event.get("time")));
            		entryList.add(database.new Entry(Float.parseFloat(event.get("longitude")), Float.parseFloat(event.get("latitude")), Float.parseFloat(event.get("mag")), cal.getTimeInMillis(), event.get("location").replace('\'', '`')));
            	}
            	
            	// Insert data to database
            	database.InsertData(entryList);
        		
            	// Start time increment.
				if(startDate.get(Calendar.MONTH) != Calendar.DECEMBER) {
					startDate.add(Calendar.MONTH, 1);
				}
				else {
					startDate.add(Calendar.YEAR, 1);
					startDate.set(Calendar.MONTH, Calendar.JANUARY);
				}
        	}

        	// Get data from database server and store data into entryList and statData.
        	List<Integer> longitude = new ArrayList<Integer>();
    		List<Integer> latitude = new ArrayList<Integer>();
    		List<Integer> magnitude = new ArrayList<Integer>();
        	List<PostgreSQL.Entry> entryList = database.getEntryList();
        	Map<Year, Integer> statData = new HashMap<Year, Integer>();
        	for(PostgreSQL.Entry entry : entryList) {
        		Calendar cal = Calendar.getInstance();
        		cal.setTimeInMillis(entry.time);
        		Year tmpYear = new Year(cal.getTime());
        		if(statData.containsKey(tmpYear)) {
        			int tmp = statData.get(tmpYear);
        			statData.remove(tmpYear);
        			statData.put(tmpYear, tmp + 1);
        		}
        		else {
        			statData.put(tmpYear, 1);
        		}
        		
        		longitude.add((int)entry.longitude);
        		latitude.add((int)entry.latitude);
        		magnitude.add((int)entry.magnitude);
        	}

        	// Generate chart
        	try {
				chart.GenerateTimeSeriesChart(statData);
				System.out.println("Generate time series chart done.");
				chart.GenerateMap(longitude, latitude, magnitude);
				System.out.println("Generate statistics map done.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	// Disconnect database
        	database.DisConnect();
        }
}
