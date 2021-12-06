import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day4 {
	
	private static int playBingoToWin(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		
		ArrayList<String[][]> cards = new ArrayList<String[][]>();
		
		String[][] bingoCard = new String[5][5];
		
		String[] nums = scn.nextLine().split(",");
		 
		int row = 0;
		scn.nextLine();
		while (scn.hasNextLine()) {
			String line = scn.nextLine().strip();
			String[] lineSplit = line.split("\\s+");
			
			if (line.isEmpty()) {
				cards.add(bingoCard);
				bingoCard = new String[5][5];
				row = 0;
				continue;
			} 
			
			for (int col = 0; col < 5; col++) {
				bingoCard[row][col] = lineSplit[col];
			}
			row++;	
		}
		cards.add(bingoCard);
		
		scn.close();
		
		return playToWin(cards, nums);
	}
	
	private static int playToWin(ArrayList<String[][]> cards, String[] nums) {
		for (String num : nums) {
			for (String[][] card : cards) {
				int result = callNumber(card, num);
				if (result > -1) return result; 
			}
		}
		return -1;
	}
	
	private static int callNumber(String[][] card, String num) {

		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				if (card[row][col].equals(num)) {
					card[row][col] = "x";
					if (isAWinner(card)) {
						return calcScore(card, num);
					}
				}
			}
		}
		return -1;
	}
	
	private static boolean isAWinner(String[][] card) {
		int count = 0;
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				if (card[row][col] != "x") break;
				
				count++;
				
				if (count == 5) return true;
			}
			count = 0;
		}	
		count = 0;
		
		for (int col = 0; col < 5; col++) {
			for (int row = 0; row < 5; row++) {
				if (card[row][col] != "x") break;
				
				count++;
				
				if (count == 5) return true;
			}
			count = 0;
		}
		return false;	
	}
	
	private static int calcScore(String[][] card, String num) {
		
		int unmarked = 0;
		
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				if (card[row][col] == "x") continue;
				
				unmarked = unmarked + Integer.parseInt(card[row][col]);
			}
		}
		return unmarked * Integer.parseInt(num);
	}
	
	// part 2
	
	private static int playBingoToLose(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		
		ArrayList<String[][]> cards = new ArrayList<String[][]>();
		
		String[][] bingoCard = new String[5][5];
		
		String[] nums = scn.nextLine().split(",");
		 
		int row = 0;
		scn.nextLine();
		while (scn.hasNextLine()) {
			String line = scn.nextLine().strip();
			String[] lineSplit = line.split("\\s+");
			
			if (line.isEmpty()) {
				cards.add(bingoCard);
				bingoCard = new String[5][5];
				row = 0;
				continue;
			} 
			
			for (int col = 0; col < 5; col++) {
				bingoCard[row][col] = lineSplit[col];
			}
			row++;	
		}
		cards.add(bingoCard);
		
		scn.close();
		
		return playToLose(cards, nums);
	}
	
	private static int playToLose(ArrayList<String[][]> cards, String[] nums) {
	
		int numWins = 0;
		int lastCard = cards.size();
		
		for (String num : nums) {
			for (String[][] card : new ArrayList<String[][]>(cards)) {
				boolean winner = callNumberToLose(card, num); 
				
				if (winner) {
					++numWins;
					cards.remove(card);
					if (numWins == lastCard) return calcScore(card, num);
				}
			}
		}
		return -1;
	}
	
	private static boolean callNumberToLose(String[][] card, String num) {

		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				if (card[row][col].equals(num)) {
					card[row][col] = "x";
					if (isAWinner(card)) return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(playBingoToWin("src/input/problem4.txt"));
		System.out.println(playBingoToLose("src/input/problem4.txt"));
	}

}
