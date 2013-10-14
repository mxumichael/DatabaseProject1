package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.util.Properties;

public class SqlTools {

	public static Boolean LoginValid(String username, String password) {
		// TODO This subroutine takes a username and password and checks if it's valid.
		return true; //uhhhh.... sure, that's a valid login i dunno :P
	}

	public static boolean insertDietObservation(String description, String amount,
			String dateTime, double patientId) throws SQLException {
		// TODO Auto-generated method stub
		//URL of Oracle database server
		String url = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:ORCL"; 

		//properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "mxu");
		props.setProperty("password", "abc123");
		
		//creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url,props);
		try{
		String sql ="insert into Diet(dietid,patientid,description,qty,dttm,rec_dttm) values (Diet_seq.nextval(),"
				+ patientId + "," + description + "," + amount + ",to_date(" + dateTime + ",'YYYYMMDD HH:MI'),sysdate);";

		//creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(sql);

		//executing the query and then committing it before closing in the finally block.
		boolean result = preStatement.execute();		
		conn.commit();
		
		return result;
		}
		finally {
			conn.close();
		}
		
	}


}
