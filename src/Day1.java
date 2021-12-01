import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 {
	
	private static int countIncreases(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		
		int count = 0;
		int curr = scn.nextInt();
		while (scn.hasNextLine()) {
			
			int next = scn.nextInt();
			
			if (next > curr) {
				count++;
			}
			
			curr = next;
		}
		scn.close();
		
		return count;
	}
	
	private static int SWCountIncreases(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		
		int count = 0;
		int depth1 = scn.nextInt();
		int depth2 = scn.nextInt();
		int depth3 = scn.nextInt();
		while (scn.hasNextLine()) {
			
			int next1 = depth2;
			int next2 = depth3;
			
			int next3 = scn.nextInt();
			
			int windowSum1 = depth1 + depth2 + depth3;
			int windowSum2 = next1 + next2 + next3;
			
			if (windowSum2 > windowSum1) {
				count++;
			}
			
			depth1 = next1;
			depth2 = next2;
			depth3 = next3;
		}
		scn.close();
		
		return count;
	}

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println("Increases: " + countIncreases("src/input/problem1.txt"));
		
		System.out.println("Sliding Window Increases: " + SWCountIncreases("src/input/problem1.txt"));

	}

}