package org.snajef.happyBirthday;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Processes the ASCII art and returns a List<String> that is easier to manipulate.
 * @author froze
 *
 */
public class Frames {
	private static final String FRAME_DELIMITER = "[frame]";
	
	public static List<String> readFramesFrom(String fileName) throws FileNotFoundException {
		return readFramesFrom(fileToScanner(fileName));
	}
	
	public static List<String> readHorizontalFrom(String fileName) throws FileNotFoundException {
		return readHorizontalFrom(fileToScanner(fileName));
	}
	
	public static List<String> readVerticalFrom(String fileName) throws FileNotFoundException {
		return readVerticalFrom(fileToScanner(fileName));
	}
	/**
	 * Grabs all the lines and stores them in a List of still frames of ASCII art.
	 * Each frame should be separated by the [frame] keyword in the text file.
	 * @param sc
	 */
	private static List<String> readFramesFrom(Scanner sc) {
		List<String> toReturn = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		String s = " ";
		while (sc.hasNextLine() && !((s = sc.nextLine()).equals(""))) {
			if (s.trim().equals(Frames.FRAME_DELIMITER)) {
				toReturn.add(sb.toString());
				sb = new StringBuilder();
				continue;
			}
			sb.append(s).append("\n");
		}
		
		sc.close();
		
		return toReturn;
	}
	
	private static List<String> readHorizontalFrom(Scanner sc) {
		List<String> toReturn = new ArrayList<>();
		
		String s = " ";
		while (sc.hasNextLine() && !((s = sc.nextLine()).equals(""))) {
			toReturn.add(s);
		}
		
		sc.close();
		
		return toReturn;
	}
	
	private static List<String> readVerticalFrom(Scanner sc) {
		List<String> horizontalLines = readHorizontalFrom(sc);
		int height = horizontalLines.size();
		int width = horizontalLines.get(0).length();
		
		List<String> toReturn = new ArrayList<>();
		for (int i = 0; i < width; i++) {
			StringBuilder verticalPiece = new StringBuilder();
			for (int j = 0; j < height; j++) {
				verticalPiece.append(horizontalLines.get(j).substring(i, i+1));
				if (j != height - 1) {
					verticalPiece.append("\n");
				}
			}
			
			toReturn.add(verticalPiece.toString());
		}
		
		return toReturn;
	}
	
	private static Scanner fileToScanner(String fileName) throws FileNotFoundException {
		InputStream srcStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		
		if (srcStream == null) {
			throw new FileNotFoundException("No file found at [" + fileName + "].");
		}
		
		Scanner scanner = new Scanner(srcStream);
		return scanner;
	}
}
