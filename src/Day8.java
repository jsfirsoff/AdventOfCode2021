import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {

	private static int descramble(String fileLocation, int flag) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter("[\\s|\\|\\n]+");
		
		HashMap<ArrayList<String>, ArrayList<String>> signalMap = new HashMap<ArrayList<String>, ArrayList<String>>();

		while (scn.hasNextLine()) {
			ArrayList<String> signals = new ArrayList<String>(); 
			ArrayList<String> output = new ArrayList<String>();
			
			for (int i = 0; i < 10; i++) {
				signals.add(scn.next());
			}	
			for (int j = 0; j < 4; j++) {
				output.add(scn.next());
			}		
			signalMap.put(signals, output);
		}
		scn.close();
		
		if (flag == 0) return countEasyDigits(signalMap);
		
		return decode(signalMap);
	}

	private static int countEasyDigits(HashMap<ArrayList<String>, ArrayList<String>> signalMap) {
		int count = 0;
		
		for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : signalMap.entrySet()) {
			ArrayList<String> output = entry.getValue();
			
			for (String s : output) {
				int len = s.length();
				if (len < 5 || len == 7) count++;
			}
		}
		return count;
	}
	
	//part 2
	
	private static int decode(HashMap<ArrayList<String>, ArrayList<String>> signalMap) {
		int total = 0;
		
		for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : signalMap.entrySet()) {
			HashMap<String, String> key = createKey(entry.getKey());
			ArrayList<String> output = entry.getValue();
			String num = "";
			for (String s : output) {
				Pattern p = Pattern.compile("\\b[" + s + "]{" + s.length() + "}\\b");
				Matcher m = p.matcher(entry.getKey().toString());
				
				m.find();

				num = num.concat(key.get(m.group()));	
			}
			total += Integer.parseInt(num);
		}		
		return total;
	}

	private static HashMap<String, String> createKey(ArrayList<String> signals) {
		HashMap<String, String> key = new HashMap<String, String>();

		String signal1 = "";
		String signal8 = "";
		String signal4 = "";

		for (String s : signals) {
			int len = s.length();
			if (len == 2) { 
				key.put(s, "1");
				signal1 = s;
			}
			if (len == 7) {
				key.put(s, "8");
				signal8 = s;
			}
			if (len == 3) key.put(s, "7");
			if (len == 4) {
				key.put(s, "4");
				signal4 = s;
			}
		}

		for (String s : signals) {
			int len = s.length();

			if (len == 5) {
				Pattern p = Pattern.compile("[" + signal1 + "]");
				Matcher m = p.matcher(s);
				
				int matches = 0;			
				
				while (m.find()) matches++;
	
				if (matches == 2) key.put(s, "3");
				else {
					matches = 0;
					m = p.matcher(signal4);
		
					String letters = m.replaceAll("");
					
					p = Pattern.compile("[" + letters + "]");
					m = p.matcher(s);
		
					while (m.find()) matches++;
					
					if (matches == 2) key.put(s, "5");
					else key.put(s, "2");	
				}
			}	
			
			if (len == 6) {
				Pattern p = Pattern.compile("[" + s + "]");
				Matcher m = p.matcher(signal8);
	
				String letter = m.replaceAll("");
				
				if (signal1.contains(letter)) {
					key.put(s, "6"); 
				}
				else if (signal4.contains(letter)) key.put(s, "0");
				else key.put(s, "9");
			}
		}
		return key;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(descramble("src/input/problem8.txt", 0));
		System.out.println(descramble("src/input/problem8.txt", 1));
	}
}