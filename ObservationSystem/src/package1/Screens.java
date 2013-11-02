/*
 * Authors: Michael Xu, John Holmes
 * Project 1
 */
package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;


public class Screens {
	static int LOOP_LIMIT = 5;
	static String LOOP_LIMIT_ERROR= "Looplimitout";
	BufferedReader in;
	int patientId= 0;

	public static void main(String args[]){

		Screens session = new Screens();
		try {
			session.StartScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Goodbye, exiting program now");
	}

	private String LoginScreen() throws IOException{

		String username = "";
		String password = "";
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Login Screen                     ");
			System.out.println("Enter uid followed by password   ");
			System.out.print("username:");
			username = in.readLine();
			System.out.print("password:");
			password = in.readLine();
			String loginValid= null;
			try {
				loginValid = SqlTools.LoginValid(username,password);
				if (loginValid == null){
					System.out.println("login/password combination not found.");
				}
				else if (loginValid.equals("patient")){
					this.patientId=Integer.parseInt(SqlTools.QueryMeThis("select patientid from patient where username = '"+username+"'"));
					patientStartScreen();
					return("1. Login was Valid");
				}
				else if (loginValid.equals("healthsupporter")){
					this.patientId=Integer.parseInt(SqlTools.QueryMeThis("SELECT supporterid FROM HealthSupporter WHERE username = '"+username+"'"));
					healthSupporterStartScreen();
					return("1. Login was Valid");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("??? please try again.");
		}

		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}

	private String healthSupporterStartScreen() throws IOException{

		try
		{
			for (int x= 0; x< LOOP_LIMIT; x++){
				//Make userChoice local in scope to for loop...o/w we print the screen twice on a return
				String userChoice = null;

				System.out.println("Welcome to the HealthSupporter start screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
				System.out.println(" 1. Add a New Observation Type                                   ");
				System.out.println(" 2. Add an Association Between Observation Type and Illness      ");
				System.out.println(" 3. View Patients         ");
				System.out.println(" 4. Back                  ");
				System.out.println("Enter choice              ");

				userChoice = in.readLine();

				if (userChoice.equals("1")){
					this.AddObservationTypeScreen(true);
				} else if (userChoice.equals("2")){
					this.AddAssociationScreen();
				} else if (userChoice.equals("3")){
					this.viewPatientScreen();
				} else if (userChoice.equals("4")){
					return("4. Back");
				}
			}
			System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
			return LOOP_LIMIT_ERROR;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	private String viewPatientScreen() throws IOException {
		String userChoice = "";

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Welcome to the View Patient screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
			System.out.println(" 1. View by Observation Type  ");
			System.out.println(" 2. View by Patient Name       ");
			System.out.println(" 3. View by Patient Class       ");
			System.out.println(" 4. Back                  ");
			System.out.println("Enter choice              ");
			userChoice = in.readLine();
			if (userChoice.equals("1")){
				this.viewByObservationType();
			} else if (userChoice.equals("2")){
				this.viewPatientByName();
			} else if (userChoice.equals("3")){
				this.viewByPatientClass();
			} else if (userChoice.equals("4")){
				return("4. Back");
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}

	private String viewByPatientClass() {
		String userChoice = "";
		
		try
		{
			System.out.println("Welcome to the View By Patient Class screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));

			System.out.println("Enter choice           ");
			System.out.println("1. HIV                 ");
			System.out.println("2. COPD                ");
			System.out.println("3. High Risk Pregnancy           ");
			System.out.println("4. Obesity             ");
		
			userChoice = in.readLine();

			switch(userChoice)
			{
			case "1": 
				userChoice = "HIV";
				break;
			case "2":
				userChoice="COPD";
				break;
			case "3":
				userChoice="High Risk Pregnancy";
				break;
			case "4":
				userChoice="Obesity";
				break;	
			default:
				userChoice="HIV";	
				break;
			}

			//Print all observations for the selected choice
			ResultSet data = SqlTools.QueryMeThisArray("SELECT * " +
					"FROM PROBLEMS PROB, PATIENT P " +
					"WHERE PROB.PATIENTID=P.PATIENTID " +
					"AND PROB.PNAME=" + "'" + userChoice + "'");

			//Get metadata
			ResultSetMetaData dataMD = data.getMetaData();

			//Print the column names
			for (int ii=1;ii<dataMD.getColumnCount();ii++)
			{
				//Pad right
				System.out.print(String.format("%-30s", dataMD.getColumnName(ii)) + " ");			
			}

			//Put newline for readability
			System.out.println("");

			//Print the data
			while(data.next())
			{
				for (int ii=1;ii<dataMD.getColumnCount();ii++)
				{		
					System.out.print(String.format("%-30s", data.getString(ii)) + " ");		
				}
				System.out.println("");
			}

			return null;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}


	}

	private String viewByObservationType() throws IOException {
		String userChoice = "";

		try
		{

			System.out.println("Welcome to the View By Observation Type screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));

			//Gives us observation types that have patient data
			ResultSet results = SqlTools.QueryMeThisArray("SELECT * " +
					"FROM ObservationTypes");

			//Print the observation types
			while(results.next())
			{
				System.out.println(results.getRow() + ". " + results.getString("OBSTYPE"));

			}

			System.out.println("Enter choice              ");
			userChoice = in.readLine();

			//Move the resultSet to the particular row
			results.absolute(Integer.parseInt(userChoice));

			//Print
			System.out.println();
			System.out.println("Patient Data");				
			System.out.println();

			//Get patients associated with this observation type
			ResultSet patients = SqlTools.QueryMeThisArray("SELECT DISTINCT P.lname, P.fname " +
					"FROM " + results.getString("OBSTYPE") + " T, PATIENT P " +
					"WHERE T.PATIENTID=P.PATIENTID");

			//Print patients out
			System.out.println(String.format("%-30s%-30s", "Lastname","Firstname"));			
			while(patients.next())
			{

				System.out.println(String.format("%-30s%-30s", patients.getString(1),patients.getString(2)));		

			}

			//Print
			System.out.println();
			System.out.println("Detailed Observation Data");				
			System.out.println();


			//Print all observations for the selected choice
			ResultSet data = SqlTools.QueryMeThisArray("SELECT * " +
					"FROM " + results.getString("OBSTYPE") + " T, PATIENT P " +
					"WHERE T.PATIENTID=P.PATIENTID");

			//Get metadata
			ResultSetMetaData dataMD = data.getMetaData();

			//Print the column names
			for (int ii=1;ii<dataMD.getColumnCount();ii++)
			{
				//Pad right
				System.out.print(String.format("%-30s", dataMD.getColumnName(ii)) + " ");			
			}

			//Put newline for readability
			System.out.println("");

			//Print the data
			while(data.next())
			{
				for (int ii=1;ii<dataMD.getColumnCount();ii++)
				{		
					System.out.print(String.format("%-30s", data.getString(ii)) + " ");		
				}
				System.out.println("");
			}

			return null;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public String StartScreen() throws IOException{
		String userinput = "";
		this.in = new BufferedReader(new InputStreamReader(System.in)); 
		
		try {
			for (int x= 0; x< LOOP_LIMIT; x++){
				System.out.println("Start Screen");
				System.out.println(" 1. Login                                                                 ");
				System.out.println(" 2. Create User                                                           ");
				System.out.println(" 3. Exit                                                                  ");
				System.out.println("Enter choice                                                             ");
				
				userinput = in.readLine();
				
				if (userinput.equals("1")){
					this.LoginScreen();
				} else if (userinput.equals("2")){
					this.CreateUserScreen();
				} else if (userinput.equals("3")){
					return("3. Exit");
				}
			}
			System.out.println("reached Looplimit "+ LOOP_LIMIT + " in start screen, going to previous screen");
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally {in.close();	}
		return LOOP_LIMIT_ERROR;
	}

	private String CreateUserScreen() throws IOException {
		// TODO Auto-generated method stub extracomments
		String userinput = "";
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Create User Screen (under construction)                                ");
			System.out.println(" 1. Previous Screen                               ");
			userinput = in.readLine();
			if (userinput.equals("1")){
				return("1. Previous Screen");
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in create user screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}
	private String patientStartScreen() throws IOException{

		String userChoice = "";

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Welcome to the patient start screen "+SqlTools.QueryMeThis("select fname from patient where patientid = "+this.patientId));
			ResultSet messages;
			try {
				messages = SqlTools.QueryMeThisArray("select * from messages where viewed = 'N' and patientid ="+this.patientId);
				if (messages.isBeforeFirst()){
					System.out.println("you have new messages");
					SqlTools.PrintResultSet(messages);
					SqlTools.InsertRunner("update messages set viewed = 'Y' where viewed = 'N' and patientId ="+this.patientId);
				}
				else {
					System.out.println("no new messages");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println(" 1. Enter Observations         ");
			System.out.println(" 2. View Observations          ");
			System.out.println(" 3. Add a New Observation Type ");
			System.out.println(" 4. View MyAlerts              ");
			System.out.println(" 5. Manage HealthFriends       ");
			System.out.println(" 6. Back                       ");
			System.out.println("Enter choice                  ");
			userChoice = in.readLine();
			if (userChoice.equals("1")){
				this.EnterObservations();
			} else if (userChoice.equals("2")){
				this.ViewObservations();
			} else if (userChoice.equals("3")){
				this.AddObservationTypeScreen(false);
			} else if (userChoice.equals("4")){
				this.ViewMyAlerts();
			} else if (userChoice.equals("5")){
				this.ManageHealthFriends();
			} else if (userChoice.equals("6")){
				return("6. Back");
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}


	private String FindNewHealthfriend() throws IOException {
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--Find a new HealthFriend--      ");
			System.out.println(" input the patient id of the patient you want to become a healthfriend with, or enter 0 to go back to the previous screen.");
			try {
				ResultSet potentialFriends=null;
				potentialFriends = SqlTools.QueryMeThisArray("select p.* from Patient p where p.publicstatus ='T' and p.patientid not in "
						+ "(select h.healthfriendid from healthfriend h where h.patientid ="+this.patientId+")");
				SqlTools.PrintResultSet(potentialFriends);
				System.out.println("  Enter choice                  ");
				String userChoice = in.readLine();
				if (userChoice.equals("0")){
					return("0. Back");
				}
				else{
					int status = SqlTools.InsertRunner("insert into healthfriend columns (Patientid,healthfriendid,dttm,end_dttm) "
							+ "values ("+this.patientId+","+userChoice+",sysdate,null)");
					if (status != 0 ){System.out.println("health friend added.");}
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}

	private String ViewMyAlerts() {
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--View My Alerts--      ");
			try {
				ResultSet notViewedAlerts=null;
				notViewedAlerts = SqlTools.QueryMeThisArray("select a.* from Alerts a where a.viewed ='N' "
						+ "and a.patientid ="+this.patientId);
				SqlTools.PrintResultSet(notViewedAlerts);
				SqlTools.InsertRunner("update Alerts a set a.viewed = 'Y' where a.patientId ="+this.patientId);
				System.out.println(" press enter to go back to the previous screen.");
				in.readLine();
				return("done");
			}
			catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}

	private String ViewAHealthfriend(int patientid) throws IOException {
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--View a HealthFriend--      ");
			System.out.println(" 1. View a list of the friend’s active (unviewed) alerts");
			System.out.println(" 2. View observations of the friend");
			System.out.println(" 3. Back");
			System.out.println("  Enter choice                  ");
			String userChoice = in.readLine();
			ResultSet observationData=null;
			try {
				if (userChoice.equals("1")){
					observationData =SqlTools.QueryMeThisArray("select * from alerts where viewed = 'N' and  patientId ="+ patientid);
					SqlTools.PrintResultSet(observationData);					//time to print all the things in the result set
				} else if (userChoice.equals("2")){
					int currentPatientID = this.patientId;
					this.patientId=patientid;//setting the current patient id to the person who we're viewing for
					//the time being so that we can use the view observations sub-routine.
					this.ViewObservations();
					this.patientId=currentPatientID;
				} else if (userChoice.equals("3")){
					return("3. Back");
				} else{
					System.out.println("invalid choice, please try again.");
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}

	private String ManageHealthFriends() throws IOException {
		String userChoice = "";

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--Manage HealthFriends--      ");
			System.out.println(" 1. View existing HealthFriends ");
			System.out.println(" 2. Find a New HealthFriend ");
			System.out.println(" 3. Find a HealthFriend at Risk ");
			System.out.println(" 4. Back ");
			System.out.println("  Enter choice                  ");
			userChoice = in.readLine();
			ResultSet observationData=null;
			try {
				if (userChoice.equals("1")){
					observationData =SqlTools.QueryMeThisArray("select * from Healthfriend where patientId ="+this.patientId);
					//time to print all the things in the result set
					SqlTools.PrintResultSet(observationData);
					System.out.println("--View Existing HealthFriends--      ");
					System.out.println(" Enter healthfriendid to view that healthfriend ");
					System.out.println(" Enter choice");
					userChoice = in.readLine();
					//TODO: ideally we'd check to make sure this guy is actually friends with the current user, but for now, we'll just let anyone view anything.
					this.ViewAHealthfriend(Integer.parseInt(userChoice));
				} else if (userChoice.equals("2")){
					this.FindNewHealthfriend();
				} else if (userChoice.equals("3")){
					observationData =SqlTools.QueryMeThisArray("select h.healthfriendid,count(*) as \"number of unviewed alerts\" from Healthfriend h inner join alerts a on h.healthfriendid = a.patientid "
							+ "where h.patientId ="+this.patientId+"group by h.healthfriendid having count(*)>=5");
					if (!observationData.isBeforeFirst()){
						System.out.println("no HealthFriends are at risk");
					}
					else{ //meaning that there are results in the resultset
						SqlTools.PrintResultSet(observationData);
						System.out.println("Send a message to a friend? enter friend's id to start a message,"
								+ " or enter 0 to return to the previous screen");
						String toAddress = in.readLine();
						if (userChoice.equals("0")){
							break;
						}
						else{
							System.out.println("type in your message (limit 1024 characters) followed by the enter key.");
							String usermessage = in.readLine();
							SqlTools.insertMessage(toAddress,this.patientId,usermessage);
							System.out.println("message sent. returning to previous screen.");
						}
					}
				} else if (userChoice.equals("4")){
					return("4. Back");
				} else{
					System.out.println("invalid choice, please try again.");
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;


	}

	private String ViewObservations() throws IOException {
		String userChoice = "";

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--View observation type--      ");

			try {

				ResultSet  observationTypes = SqlTools.ValidObservations(this.patientId);
				ArrayList<String>  observationTypeList= new ArrayList<String>();
				while (observationTypes.next()){
					observationTypeList.add(observationTypes.getString(1));
					System.out.println("  "+observationTypeList.size()+". "+observationTypeList.get(observationTypeList.size()-1));


				}
				System.out.println("  0. Back      ");
				System.out.println("  Enter choice                  ");
				userChoice = in.readLine();
				ResultSet observationData=null;

				if (userChoice.equals("0")){
					return("0. Back");
				} else {
					observationData =SqlTools.QueryMeThisArray("select * from "+observationTypeList.get(Integer.parseInt(userChoice)-1)+" where patientId ="+this.patientId);
				}
				//time to print all the things in the result set
				SqlTools.PrintResultSet(observationData);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}


	private String EnterObservations() throws IOException {


		//mostly copied from view observations. if any changes occur there, might want to replicate them here.
		//a better programmer with more time would probably combine the two, but not me :P
		String userChoice = "";

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--Pick observation type--      ");
			try {

				ResultSet  observationTypes = SqlTools.ValidObservations(this.patientId);
				ArrayList<String>  observationTypeList= new ArrayList<String>();
				while (observationTypes.next()){
					observationTypeList.add(observationTypes.getString(1));
					System.out.println("  "+observationTypeList.size()+". "+observationTypeList.get(observationTypeList.size()-1));
				}
				System.out.println("  0. Back      ");
				System.out.println("  Enter choice                  ");
				userChoice = in.readLine();
				if (userChoice.equals("0")){
					return("0. Back");
				} else {
					EnterObservationSpecifics(observationTypeList.get(Integer.parseInt(userChoice)-1));
				}
			}


			catch (SQLException e) {
				e.printStackTrace();

			}

		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}

	private int EnterObservationSpecifics(String TableName) {

		String sql = "select * from "+TableName+" where rownum <=1";
		ResultSet results;
		try {
			results = SqlTools.QueryMeThisArray(sql);
			ResultSetMetaData metadata= results.getMetaData();
			String insertStatement="insert into "+TableName+" values ("+TableName+"_seq.nextval "; //this will hold the user inputs in a sql insert friendly way.
			System.out.println("===entering in observation for "+TableName +"===");
			for (int colNum=2;colNum<=metadata.getColumnCount();colNum++){//starting with column 2 because the first one is always the primary key
				if (metadata.getColumnName(colNum).equals("REC_DTTM")){
					insertStatement = insertStatement + " , sysdate";
					System.out.println("using sysdate as recording time.");

				}else if (metadata.getColumnName(colNum).equals("PATIENTID")){
					insertStatement = insertStatement + " , "+this.patientId+"";
					System.out.println("using "+this.patientId +" as patientid");
				}
				else{
					int columnType = metadata.getColumnType(colNum);
					if (columnType == Types.TIMESTAMP){
						System.out.println("Input date (YYYYMMDD HH:MI AM/PM) for column: "+metadata.getColumnName(colNum));
						insertStatement = insertStatement + " , to_date('" +in.readLine()+"','YYYYMMDD HH:MI AM')";

					}else if (columnType == Types.NUMERIC){
						System.out.println("Input number for column: "+metadata.getColumnName(colNum));
						insertStatement = insertStatement + " , " +in.readLine()+"";
					}else if (columnType == Types.VARCHAR){
						System.out.println("Input varchar for column: "+metadata.getColumnName(colNum));
						insertStatement = insertStatement + " , '" +in.readLine()+"'";
					}else {
						System.out.println("unknown sql type of column. Type ="+columnType);
						System.out.println("see http://docs.oracle.com/javase/7/docs/api/constant-values.html#java.sql.Types.ARRAY for details and ask dev team to add this sql type and give them this stack trace ");
						for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
							System.out.println(ste);
						}
					}
				}
			}

			insertStatement = insertStatement + ")";
			int rowsAffected = SqlTools.InsertRunner(insertStatement);
			System.out.println("Record inserted");
			return rowsAffected;
		} catch (SQLException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("something went wrong in EnterObservationSpecifics, returning to previous screen.");

		return 0;
	}

	private String AddAssociationScreen() {
		String obsType, patClass = null;

		try {

			System.out.println("Welcome to the Add Association screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));


			//Gives us observation types that have patient data
			ResultSet results = SqlTools.QueryMeThisArray("SELECT obstype, association " +
					"FROM ObservationTypes D");

			//Print the observation types
			while(results.next())
			{
				System.out.println(results.getRow() + ". " + results.getString("OBSTYPE"));

			}

			System.out.println("Enter choice              ");
			obsType = in.readLine();

			//Move the resultSet to the particular row
			results.absolute(Integer.parseInt(obsType));

			System.out.println("Enter association as bitfield (e.g. 1101)             ");
			System.out.println("(Example: 1110 means to associate this observation type with HIV, COPD, and High Risk Pregnancy but not Obesity):");
			System.out.println("(Example: 1111 means to make this observation type general):");

			//This could use some work to sanitize input
			patClass = in.readLine();

			results.updateString("association", patClass);
			results.updateRow();

			return null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private String AddObservationTypeScreen(boolean isHealthSupporter) {

		String obsType,category,attributes = "";
		char[] association = new char[4];
		int charsRead = 0;

		System.out.println("Add a New Observation Type Screen                     ");

		//Type name
		System.out.println("Enter Observation Type Name: ");

		try
		{
			obsType = in.readLine();

			//Category
			//Make sure input is OK
			do
			{
				System.out.println("Enter Observation Type Category:");
				System.out.println("1. Behavioral");
				System.out.println("2. Physiological");
				System.out.println("3. Psychological");
				category = in.readLine();

			} while(Integer.parseInt(category) != 1 && 
					Integer.parseInt(category) != 2 && Integer.parseInt(category) != 3 );

			//Convert integer to a string value, so we can insert that into our table
			switch(Integer.parseInt(category))
			{

			case 1:
				category="Behavioral";
				break;

			case 2:
				category="Physiological";
				break;

			case 3:
				category="Psychological";
				break;

			default:
				//we should not be here!
				break;

			}

			//Enter the attributes in a loop
			do
			{
				System.out.println("Enter Observation Type Attribute (enter 0 to stop):");
				String attribute = in.readLine();

				//Test if we should break the loop
				if (attribute.equals("0"))
				{
					break;
				}
				else
				{
					attributes=attributes + attribute + " VARCHAR2(128) NOT NULL,";
				}

			} while(true);

			//Enter the associations in a loop if physician
			if (isHealthSupporter)
			{
				do
				{
					//Association
					System.out.println("Enter Observation Type Associations (as bit field where 1 is YES and 0 is false):");
					System.out.println("(Example: 1110 means to associate this observation type with HIV, COPD, and High Risk Pregnancy but not Obesity):");
					System.out.println("(Example: 1111 means to make this observation type general):");
					charsRead = in.read(association,0,4);


				} while(charsRead!=4);

			}
			else
			{
				association[0]='1';
				association[1]='1';
				association[2]='1';
				association[3]='1';
			}
			//Now that we have the data, we can start executing the SQL DDL/DML statements
			//First we need to create the sequence
			String seq = "CREATE SEQUENCE " + obsType + "_seq " +
					"START WITH 1 "                        +
					"INCREMENT BY 1 "                      +
					"CACHE 20";

			//Next we need to create the table
			String sql = "CREATE TABLE " + obsType + " " +
					"(obstypeid NUMBER(10)," +
					" patientid NUMBER(10)," +
					attributes +
					" dttm DATE NOT NULL," +
					" rec_dttm DATE NOT NULL," +
					" CONSTRAINT fk_"+obsType+"_patientid FOREIGN KEY (patientid) REFERENCES Patient," +
					" CONSTRAINT " +obsType+"_PK PRIMARY KEY (obstypeid)" +
					")";


			//FInally, we need to update the observations table
			String updObs = "INSERT INTO OBSERVATIONTYPES " +
					"VALUES(OBSERVATIONTYPES_SEQ.nextval, " +
					"'" + category + "'" + "," +
					"'" + obsType  + "'" + "," +
					"'" + association[0]+association[1]+association[2]+association[3] + "'" +
					")";


			//First update observations table
			SqlTools.CreateMeThis(updObs);

			//Next create the sequence
			SqlTools.CreateMeThis(seq);

			//Next create the table
			SqlTools.CreateMeThis(sql);
			return null;

		}

		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}


	}


	private String viewPatientByName() throws IOException {
		String lastName,firstName= "";
		ResultSet patInfo = null;


		System.out.println("Welcome to the View Patient By Name screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));

		try
		{
			//Read lastname
			System.out.println(" Enter patient lastname  ");
			lastName = in.readLine();
			//Read firstame
			System.out.println(" Enter patient firstname  ");
			firstName = in.readLine();

			//Display patient information

			//Go get the results!
			patInfo = SqlTools.QueryMeThisArray("SELECT *"          +
					"FROM PATIENT P "   +
					"WHERE P.lname ='" + lastName + "' AND P.fname='" + firstName +"'");

			//If there is a patient
			if (patInfo.next())
			{


				do
				{

					System.out.println();
					System.out.println("Demographic information\n");
					System.out.println();

					System.out.println(String.format("%-20s%-20s%-30s%-10s%-50s", "Firstname", 
							"Lastname", "DOB", "Gender", "Address"));

					System.out.println(String.format("%-20s%-20s%-30s%-10s%-50s", 
							patInfo.getString("fname"), 
							patInfo.getString("lname"), 
							patInfo.getString("DOB"),
							patInfo.getString("Gender"),
							patInfo.getString("Street")));


					//Store the patient id - we will need it to retrieve the observations
					int patientID = patInfo.getInt("PatientID");

					//Display observation data
					System.out.println();
					System.out.println("Observation information\n");
					System.out.println();

					//Get Observations list
					ResultSet cust = SqlTools.QueryMeThisArray("SELECT OBSTYPE "                         +
							"FROM OBSERVATIONTYPES ");

					while(cust.next())
					{
						//Print the observation name
						System.out.println(cust.getString("OBSTYPE"));

						//Print all observations entries for the type
						ResultSet data = SqlTools.QueryMeThisArray("SELECT * " +
								"FROM " + cust.getString("OBSTYPE") + " T, PATIENT P " +
								"WHERE T.PATIENTID=P.PATIENTID AND P.PATIENTID=" + patientID + " " +
								"ORDER BY dttm DESC");

						//Get metadata
						ResultSetMetaData dataMD = data.getMetaData();

						//Print the column names
						for (int ii=1;ii<dataMD.getColumnCount();ii++)
						{
							//Pad right
							System.out.print(String.format("%-30s", dataMD.getColumnName(ii)) + " ");			
						}

						//Put newline for readability
						System.out.println("");

						//Print the data
						while(data.next())
						{
							for (int ii=1;ii<dataMD.getColumnCount();ii++)
							{		
								System.out.print(String.format("%-30s", data.getString(ii)) + " ");		
							}
							System.out.println("");

						}

						System.out.println();


					}


				} while (patInfo.next());
			}
			else
			{
				System.out.println("Patient does not exist!");
				return null;
			}
			return null;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;			
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}


	}

}

