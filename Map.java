import java.util.*;
import java.util.Scanner;
import java.awt.Point;
import java.io.*;
import java.io.FileNotFoundException;

public class Map {
	/* Instance Variables */
	private char[][] map = new char[5][5];
	private boolean[][] revealed = new boolean[5][5]; // Default is false
	static Map instance = null;

	/* Constructor */ 
	private Map() { // assume the map is 5 x 5
		loadMap(1);
	}  

  public static Map getInstance(){
		if( instance == null ) {
         instance = new Map();
      }
      return instance;
  }
  
	/*
	 * Reads in the map file
	 * @throws FileNotFoundException when map files aren't opened
	 * @param int map number
	 */
	public void loadMap(int mapNum) {
		Scanner read = null;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				revealed[i][j] = false;
			}
		}
		if (mapNum == 1) {
			try {
				File a1 = new File("Area1.txt");
				read = new Scanner(a1);
			}
			catch (FileNotFoundException fnf) {
			}
		}
		else if (mapNum == 2) {
			try {
				File a2 = new File("Area2.txt");
				read = new Scanner(a2);
			}
			catch (FileNotFoundException fnf) {
			}
		}
		else if (mapNum == 3) {
			try {
				File a3 = new File("Area3.txt");
				read = new Scanner(a3);
			}
			catch (FileNotFoundException fnf) {
			}
		}
		
		while (read.hasNextLine()) {
			String line = read.nextLine();
			line = line.replaceAll("\\s", "");
			try {
				for (int i = 0; i < (line.length()); i++) {
					for (int j = 0; j < (line.length()); j++) {
						map[i][j] = line.charAt(j);
					}
					line = read.nextLine();
					line = line.replaceAll("\\s", "");
				}
				read.close();
			}
			catch (NoSuchElementException nse) {
			}
		}
	}

	/*
	 * Get the character the player is at point p
	 * 
	 * @param Point p - player location
	 * 
	 * @return char from location
	 */
	public char getCharAtLoc(Point p) {
		// out of bounds return a char that isn't on the sheet, need to be able to read
		// '!' and not go that way
		if (p.x < 0) { // Rows
			return '!';
		} else if (p.x > 4) {
			return '!';
		}
		if (p.y < 0) { // Columns
			return '!';
		} else if (p.y > 4) {
			return '!';
		}

		return map[p.x][p.y];
	}

	/*
	 * Gets the char and makes an "*" at player location
	 * 
	 * @param Point p - player location
	 * 
	 * @return String of map
	 */
	public String mapToString(Point p) {
		String strungMap = "";
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if ((i == p.x) && j == p.y) {
					strungMap += "* "; // player location
				} else if (revealed[i][j] == true) {
					strungMap += map[i][j] + " ";
				} else {
					strungMap += "x ";
				}
			}
			strungMap += "\n";
		}
		return strungMap;
	}

	/*
	 * Scans through map to find 's' - only needs to be called once
	 * 
	 * @param Point p - player location
	 * 
	 * @returns the Point, player location for start
	 */
	public Point findStart() {
		Point startCord = new Point();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 's') {
					startCord.setLocation(i, j);
					return startCord;
				}
			}
		}
		return startCord;
	}

	/*
	 * As player explores will reveal point it is at
	 * 
	 * @param Point p - player location
	 */
	public void reveal(Point p) {
		revealed[p.x][p.y] = true;
	}

	/*
	 * Certain events are one time only and will be taken off the map
	 * 
	 * @param Point p - player location
	 */
	public void removeCharAtLoc(Point p) { // for items/pokemon etc
		map[p.x][p.y] = 'n';
	}
}