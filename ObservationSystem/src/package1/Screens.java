package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Screens {

	public static void main(String args[]){
		String userinput = "";
		System.out.println("helloworld");
		userinput = StartScreen();
		System.out.println("inputed "+ userinput);

	}


	public static String StartScreen(){
		String userinput = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		try {
			System.out.println("  1. Login                                                                 ");
			System.out.println("  2. Create User                                                           ");
			System.out.println("  3. Exit                                                                  ");
			System.out.println("  Enter choice                                                             ");
			//		System.out.println("                                                                           ");
			userinput = in.readLine();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userinput;
	}
}