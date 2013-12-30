package JUnit;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PostgreSQLTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenTimeSeriesChart() {	
		try{
			Class.forName("org.postgresql.Driver").newInstance();
			
			String url="jdbc:postgresql://210.61.10.89:9999/Team2";
			Connection con = DriverManager.getConnection(url,"Team2","lab2080362");
			Statement st = con.createStatement();
			
			String sql = "CREATE TABLE ttt(id INTEGER NOT NULL PRIMARY KEY, name varchar(1002) NOT NULL);";
			//st.execute(sql);
			//System.out.println("Create Table");
			///String sql4 = "DROP TABLE ttt;";
			
			/* 插入D筆資料, 每筆包含id跟長度L的字串*/
			int D = 2000;
			int L = 1000;
			String seq="";
			for(int i=0;i<L;i++)
				seq+="!";
			for(int i=1000;i<D;i++)
			{
				System.out.println("已插入"+i+"筆資料");
				sql="INSERT INTO ttt VALUES("+i+",'"+seq+"')";
				st.execute(sql);
			}
			System.out.println("全數資料已插入");
			
			/* 刪除Table並重新建立Table
			st.execute(sql4);
			st.execute(sql);
			/**/
			
			st.close();
			con.close();

		}
		catch(Exception ee){
			System.out.print(ee.getMessage());
		}
	}
	
}
