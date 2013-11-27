import java.net.*;
import java.io.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String web = "http://comcat.cr.usgs.gov/fdsnws/event/1/query?starttime=2013-09-05&endtime=2013-09-06&format=geojson";
		
		String temp;
		temp = Fetch.getJson("2013-09-05","2013-09-06");
		System.out.println(temp);
	}

}
