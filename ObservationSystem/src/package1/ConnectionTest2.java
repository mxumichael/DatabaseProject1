package package1;
// TODO Auto-generated method stub
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Simple Java Program to connect Oracle database by using Oracle JDBC thin driver
 * Make sure you have Oracle JDBC thin driver in your classpath before running this program
 * 
 * This test runs a simple select statement on the patient table and returns the patient id and the first name of each patient.
 * @author
 */
public class ConnectionTest2 {

	public static void main(String[] args) {
		//URL of Oracle database server
		String url = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:ORCL"; 

		//properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "mxu");
		props.setProperty("password", "abc123");

		//creating connection to Oracle database using JDBC
		Connection conn;
		try {
			conn = DriverManager.getConnection(url,props);
			try{
				String sql ="select patientid,fname from patient";

				//creating PreparedStatement object to execute query
				PreparedStatement preStatement = conn.prepareStatement(sql);

				ResultSet result = preStatement.executeQuery();

				while(result.next()){
					System.out.println(" " +result.getString(1)+"," +result.getString(2));
				}
				System.out.println("done");
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/*	Output:
		Current Date from Oracle : 2012-04-12 17:13:49
		done

		Read more: http://javarevisited.blogspot.com/2012/04/java-program-to-connect-oracle-database.html#ixzz2flXdp3ye
	 */}

