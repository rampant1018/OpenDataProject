package OPEarth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import OPEarth.PostgreSQL.Entry;

public class Main {
        /**
         * @param args
         */
        public static void main(String[] args) {
            // TODO Auto-generated method stub

        	List<Map<String, String>> eventList;
        	
        	System.out.println("Main Thread");
        	Parser parser = ProjectFactory.getParser();
        	eventList = parser.parse();
        	
        	PostgreSQL database = new PostgreSQL();
        	List<PostgreSQL.Entry> entryList = new ArrayList<PostgreSQL.Entry>();
        	int count = 0;
        	for(Map<String, String> event : eventList) {
        		entryList.add(database.new Entry(Float.parseFloat(event.get("longitude")), Float.parseFloat(event.get("latitude")), Float.parseFloat(event.get("mag")), new Date(Long.parseLong(event.get("time"))), event.get("location").replace('\'', '`')));
        		if(count == 200) {
        			break;
        		}
        		count++;
        	}
        	database.InsertData(entryList);
        }
        
        
        
        
}
