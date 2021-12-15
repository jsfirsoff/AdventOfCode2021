import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Day10 {
	
	private static long errorScoring(String fileLocation, int flag) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter("\\n");
		
		ArrayList<String[]> systemChunks = new ArrayList<String[]>(); 

		while (scn.hasNextLine()) {
			String[] line = scn.nextLine().split("");
			systemChunks.add(line);
		}
		scn.close();
		
		if (flag == 0) return calcScore(findCorrupted(systemChunks));
		
		return calcAutoCompleteScore(autoComplete(systemChunks));
	}
	
	private static ArrayList<String> findCorrupted(ArrayList<String[]> systemChunks) {
		ArrayList<String> corrupted = new ArrayList<String>();
		Stack<String> syntax = new Stack<String>();
		
		for (String[] line : systemChunks) {
			int len = line.length;
			for (int i = 0; i < len; i++) {
				String curr = line[i];
				if (isOpener(curr)) syntax.push(curr);
				else {
					String previous = syntax.pop();
					if (!isAppropriateCloser(previous, curr)) {
						corrupted.add(curr);
						break;
					}
				}
			}
		}
		return corrupted;
	}
	
	private static boolean isOpener(String syntax) {
		if (syntax.matches("\\<|\\[|\\{|\\(")) return true;
		return false;
	}
	
	private static int calcScore(ArrayList<String> corrupted) {
		int score = 0;
		
		for (String syntax : corrupted) {
			switch (syntax) {
				case ">": score += 25137; break;
				case "}": score += 1197; break;
				case "]": score += 57; break;
				case ")": score += 3; break;
			}
		}	
		return score;
	}
	
	private static boolean isAppropriateCloser(String opener, String closer) {
		switch (opener) {
			case "<": return closer.equals(">");
			case "{": return closer.equals("}");
			case "[": return closer.equals("]");
			case "(": return closer.equals(")");
		}
		return false;
	}
	
	//part 2
	
	private static ArrayList<String[]> autoComplete(ArrayList<String[]> systemChunks) {
		ArrayList<String[]> completed = new ArrayList<String[]>();
		
		for (String[] line : systemChunks) {
			Stack<String> syntax = new Stack<String>();
			int len = line.length;
			int i;
			for (i = 0; i < len; i++) {
				String curr = line[i];
				if (isOpener(curr)) syntax.push(curr);
				else {
					String previous = syntax.pop();
					if (!isAppropriateCloser(previous, curr)) break;
				}
			}
			if (i < len) continue;
			
			completed.add(complete(syntax));	
		}
		return completed;
	}
	
	private static long calcAutoCompleteScore(ArrayList<String[]> completion) {
		int num = completion.size();
		long[] scores = new long[num];
		int index = 0;
		
		for (String[] line : completion) {
			long score = 0;
			int len = line.length;
			for (int i = 0; i < len; i++) {
				score *= 5;
				switch (line[i]) {
					case ">": score += 4; break;
					case "}": score += 3; break;
					case "]": score += 2; break;
					case ")": score += 1; break;
				}
			}	
			scores[index++] = score;
		}	
		Arrays.sort(scores);
		
		return scores[num/2];
	}
	
	private static String[] complete(Stack<String> syntax) {
		int size = syntax.size();
		String[] remaining = new String[size];
		
		for (int i = 0; i < size; i++) {
			remaining[i] = getCloser(syntax.pop());
		}
		return remaining;
	}
	
	private static String getCloser(String opener) {
		switch (opener) {
			case "<": return ">";
			case "{": return "}";
			case "[": return "]";
			case "(": return ")";
	}
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(errorScoring("src/input/problem10.txt", 0));
		System.out.println(errorScoring("src/input/problem10.txt", 1));
	}
}
