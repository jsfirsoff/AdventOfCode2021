import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day3 {
	
	private static int calcPowerConsumption(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		
		int[] gammaRate = new int[12];
		int[] epsilonRate = new int[12];
		int[] bitCount = new int[12];
		int total = 0;
		
		while (scn.hasNextLine()) {
			char[] biNum = scn.nextLine().toCharArray();
			
			for (int i = 0; i < 12; i++) {
				if (biNum[i] == '1') bitCount[i]++;
			}	
			total++;
		}
		
		for (int j = 0; j < 12; j++) {
			int oneBits = bitCount[j];
			int zeroBits = total - oneBits;
			if (oneBits > zeroBits) {
				gammaRate[j] = 1;
				epsilonRate[j] = 0;
			} 
			else {
				gammaRate[j] = 0;
				epsilonRate[j] = 1;
			}
		}
		
		scn.close();
		
		int gamma = Integer.valueOf(Arrays.toString(gammaRate).replaceAll("\\[|\\]|,|\\s", ""), 2);
		int epsilon = Integer.valueOf(Arrays.toString(epsilonRate).replaceAll("\\[|\\]|,|\\s", ""), 2);

		return gamma * epsilon;
	}
	
	private static int calcLifeSupport(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		
		ArrayList<char[]> nums = new ArrayList<char[]>();
		
		while (scn.hasNextLine()) {
			char[] biNum = scn.nextLine().toCharArray();
			
			nums.add(biNum);
		}
		scn.close();
		
		char[] oxygenGenRating = calcRatings(nums, 1, 0);
		char[] co2ScrRating = calcRatings(nums, 0, 0);
		
		int oxygen = Integer.valueOf(Arrays.toString(oxygenGenRating).replaceAll("\\[|\\]|,|\\s", ""), 2);
		int co2 = Integer.valueOf(Arrays.toString(co2ScrRating).replaceAll("\\[|\\]|,|\\s", ""), 2);

		return oxygen * co2;
	}
	
	private static char[] calcRatings(ArrayList<char[]> nums, int ratingBit, int pos) {
		int len = nums.size();
		
		if (len == 1) return nums.get(0);
		
		ArrayList<char[]> ones = new ArrayList<char[]>();
		ArrayList<char[]> zeros = new ArrayList<char[]>();

		for (char[] num : nums) {
			if (num[pos] == '1') {
				ones.add(num); 
			}
			else {
				zeros.add(num);
			}
		}
		
		int mostCommon = Integer.compare(ones.size(), zeros.size());
		
		if (ratingBit == 1) {
			if (mostCommon >= 0) {
				return calcRatings(ones, ratingBit, ++pos);
			}
			else {
				return calcRatings(zeros, ratingBit, ++pos);
			}
		}
		else {
			if (mostCommon >= 0) {
				return calcRatings(zeros, ratingBit, ++pos);
			}
			else {
				return calcRatings(ones, ratingBit, ++pos);
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		System.out.println(calcPowerConsumption("src/input/problem3.txt"));
		System.out.println(calcLifeSupport("src/input/problem3.txt"));
	}
}
