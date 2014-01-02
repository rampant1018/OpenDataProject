package JUnit;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import OPEarth.PostgreSQL;
import OPEarth.PostgreSQL.Entry;

public class PostgreSQLTest {
	PostgreSQL database;
	List<PostgreSQL.Entry> entryList;
	
	@Before
	public void setUp() throws Exception {
		database = new PostgreSQL();
    	entryList = new ArrayList<PostgreSQL.Entry>();
	}

	@After
	public void tearDown() throws Exception {
		database = null;
		entryList = null;
	}

	@Test
	public void testInsertData() {	
    	entryList.add(database.new Entry(14.29891f, 145.2153f, 3.2f, new Date(1378359819620L), "SW of Tanaga Volcano, Alaska"));
    	entryList.add(database.new Entry(30.21218f, -179.6285f, 2.7f, new Date(1378358536560L), "27km ENE of West Yellowstone, Montana"));
    	entryList.add(database.new Entry(14.9454f, -160.17769f, 5.0f, new Date(1378358476400L), "3km NNW of The Geysers, California"));
    	entryList.add(database.new Entry(39.16982f, 147.6754f, 1.7f, new Date(1378357983300L), "22km E of Honaunau-Napoopoo, Hawaii"));
    	entryList.add(database.new Entry(50.59983f, -174.21519f, 3.1f, new Date(1378357180200L), "97km SSW of Atka, Alaska"));
    	entryList.add(database.new Entry(49.46012f, -155.24419f, 2.2f, new Date(1378357074000L), "85km SSW of Atka, Alaska"));
    	
    	database.InsertData(entryList);
	}
	
}
