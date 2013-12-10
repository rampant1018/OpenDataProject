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
	List<Map<String, String>> eventList = new ArrayList();;
	
	// Parser test function
	// Used for testing readFile function which can read json content from given file
	// And call getEventList function to parse json content
	// Display all event details: mag, location, time, longitude, latitude
	public void test() {
		try {
			String content = readFile("testfile/query", StandardCharsets.UTF_8);
			getEventList(content);
			
			for(int i = 0; i < eventList.size(); i++) {
				System.out.println("震度： " + eventList.get(i).get("mag") + ", 地點： " + eventList.get(i).get("location") + ", 時間： " + eventList.get(i).get("time") + ", 經度： " + eventList.get(i).get("longitude") + ", 緯度： " + eventList.get(i).get("latitude"));
			}
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Parser test function
	// Liked upper test function, but use given json string instead read from testfile
	// And call getEventList function to parse json content
	// Display all event details: mag, location, time, longitude, latitude
	public void test(String content) {
		try {
			getEventList(content);
			
			for(int i = 0; i < eventList.size(); i++) {
				System.out.println("震度： " + eventList.get(i).get("mag") + ", 地點： " + eventList.get(i).get("location") + ", 時間： " + eventList.get(i).get("time") + ", 經度： " + eventList.get(i).get("longitude") + ", 緯度： " + eventList.get(i).get("latitude"));
			}
			
		} catch (JSONException e) {
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
	private void getEventList(String content) throws JSONException {
		JSONObject jsonObj = new JSONObject(new JSONTokener(content));
		JSONArray eventArray = jsonObj.getJSONArray("features");
		
		for(int i = 0; i < eventArray.length(); i++) {
			JSONObject event = eventArray.getJSONObject(i);
			
			Double mag, longitude, latitude;
			Long time;
			String location;
			mag = event.getJSONObject("properties").getDouble("mag");
			location = event.getJSONObject("properties").getString("place");
			time = event.getJSONObject("properties").getLong("time");
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
	}
}
