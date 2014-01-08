package OPEarth;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.*;

public class Parser { 
	// Parser test function
	// Used for testing readFile function which can read json content from given file
	// And call getEventList function to parse json content
	// Display all event details: mag, location, time, longitude, latitude
	public void test() {
		List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();
		
		try {
			String content = readFile("testfile/query", StandardCharsets.UTF_8);
			eventList = getEventList(content);
			
			for(Map<String, String> event : eventList) {
				System.out.println("震度： " + event.get("mag") + ", 地點： " + event.get("location") + ", 時間： " + event.get("time") + ", 經度： " + event.get("longitude") + ", 緯度： " + event.get("latitude"));
			};
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Read json content from testfile
	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	
	// parse json content to get event detail
	public List<Map<String, String>> getEventList(String content) throws JSONException {
		List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();
		JSONObject jsonObj = new JSONObject(new JSONTokener(content));
		JSONArray eventArray = jsonObj.getJSONArray("features");
		
		for(JSONObject event : new IterableJSONArray(eventArray)){
			//System.out.println(event.toString());
        	Double mag, longitude, latitude;
			Long time;
			String location;
			
			if(!event.getJSONObject("properties").isNull("mag")) {
				mag = event.getJSONObject("properties").getDouble("mag");
			}
			else {
				mag = 0d;
			}
			
			if(!event.getJSONObject("properties").isNull("place")) {
				location = event.getJSONObject("properties").getString("place");
			}
			else {
				location = "";
			}
			
			if(!event.getJSONObject("properties").isNull("time")) {
				time = event.getJSONObject("properties").getLong("time");
			}
			else {
				time = 0L;
			}
			longitude = event.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0);
			latitude = event.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1);
			
			HashMap<String, String>eventDetail = new HashMap<String, String>();
			eventDetail.put("mag", mag.toString());
			eventDetail.put("location", location);
			eventDetail.put("time", time.toString());
			eventDetail.put("longitude", longitude.toString());
			eventDetail.put("latitude", latitude.toString());
			
			eventList.add(eventDetail);
		}
		
		return eventList;
	}
	
}
