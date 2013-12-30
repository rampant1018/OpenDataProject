package OPEarth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQL {
	public void InsertData(float longitude, float latitude, float magnitude, Date time, String location) {
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
			
			sql = "INSERT INTO earthquakeinfo VALUES(" + (lastID + 1) + ", " + longitude + ", " + latitude + ", " + magnitude + ", '" + formatter.format(time) + "', " + "'" + location + "');";
			System.out.println(sql);
			
			st.executeQuery(sql);
			/*
			while(rs.next()) {
				System.out.println(rs.getString(2));
			}
			*/
			st.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}
}
