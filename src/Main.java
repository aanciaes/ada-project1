import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
			
		readLines(reader);
	}
	
	public static void readLines (BufferedReader reader) throws IOException{
				
		String [] string_array = readLine(reader, "//s");
		int num_tests = Integer.parseInt(string_array[0]);
		
		for (int i=0;i<num_tests;i++){
			String [] problem = readLine(reader, "");
				
			BigOldDesert bod = new BigOldDesert(problem);
			
			//System.out.println((int)bod.solveRecursively(0,0));
			
			System.out.println(bod.resolveIterative());
		}		
	}
	
	public static String [] readLine (BufferedReader reader, String regex) throws IOException {
		String line = reader.readLine();
		String [] string_array = line.split(regex);
		return string_array;
	}
}
