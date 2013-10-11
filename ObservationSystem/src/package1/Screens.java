package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Screens {
	static int LOOP_LIMIT = 5;
	static String LOOP_LIMIT_ERROR= "Looplimitout";
	BufferedReader in;

	public static void main(String args[]){

		System.out.println("helloworld");
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
			System.out.println("  Enter uid followed by password                                                                ");
			System.out.print("username:");
			username = in.readLine();
			System.out.print("password:");
			password = in.readLine();
			Boolean loginValid= false;
			loginValid = SqlTools.LoginValid(username,password);
			if (loginValid){
				System.out.println("login Valid!");
				return("1. Login was Valid");
			}
			else {
				System.out.println("login Not Valid, please try again.");
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in login screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
		}

	public String StartScreen() throws IOException{
		String userinput = "";
		this.in = new BufferedReader(new InputStreamReader(System.in)); 
		try {
			for (int x= 0; x< LOOP_LIMIT; x++){
				System.out.println("  1. Login                                                                 ");
				System.out.println("  2. Create User                                                           ");
				System.out.println("  3. Exit                                                                  ");
				System.out.println("  Enter choice                                                             ");
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
			System.out.println("  Page under Construction                                                                ");
			System.out.println("  1. Previous Screen                                                                 ");
			userinput = in.readLine();
			if (userinput.equals("1")){
				return("1. Previous Screen");
			}
		}
		System.out.println("reached Looplimit "+ LOOP_LIMIT + " in create user screen, going to previous screen");
		return LOOP_LIMIT_ERROR;
	}
}