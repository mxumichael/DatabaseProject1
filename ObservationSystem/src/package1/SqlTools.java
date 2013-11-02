/*
 * Authors: Michael Xu, John Holmes
 * Project 1
 */
package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
			//JWH UPDATE 10/27
			PreparedStatement preStatement = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
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
	public static int insertWeightObservation(String qTY, String dTTM,
			int patientId) throws SQLException {
		Connection conn = makeMyConnection();
		try{
			String sql ="insert into WEIGHT(WEIGHTID, PATIENTID, QTY, DTTM, REC_DTTM) values (Weight_seq.nextval,'"
					+ patientId + "','" + qTY + "',to_date('" + dTTM + "','YYYYMMDD HH:MI AM'),sysdate)";

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
	public static int insertExerciseObservation(String dESCRIPTION,
			String dURATION, String dTTM, int patientId) throws SQLException {
		Connection conn = makeMyConnection();
		try{
			String sql ="insert into EXERCISE(EXERCISEID, PATIENTID, DESCRIPTION, DURATION, DTTM, REC_DTTM) values (EXERCISE_seq.nextval,'"
					+ patientId + "','" + dESCRIPTION + "','" + dURATION + "',to_date('" + dTTM + "','YYYYMMDD HH:MI AM'),sysdate)";

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
	public static int insertMoodObservation(String mOOD, String dTTM,
			int patientId) throws SQLException {
		Connection conn = makeMyConnection();
		try{
			String sql ="insert into MOOD(MOODID, PATIENTID, MOOD, DTTM, REC_DTTM) values (MOOD_seq.nextval,'"
					+ patientId + "','" + mOOD + "',to_date('" + dTTM + "','YYYYMMDD HH:MI AM'),sysdate)";

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
	/**
	 *  this subroutine prints out everything in the result set. doesn't matter how many columns the result set has, 
	 *  doesn't matter how many rows the result set has. prints to system.out
	 */
	public static void PrintResultSet(ResultSet observationData) {
		ResultSetMetaData metaData;
		try {
			metaData = observationData.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int index=1;index<=columnCount;index++){
				System.out.print(metaData.getColumnName(index));
				if (index!=columnCount){//comma seperator not needed for the last element.
					System.out.print(",");
				}
			}System.out.print("\n");
			System.out.println("==============================================");
			while(observationData.next()){
				for (int index=1;index<=columnCount;index++){
					Object toprint = observationData.getObject(index);
					if (toprint == null){
						System.out.print("");
					}else{
						System.out.print(observationData.getObject(index).toString());	
					}

					if (index!=columnCount){//comma seperator not needed for the last element.
						System.out.print(",");
					}
				}System.out.print("\n"); //end of the row, carriage return, line feed.
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * This subroutine takes an insert statement and runs it on the database.
	 * @param insertStatement
	 * @return int number of rows affected
	 * @throws SQLException 
	 */
	public static int InsertRunner(String insertStatement) throws SQLException{
		Connection conn = makeMyConnection();
		try{
			//creating PreparedStatement object to execute query
			PreparedStatement preStatement = conn.prepareStatement(insertStatement);

			//executing the query and then committing it before closing in the finally block.
			int result = preStatement.executeUpdate();		
			conn.commit();

			return result;
		}
		finally {
			conn.close();
		}		
	}
	public static int insertMessage(String toAddress, int patientId,
			String usermessage) throws SQLException {
		String sql = "insert into messages values (messages_seq.nextval,"+toAddress+","+patientId+",sysdate,'"+usermessage+"','N')";
			return SqlTools.InsertRunner(sql);
		
	}

/**

	 * returns a result set of the validation observation types that this patient can see.
	 * @param patientId
	 * @return resultset of observation names.
	 * @throws SQLException 
	 */

	public static ResultSet ValidObservations(int patientId) throws SQLException {
		Connection conn = makeMyConnection();
		try{

			//creating PreparedStatement object to execute query
			PreparedStatement preStatement = conn.prepareStatement("select pname from problems where patientid ="+patientId);
			//executing the query and then committing it before closing in the finally block.
			ResultSet result = preStatement.executeQuery();		
			String Hiv = "0=1";
			String COPD = "0=1";
			String HighRiskPregnancy = "0=1";
			String Obesity = "0=1";
			while (result.next()){
				if (result.getString(1).equals("HIV")){
					Hiv = "substr(association,1,1) = 1";
				}else if (result.getString(1).equals("COPD")){
					COPD = "substr(association,2,1) = 1";
				}else if (result.getString(1).equals("High Risk Pregnancy")){
					HighRiskPregnancy = "substr(association,3,1) = 1";
				}else if (result.getString(1).equals("Obesity")){
					Obesity = "substr(association,4,1) = 1";
				}
			}
			//creating PreparedStatement object to execute query
			preStatement = conn.prepareStatement("select obstype from observationtypes where ( "
			+Hiv+" or "+COPD+" or "+HighRiskPregnancy+" or "+Obesity+")" );
			//executing the query and then committing it before closing in the finally block.
			result = preStatement.executeQuery();		

			return result;
		} catch (SQLException e){

			e.printStackTrace();

		}
		return null;
/*		finally {
			conn.close();
		}
*/
	}
	/**
	 * give this subroutine a sql DML query and it'll execute!
	 * @param sql
	 * @return
	 */
	public static String CreateMeThis(String sql){
		Connection conn;
		try {
			conn = makeMyConnection();
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.executeUpdate(sql);		
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
