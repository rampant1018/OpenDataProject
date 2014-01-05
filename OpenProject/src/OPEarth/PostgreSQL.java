package OPEarth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** API which is used to communicate with PostgreSQL database
 * 
 * @author rampant
 *
 */
public class PostgreSQL {
	/**
	 * Insert earthquake info into database
	 * @param entryList the data which need to be inserted
	 */
	public void InsertData(List<Entry> entryList) {
		int lastID = 0;
		String sql;
		
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			
			// Connection settings
			String url="jdbc:postgresql://210.61.10.89:9999/Team2";
			Connection con = DriverManager.getConnection(url,"Team2","lab2080362");
			
			Statement st = con.createStatement();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			// Get the last id from table
			sql = "SELECT MAX(id) FROM earthquakeinfo;";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				lastID = rs.getInt(1);
			}
			
			// Insert every info into table. Manually control id value.
			for(Entry entry : entryList) {
				sql = "INSERT INTO earthquakeinfo VALUES(" + (lastID + 1) + ", " + entry.longitude + ", " + entry.latitude + ", " + entry.magnitude + ", '" + formatter.format(entry.time) + "', " + "'" + entry.location + "');";
				lastID++;
				System.out.println(sql);
				st.execute(sql);
			}
			
			// Close connection
			st.close();
			con.close();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	/** Earthquake info data format. Contain 'longitude', 'latitude', 'magnitude', 'time', 'location'
	 * 
	 * @author rampant
	 *
	 */
	public class Entry {
		float longitude;
		float latitude;
		float magnitude;
		Date time;
		String location;
		
		/** 
		 * 
		 * @param longitude
		 * @param latitude
		 * @param magnitude 
		 * @param time timestamp with UNIX timestamp format
		 * @param location description of the location where the earthquake occur
		 */
		public Entry(float longitude, float latitude, float magnitude, Date time, String location) {
			this.longitude = longitude;
			this.latitude = latitude;
			this.magnitude = magnitude;
			this.time = time;
			this.location = location;
		}
	}
}
