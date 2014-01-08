package OPEarth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	Connection con;
	
	public void Connect() {
		// Connection settings
		String url="jdbc:postgresql://210.61.10.89:9999/Team2";
			
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection(url,"Team2","lab2080362");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public void DisConnect() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Insert earthquake info into database
	 * @param entryList the data which need to be inserted
	 */
	public void InsertData(List<Entry> entryList) {
		int lastID = 0;
		String sql;
		
		try {
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
				st.execute(sql);
			}
			
			System.out.println("Insert done");
			st.close();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public List<Entry> getEntryList() {
		String sql;
		List<Entry> entryList = new ArrayList<Entry>();
		
		try {
			Statement st = con.createStatement();
			sql = "SELECT * FROM earthquakeinfo where magnitude >= 4;";
			ResultSet result = st.executeQuery(sql);
			
			while(result.next()) {
				Calendar cal = Calendar.getInstance();
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			    cal.setTime(sdf.parse(result.getString("time")));
				entryList.add(new Entry(result.getFloat("longitude"), result.getFloat("latitude"), result.getFloat("magnitude"), cal.getTimeInMillis(), result.getString("location")));
			}
						
			st.close();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return entryList;
	}
	
	/**
	 * Get the latest earthquake info time
	 * @return timestamp(millseconds)
	 */
	public Long GetLatestTime() {
		String sql;
		Long resultTime = null;
		
		try {
			Statement st = con.createStatement();
			sql = "SELECT MAX(time) FROM earthquakeinfo;";
			ResultSet result = st.executeQuery(sql);
			
			while(result.next()) {
				if(result.wasNull()) {
					resultTime = null;
				}
				else {
					Calendar cal = Calendar.getInstance();
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				    cal.setTime(sdf.parse(result.getString(1)));
				    resultTime = cal.getTimeInMillis();
				}
			}
			
			st.close();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		return resultTime;
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
		long time;
		String location;
		
		/** 
		 * 
		 * @param longitude
		 * @param latitude
		 * @param magnitude 
		 * @param time timestamp with UNIX timestamp format
		 * @param location description of the location where the earthquake occur
		 */
		public Entry(float longitude, float latitude, float magnitude, long time, String location) {
			this.longitude = longitude;
			this.latitude = latitude;
			this.magnitude = magnitude;
			this.time = time;
			this.location = location;
		}
	}
}
