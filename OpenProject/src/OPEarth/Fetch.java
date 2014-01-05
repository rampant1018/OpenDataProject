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
	public static String getJson(String startTime, String endTime){
        byte[] chunk = new byte[chunksize];
        int count; // data size

		String rcv = ""; 
        try {    
        	URL pageUrl = new URL("http://comcat.cr.usgs.gov/fdsnws/event/1/query?starttime="+startTime+"&endtime="+endTime+"&format=geojson&&minlatitude=-55&maxlatitude=65&minlongitude=90&maxlongitude=325");
        	BufferedInputStream bis = new BufferedInputStream(pageUrl.openStream());
        	// read information per 4096(predefined) size and add to string variable "rcv"
            while ((count = bis.read(chunk, 0, chunksize)) != -1) {
                rcv += new String(chunk, 0,count);
            }

            bis.close(); // close bufferedinputstream
        }
        catch (IOException e) {
             e.printStackTrace();
        }
        
		return rcv;
	}	
}
