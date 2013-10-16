package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
					System.out.println("login/password combination not found. please try again");
				}
				else if (loginValid.equals("patient")){
					this.patientId=Integer.parseInt(SqlTools.QueryMeThis("select patientid from patient where username = '"+username+"'"));
					patientStartScreen();
					return("1. Login was Valid");
				}
				else if (loginValid.equals("healthsupporter")){
					healthSupporterStartScreen();
					return("1. Login was Valid");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
	return LOOP_LIMIT_ERROR;
}

private void healthSupporterStartScreen() {
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
		System.out.println("Welcome to the patient start screen, "+SqlTools.QueryMeThis("select fname from patient where patientid = "+this.patientId));
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

private void ExerciseObservation() {
	// TODO Auto-generated method stub
	
}

private void OtherObservation() {
	// TODO Auto-generated method stub

}

private void MoodObservation() {
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

}