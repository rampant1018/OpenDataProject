package OPEarth;

import java.net.*;
import java.io.*;

public class Fetch {
	/**
	 * URL download packet size
	 */
	static final int chunksize = 4096;
	
	/** Get geojson data from USGS
	 * 
	 * @param startTime specified the start time of earthquake info
	 * @param endTime specified the end time of earthquake info
	 * @return geojson data with String format
	 */
	public String getJson(String startTime, String endTime){
        byte[] chunk = new byte[chunksize];
        int count; // data size

		String rcv = "";
		boolean finished = false;
		while(!finished) {
	        try {
	        	URL pageUrl = new URL("http://comcat.cr.usgs.gov/fdsnws/event/1/query?starttime="+startTime+"&endtime="+endTime+"&format=geojson&minlatitude=-70&maxlatitude=80&minlongitude=75&maxlongitude=325");
	        	BufferedInputStream bis = new BufferedInputStream(pageUrl.openStream());
	        	// read information per 4096(predefined) size and add to string variable "rcv"
	            while ((count = bis.read(chunk, 0, chunksize)) != -1) {
	                rcv += new String(chunk, 0,count);
	            }
	
	            bis.close(); // close bufferedinputstream*/
	            finished = true;
	        }
	        catch (IOException e) {
	             e.printStackTrace();
	        }
		}
        
		return rcv;
	}	
}
