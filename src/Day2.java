import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {

	private static int trackPath(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter(" |\\n");
		
		String direction = "";
		int amount = 0;
		int hPos = 0;
		int depth = 0;
		
		while (scn.hasNextLine()) {
			
			direction = scn.next();
			amount = Integer.valueOf(scn.next().strip());

			switch (direction) {
			
				case "forward":
					hPos = hPos + amount;
					break;
					
				case "down":
					depth = depth + amount;
					break;
					
				case "up":
					depth = depth - amount;
					break;
			}
		}
		scn.close();
		
		return hPos * depth;
	}
	
	private static int trackPathByAim(String fileLocation) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter(" |\\n");
		
		String direction = "";
		int amount = 0;
		int hPos = 0;
		int depth = 0;
		int aim = 0;
		
		while (scn.hasNextLine()) {
			
			direction = scn.next();
			amount = Integer.valueOf(scn.next().strip());

			switch (direction) {
			
				case "forward":
					hPos = hPos + amount;
					depth = depth + (aim * amount);
					break;
					
				case "down":
					aim = aim + amount;
					break;
					
				case "up":
					aim = aim - amount;
					break;
			}
		}
		scn.close();
		
		return hPos * depth;
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Position: " + trackPath("src/input/problem2.txt"));
		System.out.println("Position With Aim: " + trackPathByAim("src/input/problem2.txt"));

	}

}
