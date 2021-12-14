import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Day9 {
	
	private static boolean[][] visited;

	private static int riskLevels(String fileLocation, int flag) throws FileNotFoundException {
		File file = new File(fileLocation);
		
		Scanner scn = new Scanner(file);
		scn.useDelimiter("\\n");
		
		ArrayList<String[]> values = new ArrayList<String[]>(); 

		while (scn.hasNextLine()) {
			String[] arr = scn.nextLine().split("");
			values.add(arr);
			
		}
		scn.close();
		
		int len = values.size();
		int width = values.get(0).length;
		
		int[][] locations = new int[len][width];
		visited = new boolean[len][width];
		
		int row = 0;
		for (String[] val : values) {
			for (int col = 0; col < width; col++) {
				locations[row][col] = Integer.parseInt(val[col]);
			}
			row++;
		}
		
		if (flag == 0) return calcLowPoints(locations);
		
		return calcHighestBasins(locations);
	}
	
	private static int calcLowPoints(int[][] locations) {
		int rows = locations.length;
		int cols = locations[0].length;
		int riskLevelTotal = 0;
		
		for (int i = 1; i < rows-1; i++) {
			//left side
			riskLevelTotal += calcRiskLevel(locations[i][0], locations[i+1][0], 
					locations[i-1][0], 100, locations[i][1]);
			//right side
			riskLevelTotal += calcRiskLevel(locations[i][cols-1], locations[i+1][cols-1], 
					locations[i-1][cols-1], locations[i][cols-2], 100);	
			//middle
			for (int j = 1; j < cols-1; j++) {	
				riskLevelTotal += calcRiskLevel(locations[i][j], locations[i+1][j], 
						locations[i-1][j], locations[i][j-1], locations[i][j+1]);
			}
		}	
		//top & bottom	
		for (int i = 1; i < cols-1; i++) {
			//top
			riskLevelTotal += calcRiskLevel(locations[0][i], 100, 
					locations[1][i], locations[0][i-1], locations[0][i+1]);
			//bottom
			riskLevelTotal += calcRiskLevel(locations[rows-1][i], locations[rows-2][i], 
					100, locations[rows-1][i-1], locations[rows-1][i+1]);	
		}	
		//corners
		riskLevelTotal += calcRiskLevel(locations[0][0], 100, 
				locations[1][0], 100, locations[0][1]);
		riskLevelTotal += calcRiskLevel(locations[0][cols-1], 100, 
				locations[1][cols-1], locations[0][cols-2], 100);
		riskLevelTotal += calcRiskLevel(locations[rows-1][0], locations[rows-2][0], 
				100, 100, locations[rows-1][1]);
		riskLevelTotal += calcRiskLevel(locations[rows-1][cols-1], locations[rows-2][cols-1], 
				100, locations[rows-1][cols-2], 100);
		
		return riskLevelTotal;
	}
	
	private static int calcRiskLevel(int point, int above, int below, int left, int right) {
		int total = 0;
		int[] points = new int[] {above, below, left, right};
		
		for (int k = 0; k < 4; k++) {
			if (point >= points[k]) break;
			if (k == 3) total = point + 1;
		}
		return total;
	}
	
	//part 2
	
	private static int calcHighestBasins(int[][] locations) {
		int rows = locations.length;
		int cols = locations[0].length;
		PriorityQueue<Integer> basins = new PriorityQueue<Integer>(Collections.reverseOrder());
		
		for (int i = 1; i < rows-1; i++) {
			//left side
			basins.add(calcBasin(locations[i][0], locations[i+1][0], 
					locations[i-1][0], 100, locations[i][1], locations, i, 0));
			//right side
			basins.add(calcBasin(locations[i][cols-1], locations[i+1][cols-1], 
					locations[i-1][cols-1], locations[i][cols-2], 100, locations, i, cols-1));	
			//middle
			for (int j = 1; j < cols-1; j++) {	
				basins.add(calcBasin(locations[i][j], locations[i+1][j], 
						locations[i-1][j], locations[i][j-1], locations[i][j+1], locations, i, j));
			}
		}	
		//top & bottom	
		for (int i = 1; i < cols-1; i++) {
			//top
			basins.add(calcBasin(locations[0][i], 100, 
					locations[1][i], locations[0][i-1], locations[0][i+1], locations, 0, i));
			//bottom
			basins.add(calcBasin(locations[rows-1][i], locations[rows-2][i], 
					100, locations[rows-1][i-1], locations[rows-1][i+1], locations, rows-1, i));	
		}	
		//corners
		basins.add(calcBasin(locations[0][0], 100, 
				locations[1][0], 100, locations[0][1], locations, 0, 0));
		basins.add(calcBasin(locations[0][cols-1], 100, 
				locations[1][cols-1], locations[0][cols-2], 100, locations, 1, cols-1));
		basins.add(calcBasin(locations[rows-1][0], locations[rows-2][0], 
				100, 100, locations[rows-1][1], locations, rows-1, 0));
		basins.add(calcBasin(locations[rows-1][cols-1], locations[rows-2][cols-1], 
				100, locations[rows-1][cols-2], 100, locations, rows-1, cols-1));
		
		return basins.poll() * basins.poll() * basins.poll();
	}
	
	private static int calcBasin(int point, int above, int below, int left, int right, int[][] locations, int row, int col) {
		int total = 0;
		int[] points = new int[] {above, below, left, right};
		
		for (int k = 0; k < 4; k++) {
			if (point >= points[k]) break;
			if (k == 3) {
				visited = new boolean[locations.length][locations[0].length];
				total = findBasin(locations, row, col, 0);
				}
		}
		return total;
	}

	private static int findBasin(int[][] locations, int row, int col, int total) { 
		int rows = locations.length;
		int cols = locations[0].length;
		
		if (row < 0 || col < 0 || row >= rows || col >= cols || locations[row][col] == 9 ||
				visited[row][col]) return 0;

		total++;
		visited[row][col] = true; 
		
		total += findBasin(locations, row, col-1, 0);
		total += findBasin(locations, row, col+1, 0);
		total += findBasin(locations, row-1, col, 0);
		total += findBasin(locations, row+1, col, 0); 
		
		return total;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(riskLevels("src/input/problem9.txt", 0));
		System.out.println(riskLevels("src/input/problem9.txt", 1));
	}
}
