package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;


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

	for (int x= 0; x< LOOP_LIMIT; x++){
		System.out.println("Welcome to the patient start screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
		System.out.println(" 1. Add Observation Type  ");
		System.out.println(" 2. Add Association       ");
		System.out.println(" 3. View Patient          ");
		System.out.println(" 4. Back                  ");
		System.out.println("Enter choice              ");
		userChoice = in.readLine();
		if (userChoice.equals("1")){
			this.AddObservationType();
		} else if (userChoice.equals("2")){
			this.AddAssociation();
		} else if (userChoice.equals("3")){
			this.viewPatientScreen();
		} else if (userChoice.equals("4")){
			return("6. Back");
		}
	}
	System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
	return LOOP_LIMIT_ERROR;

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

private String viewPatientByName() throws IOException {
	String lastName,firstName= "";
	ResultSet patInfo = null;
	int patientID = 0;

	System.out.println("Welcome to the patient view screen "+SqlTools.QueryMeThis("SELECT fname FROM HealthSupporter where supporterid = "+this.patientId));
	
	//Read lastname
	System.out.println(" Enter patient lastname  ");
	lastName = in.readLine();
	//Read firstame
	System.out.println(" Enter patient firstname  ");
	firstName = in.readLine();
	
	//Display patient information
	System.out.println("Demographic information");
	System.out.println("Firstname Lastname, DOB, Gender, Address");
	//patInfo = SqlTools.QueryMeThisArray("SELECT *"          +
		//	                            "FROM PATIENT P "   +
	      //                              "WHERE P.lname ='" + lastName + "' AND P.fname='" + firstName +"'");

	/*
	if (!patInfo.next())
	{
		//No rows, so no patients
		System.out.println("Patient does not exist!");
	}
	else
	{
		do	
		{
			System.out.println(patInfo.getString("fname"));
			
			//Display observation data
			System.out.println("Observation information");
			//Get the observations type
			ResultSet obsTypes = SqlTools.QueryMeThisArray("SELECT obsType FROM ObservationsMeta");
			
			//Loop through each observation table to see if this patient has any
			while(obsTypes.next())
			{
				//Print the observation type
				System.out.println(obsTypes.getString(1));
				
				//Get observation data for this patient
				//SqlTools.QueryMeThisArray("SELECT obsType FROM " + obsTypes.getString(1)");
				
			}
			
		} while (patInfo.next());
		
	}
		
	
	//Set our counters
	int ii=0;
	int iii=0;
	
	//Display all observation types
	for(String str : stringList) 
	{
		
		System.out.println(ii + ". " + str);
		ii++;
		
	}
	//TODO: Sanitize the input
	userChoice = in.readLine();
	
	////
	patInfo = SqlTools.QueryMeThis("SELECT fname || ' ' || lname || ',' || DOB || ',' || Gender || ',' ||" +
                                           "street || ',' || city || ',' || state || ',' || zip "           +
      "FROM PATIENT P "                                                      +
      "WHERE P.lname ='" + lastName + "' AND P.fname='" + firstName +"'");
	
	
	*/
	return null;

	
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
		finally 
		{
			in.close();		
		}
		
		

			
	}
	
	System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
	return LOOP_LIMIT_ERROR;

	
}
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

private void AddAssociation() {
	// TODO Auto-generated method stub
	
}

private void AddObservationType() {
	// TODO Auto-generated method stub
	
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

private void ManageHealthFriends() {
	// TODO Auto-generated method stub

}

private void ViewMyAlerts() {
	// TODO Auto-generated method stub

}

private void NewObservationType() {
	// TODO Auto-generated method stub

}

private void ViewObservations() {
	// TODO Auto-generated method stub

}

private String EnterObservations() throws IOException {
	// TODO Auto-generated method stub

	String userChoice = "";

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

private void OtherObservation() {
	// TODO Auto-generated method stub

}

private void MoodObservation() {
	// TODO Auto-generated method stub

}

private void ExerciseObservation() {
	// TODO Auto-generated method stub

}

private void WeightObservation() {
	// TODO Auto-generated method stub

}

private String DietObservation() throws IOException {
	// TODO Auto-generated method stub
	String description = "";
	String amount = "";
	String dateTime = "";
	for (int x= 0; x< LOOP_LIMIT; x++){
		System.out.println("  Enter:                                                                ");
		System.out.print("What food or drink was consumed");
		description = in.readLine();
		System.out.print("Amount in servings:");
		amount = in.readLine();
		System.out.print("Date time of observation (YYYYMMDD HH:MI):");
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

}