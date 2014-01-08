package OPEarth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

/** Used to parse JSON content to simple format
 * 
 * @author rampant
 *
 */
public class Parser { 
	/** Parse JSON content to get event detail
	 * 
	 * @param content JSON content
	 * @return a list with earthquakes event
	 * @throws JSONException
	 */
	public List<Map<String, String>> getEventList(String content) throws JSONException {
		List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();
		JSONObject jsonObj = new JSONObject(new JSONTokener(content));
		JSONArray eventArray = jsonObj.getJSONArray("features");
		
		for(JSONObject event : new IterableJSONArray(eventArray)){
			//System.out.println(event.toString());
        	Double mag, longitude, latitude;
			Long time;
			String location;
		
			if(!event.getJSONObject("properties").isNull("mag")) { // null check
				mag = event.getJSONObject("properties").getDouble("mag");
			}
			else {
				mag = 0d;
			}
			
			if(!event.getJSONObject("properties").isNull("place")) { // null check
				location = event.getJSONObject("properties").getString("place");
			}
			else {
				location = "";
			}
			
			if(!event.getJSONObject("properties").isNull("time")) { // null check
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
