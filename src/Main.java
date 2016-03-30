import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
			
		readLines(reader);
	}
	
	public static void readLines (BufferedReader reader) throws IOException{
		
		String [] string_array = readLine(reader);
		int num_tests = Integer.parseInt(string_array[0]);
		
		for (int i=0;i<num_tests;i++){
			String [] problem = readLine(reader);
				
			BigOldDesert bod = new BigOldDesert(problem);
			
			System.out.println(bod.solve());
		}		
	}
	
	public static String [] readLine (BufferedReader reader) throws IOException {
		String line = reader.readLine();
		String [] string_array = line.split("");
		return string_array;
	}
}
