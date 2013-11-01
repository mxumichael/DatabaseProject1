package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


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
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("??? please try again.");
		}

		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}

	private String healthSupporterStartScreen() throws IOException{
		// TODO Auto-generated method stub
		String userChoice = "";

		try
		{
			for (int x= 0; x< LOOP_LIMIT; x++){
				System.out.println("Welcome to the patient start screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
				System.out.println(" 1. Add a New Observation Type                                   ");
				System.out.println(" 2. Add an Association Between Observation Type and Illness      ");
				System.out.println(" 3. View Patients         ");
				System.out.println(" 4. Back                  ");
				System.out.println("Enter choice              ");

				userChoice = in.readLine();

				if (userChoice.equals("1")){
					this.AddObservationTypeScreen();
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
		// TODO Auto-generated method stub
		String userChoice = "";

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Welcome to the patient view screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
			System.out.println(" 1. View by Observation Type  ");
			System.out.println(" 2. View by Patient Name       ");
			System.out.println(" 3. Back                  ");
			System.out.println("Enter choice              ");
			userChoice = in.readLine();
			if (userChoice.equals("1")){
				this.viewByObservationType();
			} else if (userChoice.equals("2")){
				this.viewPatientByName();
			} else if (userChoice.equals("3")){
				return("3. Back");
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}

	private String viewByObservationType() throws IOException {
		String userChoice = "";



		try

		{


			for (int x= 0; x< LOOP_LIMIT; x++){
				System.out.println("Welcome to the View By Observation Type screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));

				//Gives us observation types that have patient data
				ResultSet results = SqlTools.QueryMeThisArray("SELECT * " +
						"FROM ObservationTypes D");

				//Print the observation types
				while(results.next())
				{
					System.out.println(results.getRow() + ". " + results.getString("OBSTYPE"));

				}

				System.out.println("Enter choice              ");
				userChoice = in.readLine();

				//Move the resultSet to the particular row
				results.absolute(Integer.parseInt(userChoice));

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
			System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
			return LOOP_LIMIT_ERROR;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			return null;
		}


	}


	/*
		private String viewByObservationType() throws IOException {
		String userChoice = "";

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Welcome to the View By Observation Type screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
			System.out.println(" Behavioral  ");
			System.out.println(" 1. Diet  ");
			System.out.println(" 2. Weight       ");
			System.out.println(" 3. Exercise          ");
			System.out.println(" Physiological  ");
			System.out.println(" 4. Blood Pressure                  ");
			System.out.println(" 5. Exercise Tolerance                 ");
			System.out.println(" 6. O2 Saturation                  ");
			System.out.println(" 7. Pain                  ");
			System.out.println(" 8. Contraction                  ");
			System.out.println(" 9. Temperature                  ");
			System.out.println(" Psychological  ");
			System.out.println(" 10. Mood                  ");
			System.out.println(" Custom Observations  ");
			System.out.println(" 11. Custom Types                  ");
			System.out.println(" 12. Back                  ");
			System.out.println("Enter choice              ");
			userChoice = in.readLine();
			if (userChoice.equals("1"))
			{
				this.viewDietObservations();
			} 
			else if (userChoice.equals("2"))
			{
				this.viewWeightObservations();
			} 
			else if (userChoice.equals("3"))
			{
				this.viewExerciseObservations();
			} 
			else if (userChoice.equals("4"))
			{
				this.viewBPObservations();
			} 
			else if (userChoice.equals("5"))
			{
				this.viewETObservations();
			} 
			else if (userChoice.equals("6"))
			{
				this.viewO2Observations();
			} 
			else if (userChoice.equals("7"))
			{
				this.viewPainObservations();
			} 
			else if (userChoice.equals("8"))
			{
				this.viewContrObservations();
			} 
			else if (userChoice.equals("9"))
			{
				this.viewTempObservations();
			} 
			else if (userChoice.equals("10"))
			{
				this.viewMoodObservations();
			}
			else if (userChoice.equals("11"))
			{
				this.viewCustObservations();
			} 
			else if (userChoice.equals("12"))
			{
				return("12. Back");	
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;


	}
	 */

	private String viewDietObservations() {


		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM DIET D, PATIENT P "            +
					"WHERE D.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String viewWeightObservations() {


		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM WEIGHT W, PATIENT P "            +
					"WHERE W.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String viewExerciseObservations() {

		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM EXERCISE E, PATIENT P "            +
					"WHERE E.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String viewBPObservations() {

		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM BloodPressure B, PATIENT P "            +
					"WHERE B.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String viewETObservations() {
		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM ExerciseTolerance ET, PATIENT P "            +
					"WHERE ET.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String viewO2Observations() {


		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM OxSaturation ET, PATIENT P "            +
					"WHERE ET.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String viewPainObservations() {


		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM PAIN A, PATIENT P "            +
					"WHERE A.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String viewContrObservations() {


		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM CONTRACTION C, PATIENT P "            +
					"WHERE C.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}
	private String viewTempObservations() {


		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM TEMPERATURE T, PATIENT P "            +
					"WHERE T.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String viewMoodObservations() {


		try
		{
			//Pass the query and get results back
			ResultSet results = SqlTools.QueryMeThisArray("SELECT DISTINCT p.patientid, p.lname, p.fname " +
					"FROM MOOD M, PATIENT P "            +
					"WHERE M.patientid=P.patientid");

			System.out.println("PatientID, Lastname, Firstname");
			while(results.next())
			{		
				System.out.println(results.getString(1) + "," + results.getString(2) + "," +  results.getString(3)  );
				//System.out.println(results.getString("qty") + results.getString("rec_dttm") + results.getString("fname")  );
			}
		}
		catch(SQLException e)
		{		
			// TODO Auto-generated catch block		
			e.printStackTrace();
			return null;
		}

		return null;
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
				//		System.out.println("                                                                           ");
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
				this.NewObservationType();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("something went wrong in EnterObservationSpecifics, returning to previous screen.");
		return 0;
	}

	/*		String userChoice = "";

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("  1. Diet      ");
			System.out.println("  2. Weight    ");
			System.out.println("  3. Exercise  ");
			System.out.println("  4. Mood      ");
			System.out.println("  5. Other     ");
			System.out.println("  6. Back      ");
			System.out.println("  Enter choice                  ");
			userChoice = in.readLine();
			if (userChoice.equals("1")){
				this.DietObservation();
			} else if (userChoice.equals("2")){
				this.WeightObservation();
			} else if (userChoice.equals("3")){
				this.ExerciseObservation();
			} else if (userChoice.equals("4")){
				this.MoodObservation();
			} else if (userChoice.equals("5")){
				this.OtherObservation();
			} else if (userChoice.equals("6")){
				return("6. Back");
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}
	 */
	private void OtherObservation() {
		// TODO Auto-generated method stub

	}

	private String MoodObservation() {
		// MOODID, PATIENTID, MOOD, DTTM, REC_DTTM
		String MOOD = "";
		String DTTM = "";
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--Enter in your--");
			try {
				System.out.print("MOOD:");
				MOOD = in.readLine();
				System.out.print("Date time of observation (YYYYMMDD HH:MI AM/PM):");
				DTTM = in.readLine();
				SqlTools.insertMoodObservation(MOOD,DTTM,patientId);
				return "Observation Inserted Successfully";
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("observation input not Valid, please try again.");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;		
	}

	private String ExerciseObservation() {
		// EXERCISEID, PATIENTID, DESCRIPTION, DURATION, DTTM, REC_DTTM
		String DESCRIPTION = "";
		String DURATION = "";
		String DTTM = "";
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--Enter in your--");
			try {
				System.out.print("Description:");
				DESCRIPTION = in.readLine();
				System.out.print("DURATION:");
				DURATION = in.readLine();
				System.out.print("Date time of observation (YYYYMMDD HH:MI AM/PM):");
				DTTM = in.readLine();
				SqlTools.insertExerciseObservation(DESCRIPTION,DURATION,DTTM,patientId);
				return "Observation Inserted Successfully";
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("observation input not Valid, please try again.");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;		
	}

	private String WeightObservation() {
		// WEIGHTID, PATIENTID, QTY, DTTM, REC_DTTM
		String QTY = "";
		String DTTM = "";
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("--Enter in your--");
			System.out.print("Weight:");
			try {
				QTY = in.readLine();
				System.out.print("Date time of observation (YYYYMMDD HH:MI AM/PM):");
				DTTM = in.readLine();
				SqlTools.insertWeightObservation(QTY,DTTM,patientId);
				return "Observation Inserted Successfully";
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("observation input not Valid, please try again.");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;		
	}

	private String DietObservation() throws IOException {
		// TODO Auto-generated method stub
		String description = "";
		String amount = "";
		String dateTime = "";
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("  Enter:                  ");
			System.out.print("What food or drink was consumed:");
			description = in.readLine();
			System.out.print("Amount in servings:");
			amount = in.readLine();
			System.out.print("Date time of observation (YYYYMMDD HH:MI AM/PM):");
			dateTime = in.readLine();

			try {
				SqlTools.insertDietObservation(description,amount,dateTime,patientId);
				return "Observation Inserted Successfully";
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("observation input not Valid, please try again.");
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;		
	}


	//JWH CHANGES

	private String AddAssociationScreen() {
		String obsType, patClass = null;
		ResultSet custObsData = null;

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
			patClass = in.readLine();


			results.updateString("association", patClass);
			results.updateRow();


			/*		
		System.out.println(" Choose Observation Type:  ");
		System.out.println(" Behavioral  ");
		System.out.println(" 1. Diet  ");
		System.out.println(" 2. Weight       ");
		System.out.println(" 3. Exercise          ");
		System.out.println(" Physiological  ");
		System.out.println(" 4. Blood Pressure                  ");
		System.out.println(" 5. Exercise Tolerance                 ");
		System.out.println(" 6. O2 Saturation                  ");
		System.out.println(" 7. Pain                  ");
		System.out.println(" 8. Contraction                  ");
		System.out.println(" 9. Temperature                  ");
		System.out.println(" Psychological  ");
		System.out.println(" 10. Mood                  ");
		System.out.println(" Custom Observations  ");
		//Grab those custom types!

		custObsData = SqlTools.QueryMeThisArray("SELECT * " +
		                                        "FROM CUSTOMOBSERVATIONTYPES");

		//Iterate through the custom types and print them out

		while (custObsData.next())
		{		
			System.out.println(Integer.toString(10 + custObsData.getRow()) + ". " + custObsData.getString(3) );
		}

		System.out.println(" 0. Back                  ");
		System.out.println("Enter choice              ");



		System.out.println("Enter observation type              ");
		obsType = in.readLine();


		System.out.println("You TYPED" + obsType);
			 *
			 */
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

	private String AddObservationTypeScreen() {
		// TODO Auto-generated method stub

		String obsType, category,attributes = "";


		/*
		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Add a New Observation Type Screen                     ");

			//Type name
			System.out.println("Enter Observation Type Name: ");
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


				switch(category)
				{


				case "1":

					category = "Behavioral"; 
					break;

				default: 

					break;

				}
		 */
		/*				
			}while(Integer.parseInt(category) != 1 || Integer.parseInt(category) != 2 || Integer.parseInt(category) != 3 );

			do
			{
				System.out.println("Enter Observation Type Attribute (enter 0 to stop):");
				String attribute = in.readLine();

				//Test if we should break the loop
				if (Integer.parseInt(attribute) == 0)
				{
					break;
				}
				else
				{
					attributes=attributes + attribute + " VARCHAR2(128) NOT NULL,";
				}

			} while(true);

		 */		
		/*
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
			                "VALUES(OBSERVATIONTYPES_SEQ.nextval " +
			                "OBSCATEGORY + "
			                + ""
			                + "TABLE " + obsType + " " +
			             "(obstypeid NUMBER(10)," +
			             " patientid NUMBER(10)," +
			             attributes +
			             " dttm DATE NOT NULL," +
			             " rec_dttm DATE NOT NULL," +
			             " CONSTRAINT fk_"+obsType+"_patientid FOREIGN KEY (patientid) REFERENCES Patient," +
			             " CONSTRAINT " +obsType+"_PK PRIMARY KEY (obstypeid)" +
			             ")";


			SqlTools.QueryMeThis(sql);
		 */
		//}


		//System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		//return LOOP_LIMIT_ERROR;
		return null;

	}


	private String viewPatientByName() throws IOException {
		String lastName,firstName= "";
		ResultSet patInfo = null;


		System.out.println("Welcome to the patient view screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));

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
					System.out.println("Demographic information\n");
					System.out.println("Firstname Lastname, DOB, Gender, Address");
					System.out.println(patInfo.getString("fname") + patInfo.getString("lname") + "," + patInfo.getString("DOB")+ patInfo.getString("Gender") + "\n" + patInfo.getString("Street") + "\n");

					//Store the patient id - we will need it to retrieve the observations
					int patientID = patInfo.getInt("PatientID");

					//Display observation data
					System.out.println("Observation information\n");

					//Get diet observations

					ResultSet diet = SqlTools.QueryMeThisArray("SELECT description,qty,dttm,rec_dttm " +
							"FROM DIET D "              +
							"WHERE D.patientid=" + patientID + " " +
							"ORDER BY dttm desc");


					if(diet.next())
					{
						System.out.println("Diet observations\n");

						System.out.println(String.format("%-20s,%-20s,%-20s,%-20s", "Description", "Servings", "Observation datetime", "Record datetime"));
						do 
						{
							//Print header

							System.out.println(diet.getString("description") + "," + diet.getString("qty")+
									diet.getString("dttm") + "," + diet.getString("rec_dttm") + "\n");

						} while(diet.next()); 

					}




					//Get weight observations

					ResultSet weight = SqlTools.QueryMeThisArray("SELECT weightid, qty,dttm,rec_dttm "                  +
							"FROM WEIGHT W "                         +
							"WHERE W.patientid=" + patientID + " "  +
							"ORDER BY dttm desc");

					if(weight.next())
					{
						System.out.println("Weight observations\n");
						System.out.println("Qty (lb), Observation datetime, Record datetime");
						do
						{
							//Print header


							System.out.println(weight.getString("qty") + "," + 
									weight.getString("dttm") + "," + weight.getString("rec_dttm") + "\n");


						} while (weight.next());

					}




					//Get exercise observations

					ResultSet exercise = SqlTools.QueryMeThisArray("SELECT description,duration,dttm,rec_dttm "  +
							"FROM EXERCISE E "                       +
							"WHERE E.patientid=" + patientID + " "  +
							"ORDER BY dttm desc");

					if(exercise.next())
					{
						System.out.println("Exercise observations\n");
						System.out.println("Description, Duration, Observation datetime, Record datetime");
						do
						{

							System.out.println(exercise.getString("description") + "," + exercise.getString("duration") + 
									"," + exercise.getString("dttm") + "," + exercise.getString("rec_dttm") + "\n");
						}while(exercise.next());	
					}


					//Get blood pressure observations

					ResultSet bloodpressure = SqlTools.QueryMeThisArray("SELECT systolic,diastolic,dttm, rec_dttm "            +
							"FROM BLOODPRESSURE BP "                 +
							"WHERE BP.patientid=" + patientID + " "  +
							"ORDER BY dttm desc");

					if(bloodpressure.next())
					{

						System.out.println("Blood pressure observations\n");
						System.out.println("Systolic (mgHg), Diastolic (mgHg), Observation datetime, Record datetime");
						do
						{
							System.out.println(bloodpressure.getString("systolic") + "," + bloodpressure.getString("diastolic") + 
									"," + bloodpressure.getString("dttm") + "," + bloodpressure.getString("rec_dttm") + "\n");
						}while(bloodpressure.next());

					}

					//Get Exercise Tolerance observations

					ResultSet extol = SqlTools.QueryMeThisArray("SELECT steps, dttm, rec_dttm "                            +
							"FROM EXERCISETOLERANCE ET "                 +
							"WHERE ET.patientid=" + patientID + " "  +
							"ORDER BY dttm desc");

					if(extol.next())
					{

						System.out.println("Exercise tolerance observations\n");
						System.out.println("Steps, Observation datetime, Record datetime");
						do
						{
							System.out.println(extol.getString("steps") +  
									"," + extol.getString("dttm") + "," + extol.getString("rec_dttm") + "\n");
						}while(extol.next());


					}


					ResultSet oxsat = SqlTools.QueryMeThisArray("SELECT amount, dttm, rec_dttm "                            +
							"FROM OXSATURATION OX "                 +
							"WHERE OX.patientid=" + patientID + " "  +
							"ORDER BY dttm desc");


					if(oxsat.next())
					{
						System.out.println("Oxygen saturation observations\n");
						System.out.println("Amount, Observation datetime, Record datetime");
						//Get o2 sat observations
						do
						{

							System.out.println(oxsat.getString("amount") +  
									"," + oxsat.getString("dttm") + "," + oxsat.getString("rec_dttm") + "\n");
						} while(oxsat.next());

					}

					//Get Custom Observations list
					//Only get observation IDs above 10 (custom observations)

					ResultSet cust = SqlTools.QueryMeThisArray("SELECT OBSTYPE "                         +
							"FROM OBSERVATIONTYPES " +
							"WHERE obstypeid > 10");


					while(cust.next())
					{
						System.out.println("Custom observations\n");
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


	private String viewCustObservations() throws IOException {
		String userChoice = "";
		ResultSet custObsData, custPatData = null;

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Welcome to the View By Custom Observation Type screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
			System.out.println(" Custom Observations  ");
			try 
			{
				//Grab those custom types!
				custObsData = SqlTools.QueryMeThisArray("SELECT * " +
						"FROM CUSTOMOBSERVATIONTYPES");

				//Iterate through the custom types and print them out
				while (custObsData.next())
				{		
					System.out.println(Integer.toString(custObsData.getRow()) + ". " + custObsData.getString(3) );
				}

				System.out.println("Enter choice              ");
				System.out.println(" 0. Back                  ");

				userChoice = in.readLine();

				//Now print the patient information
				custPatData = SqlTools.QueryMeThisArray("SELECT * " +
						"FROM CUSTOMOBSERVATIONS C, PATIENT P " +
						"WHERE C.patientid=P.patientid AND OBSTYPEID=" + Integer.parseInt(userChoice));

				System.out.println("PatientID,Lastname,Firstname");
				while (custPatData.next())
				{
					System.out.println(custPatData.getString("PATIENTID") + "," + custPatData.getString("fname")  + "," + custPatData.getString("lname"));
				}
				return null;
			}
			catch(SQLException e)
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

	private void NewObservationType() {
		// TODO Auto-generated method stub

	}

	/*	
	private String viewCustObservations() throws IOException {
		String userChoice = "";
		ResultSet custObsData, custPatData = null;

		for (int x= 0; x< LOOP_LIMIT; x++){
			System.out.println("Welcome to the View By Custom Observation Type screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
			System.out.println(" Custom Observations  ");
			try 
			{
				//Grab those custom types!
				custObsData = SqlTools.QueryMeThisArray("SELECT * " +
						"FROM CUSTOMOBSERVATIONTYPES");

				//Iterate through the custom types and print them out
				while (custObsData.next())
				{		
					System.out.println(Integer.toString(custObsData.getRow()) + ". " + custObsData.getString(3) );
				}

				System.out.println("Enter choice              ");
				System.out.println(" 0. Back                  ");

				userChoice = in.readLine();

				//Now print the patient information
				custPatData = SqlTools.QueryMeThisArray("SELECT * " +
						"FROM CUSTOMOBSERVATIONS C, PATIENT P " +
						"WHERE C.patientid=P.patientid AND OBSTYPEID=" + Integer.parseInt(userChoice));

				System.out.println("PatientID,Lastname,Firstname");
				while (custPatData.next())
				{
					System.out.println(custPatData.getString("PATIENTID") + "," + custPatData.getString("fname")  + "," + custPatData.getString("lname"));
				}
				return null;
			}
			catch(SQLException e)
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

		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;


	}	
	 */
}

