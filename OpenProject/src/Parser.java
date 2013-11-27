import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

public class Parser {
	List<Map<String, String>> eventList = new ArrayList();;
	
	public void test() {
		try {
			String content = readFile("testfile/query.txt", StandardCharsets.UTF_8);
			getEventList(content);
			
			for(int i = 0; i < eventList.size(); i++) {
				System.out.println("震度： " + eventList.get(i).get("mag") + ", 地點： " + eventList.get(i).get("location") + ", 時間： " + eventList.get(i).get("time") + ", 經度： " + eventList.get(i).get("longitude") + ", 緯度： " + eventList.get(i).get("latitude"));
			}
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	
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
