//import library
import java.net.*;
import java.io.*;

//Fetch class
public class Fetch {
	
	/*
	 * fetch web information and create json file
	 * argument: input the time interval for information
	 */
	public static String getJson(String startTime, String endTime){
		
		//information packet size
		int chunksize = 4096;
        byte[] chunk = new byte[chunksize];
		//receive string
		String rcv = "";
		//the size of read from bufferedinputstream 
        int count;
        //try to do get information from URL
        try {    
        	//send request to URL and get return information
        	URL pageUrl = new URL("http://comcat.cr.usgs.gov/fdsnws/event/1/query?starttime="+startTime+"&endtime="+endTime+"&format=geojson");
            //get information from openStream
        	BufferedInputStream bis = new BufferedInputStream(pageUrl.openStream());
        	//read information per 4096 size and add to string variable "rcv"
            while ((count = bis.read(chunk, 0, chunksize)) != -1) {
                rcv += new String(chunk, 0,count);
            }
            //close bufferedinputstream
            bis.close();
        }
        //exception catcher
        catch (IOException e) {
             e.printStackTrace();
        }
        //return string variable "rcv"
		return rcv;
	}	
}
