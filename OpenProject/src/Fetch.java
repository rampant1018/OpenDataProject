import java.net.*;
import java.io.*;

public class Fetch {	
	public static String getJson(String startTime, String endTime){
		int chunksize = 4096;
		String rcv ="";
        byte[] chunk = new byte[chunksize];
        int count;
        try  {    
            URL pageUrl = new URL("http://comcat.cr.usgs.gov/fdsnws/event/1/query?starttime="+startTime+"&endtime="+endTime+"&format=geojson");
       
            BufferedInputStream bis = new BufferedInputStream(pageUrl.openStream());
            System.out.println("read1() running " );
            while ((count = bis.read(chunk, 0, chunksize)) != -1) {
                rcv += new String(chunk, "UTF-8");
            }
            bis.close();     
          
            
          System.out.println("Done");   
         // return rcv;
         }catch (IOException e) {
             e.printStackTrace();
         }
		return rcv;
	}
		
}
