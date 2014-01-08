package OPEarth;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.Month;
import org.jfree.data.time.Year;
import org.json.JSONException;

import OPEarth.PostgreSQL.Entry;

public class Main {
        /**
         * @param args
         */
        public static void main(String[] args) {
            // TODO Auto-generated method stub
        	System.out.println("Main Thread");

        	Fetch fetch = ProjectFactory.getFetch();
        	Parser parser = ProjectFactory.getParser();
        	Chart chart = ProjectFactory.getChart();
        	PostgreSQL database = ProjectFactory.getDatabase();
        	database.Connect();
        	
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

				try {
					eventList = parser.getEventList(fetch.getJson(formatter.format(startDate.getTime()), formatter.format(endDate.getTime())));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	    			
            	List<PostgreSQL.Entry> entryList = new ArrayList<PostgreSQL.Entry>();
            	for(Map<String, String> event : eventList) {
        			Calendar cal = Calendar.getInstance();
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        			cal.setTimeInMillis(Long.parseLong(event.get("time")));
            		entryList.add(database.new Entry(Float.parseFloat(event.get("longitude")), Float.parseFloat(event.get("latitude")), Float.parseFloat(event.get("mag")), cal.getTimeInMillis(), event.get("location").replace('\'', '`')));
            	}
            	database.InsertData(entryList);
        		
				if(startDate.get(Calendar.MONTH) != Calendar.DECEMBER) {
					startDate.add(Calendar.MONTH, 1);
				}
				else {
					startDate.add(Calendar.YEAR, 1);
					startDate.set(Calendar.MONTH, Calendar.JANUARY);
				}

        	}

        	List<Integer> longitude = new ArrayList<Integer>();
    		List<Integer> latitude = new ArrayList<Integer>();
    		List<Integer> magnitude = new ArrayList<Integer>();
        	List<PostgreSQL.Entry> entryList = database.getEntryList();
        	Map<Year, Integer> statData = new HashMap<Year, Integer>();
        	for(PostgreSQL.Entry entry : entryList) {
        		Calendar cal = Calendar.getInstance();
        		cal.setTimeInMillis(entry.time);
        		Year tmpYear = new Year(cal.getTime());
        		System.out.println(entry.longitude + " " + entry.latitude + " " + entry.magnitude + " " + tmpYear + " " + entry.location);
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
        	try {
				chart.GenerateTimeSeriesChart(statData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				//chart.GenerateMapStat(longitude, latitude, magnitude);
				chart.GenerateMap(longitude, latitude, magnitude);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	database.DisConnect();
        }
}
