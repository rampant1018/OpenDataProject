package OPEarth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQL {
	public void InsertData(List<Entry> entryList) {
		int lastID = 0;
		String sql;
		
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			
			String url="jdbc:postgresql://210.61.10.89:9999/Team2";
			Connection con = DriverManager.getConnection(url,"Team2","lab2080362");
			Statement st = con.createStatement();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			sql = "SELECT MAX(id) FROM earthquakeinfo;";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				lastID = rs.getInt(1);
			}
			
			for(Entry entry : entryList) {
				sql = "INSERT INTO earthquakeinfo VALUES(" + (lastID + 1) + ", " + entry.longitude + ", " + entry.latitude + ", " + entry.magnitude + ", '" + formatter.format(entry.time) + "', " + "'" + entry.location + "');";
				lastID++;
				System.out.println(sql);
				st.execute(sql);
			}
			
			st.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public class Entry {
		float longitude;
		float latitude;
		float magnitude;
		Date time;
		String location;
		
		public Entry(float longitude, float latitude, float magnitude, Date time, String location) {
			this.longitude = longitude;
			this.latitude = latitude;
			this.magnitude = magnitude;
			this.time = time;
			this.location = location;
		}
	}
}
