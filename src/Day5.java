import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Day5 {
	
	private static int overlappingLines(String fileLocation, int diagFlag) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter("[,|\\-|>|\\s|\\n]+");
		
		HashMap<Integer, PriorityQueue<Integer>> points = new HashMap<Integer, PriorityQueue<Integer>>();

		while (scn.hasNextLine()) {
			int x1 = scn.nextInt();
			int y1 = scn.nextInt();
			int x2 = scn.nextInt();
			int y2 = scn.nextInt();
			
			if (x1 != x2 && y1 != y2 && diagFlag == 0) continue;
			
			addToMap(points, x1, y1);
			addToMap(points, x2, y2);
			
			int lowerX = Math.min(x1, x2);
			int higherX = Math.max(x1, x2);
			int lowerY = Math.min(y1, y2);
			int higherY = Math.max(y1, y2);

			if (x1 == x2) {
				for (int i = lowerY + 1; i < higherY; i++) {
					addToMap(points, x1, i);
				}
			}
			else if (y1 == y2) {
				for (int i = lowerX + 1; i < higherX; i++) {
					addToMap(points, i, y1);
				}
			}
			else if (x1 < x2){
				if (y1 < y2) {
					for (int i = lowerX + 1; i < higherX; i++) {
						addToMap(points, i, ++lowerY);
					}
				}
				else {
					for (int i = lowerX + 1; i < higherX; i++) {
						addToMap(points, i, --higherY);
					}
				}
			}
			else {
				if (y1 < y2) {
					for (int i = higherX - 1; i > lowerX; i--) {
						addToMap(points, i, ++lowerY);
					}
				}
				else {
					for (int i = higherX - 1; i > lowerX; i--) {
						addToMap(points, i, --higherY);
					}
				}
			}
		}
		scn.close();
		
		int numPoints = calcOverlapping(points);
		
		return numPoints;
	}
	
	private static void addToMap(HashMap<Integer, PriorityQueue<Integer>> map, int x, int y) {
		if (map.containsKey(x)) {
			map.get(x).add(y);
		}
		else {
			map.put(x, new PriorityQueue<Integer>());
			map.get(x).add(y);
		}
	}
	
	private static int calcOverlapping(HashMap<Integer, PriorityQueue<Integer>> points) {
		int overlapping = 0;
			
		for (Map.Entry<Integer, PriorityQueue<Integer>> entry : points.entrySet()) {
			PriorityQueue<Integer> yValues = entry.getValue();
			
			Integer curr = yValues.poll();
			Integer next = null;
			boolean hasOccured = false;
			
			while (!yValues.isEmpty()) { 
				next = yValues.poll();
				
				if (curr.equals(next) && !hasOccured) {
					hasOccured = true;
					overlapping++;
				}
				else if (!curr.equals(next) && hasOccured) {
					hasOccured = false;
				}	
				curr = next;
			}
		}
		return overlapping;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(overlappingLines("src/input/problem5.txt", 0));
		System.out.println("With Diagonals: " + overlappingLines("src/input/problem5.txt", 1));
	}
}
