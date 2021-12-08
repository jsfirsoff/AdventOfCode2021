import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7 {

	private static int findMostEfficient(String fileLocation, int flag) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter(",");
		
		ArrayList<Integer> hPositions = new ArrayList<Integer>();
		
		while (scn.hasNextInt()) {
			hPositions.add(scn.nextInt());
		}
		scn.close();
		
		if (flag == 0) return mostEfficientPosition(hPositions);
		
		return mostEfficientPositionWithExpensiveFuel(hPositions);
	}
	
	private static int mostEfficientPosition(ArrayList<Integer> hPositions) {
		int max = maxPosition(hPositions);
		
		int leastFuel = 0;

		for (Integer pos : hPositions) {
			leastFuel += pos;
		}
		
		for (int i = 1; i <= max; i++) {
			int totalFuel = 0;
			for (Integer pos : hPositions) {
				totalFuel += Math.abs(pos - i);
				if (totalFuel > leastFuel) break;
			}
			leastFuel = Math.min(leastFuel, totalFuel);
		}
		return leastFuel;
	}
	
	private static int maxPosition(ArrayList<Integer> hPositions) {
		int max = 0;
		
		for (Integer pos : hPositions) {
			max = Math.max(max, pos);
		}
		return max;
	}
	
	//part 2
	
	private static int mostEfficientPositionWithExpensiveFuel(ArrayList<Integer> hPositions) {
		int max = maxPosition(hPositions);	
		int leastFuel = 0;

		for (Integer pos : hPositions) {
			leastFuel += calcFuel(pos);
		}
		
		for (int i = 1; i <= max; i++) {
			int totalFuel = 0;
			for (Integer pos : hPositions) {
				totalFuel += calcFuel(Math.abs(pos - i));
				if (totalFuel > leastFuel) break;
			}
			leastFuel = Math.min(leastFuel, totalFuel);
		}
		return leastFuel;
	}
	
	private static int calcFuel(int steps) {
		int fuel = ((steps) * (steps+1))/2;
		
		return fuel;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(findMostEfficient("src/input/problem7.txt", 0));
		System.out.println(findMostEfficient("src/input/problem7.txt", 1));
	}
}