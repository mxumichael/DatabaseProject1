package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SqlTools {

	private static Connection makeMyConnection() throws SQLException{
		//URL of Oracle database server
		String url = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:ORCL"; 

		//properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "jwholme2");
		props.setProperty("password", "sbct3st!");

		//creating connection to Oracle database using JDBC
		Connection conn = DriverManager.getConnection(url,props);
		return conn;
	}
	/**
	 * give this subroutine a sql query and it'll get you a 2d array of strings.
	 * We do this to separate SQL logic from Screens class methods
	 * @param sql
	 * @return
	 */
	public static ResultSet QueryMeThisArray(String sql) throws SQLException{
		Connection conn;
		
		//We will be storing the data as a table
		
		try {
			conn = makeMyConnection();
			PreparedStatement preStatement = conn.prepareStatement(sql);
			ResultSet result = preStatement.executeQuery();
			//While we have a row of data...
			return result;
		}		
		 catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	
		 }

	}
	
	/**
	 * give this subroutine a sql query and it'll get the first result for you as a string.
	 * @param sql
	 * @return
	 */
	public static String QueryMeThis(String sql){
		Connection conn;
		try {
			conn = makeMyConnection();
			PreparedStatement preStatement = conn.prepareStatement(sql);
			ResultSet result = preStatement.executeQuery();		
			if(result.next()){
				return result.getString(1);
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * returns "patient" if the login is a valid patient, "healthsupporter" if the login is a valid health supporter, and null if it is neither.
	 * @param username
	 * @param passw
	 * @return
	 * @throws SQLException 
	 */
	public static String LoginValid(String username, String passw) throws SQLException {
		// TODO This subroutine takes a username and password and checks if it's valid.
		Connection conn = makeMyConnection();
		try{
			String sql ="select * from PATIENT where username = '"
					+ username + "' and passw = '" + passw + "'";
			//creating PreparedStatement object to execute query
			PreparedStatement preStatement = conn.prepareStatement(sql);
			//executing the query and then committing it before closing in the finally block.
			ResultSet result = preStatement.executeQuery();		
			if (!result.next() ) {    
				System.out.println("No patients that match this username/password combination:"+username+"/"+passw); 
			} 
			else {
				return ("patient");
			}
			sql ="select * from HEALTHSUPPORTER where username = '"
					+ username + "' and passw = '" + passw + "'";
			//creating PreparedStatement object to execute query
			preStatement = conn.prepareStatement(sql);
			//executing the query and then committing it before closing in the finally block.
			result = preStatement.executeQuery();		
			if (!result.next() ) {    
				System.out.println("No healthsupporters that match this username/password combination:"+username+"/"+passw); 
			} 
			else {
				return ("healthsupporter");
			}
			return null;
		}
		finally {
			conn.close();
		}

	}

	public static int insertDietObservation(String description, String amount,
			String dateTime, int patientId) throws SQLException {
		// TODO Auto-generated method stub

		Connection conn = makeMyConnection();
		try{
			String sql ="insert into Diet(dietid,patientid,description,qty,dttm,rec_dttm) values (Diet_seq.nextval,"
					+ patientId + ",'" + description + "'," + amount + ",to_date('" + dateTime + "','YYYYMMDD HH:MI AM'),sysdate)";

			//creating PreparedStatement object to execute query
			PreparedStatement preStatement = conn.prepareStatement(sql);

			//executing the query and then committing it before closing in the finally block.
			int result = preStatement.executeUpdate();		
			conn.commit();

			return result;
		}
		finally {
			conn.close();
		}

	}

	public static int selectObservationTypes(String description, String amount,
			String dateTime, int patientId) throws SQLException {
		// TODO Auto-generated method stub

		Connection conn = makeMyConnection();
		try{
			String sql ="SELECT type FROM Custom_Observations";

			//creating PreparedStatement object to execute query
			PreparedStatement preStatement = conn.prepareStatement(sql);

			//executing the query and then committing it before closing in the finally block.
			int result = preStatement.executeUpdate();		
			conn.commit();

			return result;
		}
		finally {
			conn.close();
		}

	}



}
