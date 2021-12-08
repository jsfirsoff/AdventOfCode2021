import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Day6 {

	private static int simulateLanternfish(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter(",");
		
		ArrayList<Integer> fish = new ArrayList<Integer>();
		
		while (scn.hasNextInt()) {
			fish.add(scn.nextInt());
		}
		scn.close();

		simulateFish(fish, 80);
		
		return fish.size();
	}
	
	private static ArrayList<Integer> simulateFish(ArrayList<Integer> fish, int days) {
		if (days == 0) return fish;
		int newFish = 0;
		int index = 0;
		
		for (Integer timer : fish) {
			if (timer == 0) {
				fish.set(index, 6);
				newFish++;
			}
			else {
				fish.set(index, --timer);
			}
			index++;
		}
		
		for (int i = 0; i < newFish; i++) {
			fish.add(8);
		}
		 
		return simulateFish(fish, --days);
	}
	
	//part 2
	
	private static BigInteger simulateInfiniteLanternfish(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter(",");
		
		ArrayList<Integer> fish = new ArrayList<Integer>();
		
		while (scn.hasNextInt()) {
			fish.add(scn.nextInt());
		}
		scn.close();

		BigInteger[] totalFish = simulateInfiniteFish(fish, 256);
		
		return tallyFish(totalFish);
	}
	
	private static BigInteger[] simulateInfiniteFish(ArrayList<Integer> fish, int days) {
		BigInteger[] lifeStages = new BigInteger[9];
		
		for (int i = 0; i < 9; i++) {
			lifeStages[i] = BigInteger.ZERO;
		}
		
		for (Integer timer : fish) {
			lifeStages[timer] = lifeStages[timer].add(BigInteger.ONE);
		}
		
		BigInteger curr = BigInteger.ZERO;
		BigInteger next = BigInteger.ZERO;
		
		for (int i = 0; i < days; i++) {
			curr = lifeStages[8];
			for (int j = 7; j >= 0; j--) {
				next = lifeStages[j];
				
				lifeStages[j] = curr;
	
				curr = next;
				
				if (j == 0) {
					lifeStages[6] = lifeStages[6].add(curr);
					lifeStages[8] = curr;
				}
			}
		}
		return lifeStages;
	}
	
	private static BigInteger tallyFish(BigInteger[] fish) {
		BigInteger total = BigInteger.ZERO;
		
		for (int i = 0; i < 9; i++) {
			total = total.add(fish[i]);
		}
		return total;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(simulateLanternfish("src/input/problem6.txt"));
		System.out.println(simulateInfiniteLanternfish("src/input/problem6.txt"));
	}
}
