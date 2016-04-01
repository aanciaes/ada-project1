import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
			
		readLines(reader);
	}
	
	public static void readLines (BufferedReader reader) throws IOException{
				
		int num_tests=Integer.parseInt(reader.readLine());
		int [] results = new int [num_tests];
		
		for (int i=0;i<num_tests;i++){
			char [] problem = reader.readLine().toCharArray();
			BigOldDesert bod = new BigOldDesert(problem);
			
			//System.out.println((int)bod.solveRecursively(0,0));
			
			results[i]=bod.resolveIterative();
		}
		for(int i=0;i<num_tests;i++)
			System.out.println(results[i]);
	}
}