package org.snajef.happyBirthday;

import java.io.FileNotFoundException;
import java.util.List;

import org.fusesource.jansi.Ansi;

/**
 * Given a particular ASCII art, provides a moving window view of it.
 * The text appears to be moving from right to left.
 * Every line in the ASCII art should have the same length, including whitespaces.
 * @author froze
 *
 */
public class Scroller {
	private List<String> asciiArt;
	private int windowSize;
	private long wait;
	private boolean isLoopingInfinitely;
	private int loopTimes;
	
	/**
	 * C'tor
	 * @param fileName The filename of the ASCII art
	 * @param fps The frames per second to render
	 * @param windowSize the size of the moving window, in characters.
	 * @param loopTimes the number of times to loop; one loop is defined as the window crossing the length of the ASCII art.
	 * @param isLoopInfinitely whether the Scroller should loop infinitely.
	 * @throws FileNotFoundException 
	 */
	public Scroller(String fileName, int fps, int windowSize, int loopTimes, boolean isLoopInfinitely) throws FileNotFoundException {
		this.asciiArt = Frames.readHorizontalFrom(fileName);
		this.windowSize = windowSize;
		this.wait = (long) (1000 / fps);
		this.isLoopingInfinitely = isLoopInfinitely;
		this.loopTimes = loopTimes;
		
		// Check if all the art lines have the same length, check against the first line.
		int length = asciiArt.get(0).length();
		for (int i = 0; i < asciiArt.size(); i++) {
			if (asciiArt.get(i).length() != length) {
				throw new IllegalArgumentException("Line " + i + " of the ASCII art has an incorrect length (expected " + length + ", but got " + asciiArt.get(i).length() + ".");
			}
		}
	}
	
	public void start() throws InterruptedException {
		int totalLength = asciiArt.get(0).length(); // The length of the entire art block
		int height = asciiArt.size(); 
		
		int loopEnd = loopTimes * totalLength;
		for (int i = 0; isLoopingInfinitely || (i < loopEnd); i++) {
			System.out.print(Ansi.ansi().cursor(0, 0));
			Thread.sleep(wait);
			System.out.print(Ansi.ansi().eraseScreen());
			
			for (int j = 0; j < height; j++) {
				System.out.println(loopingSubstring(asciiArt.get(j), i % (totalLength - 1), windowSize));
			}
		}
		System.out.print(Ansi.ansi().cursor(0, 0));
		System.out.print(Ansi.ansi().eraseScreen());
	}
	
	/**
	 * Given a String, return a looping window view of it. 
	 * e.g. if the given String is "hello world", then 
	 * loopingSubstring("hello world", 6, 6) -> "worldh"
	 * loopingSubstring("hello world", 6, 16) -> "worldhello world"
	 * @param original
	 * @return
	 */
	private String loopingSubstring(String original, int start, int length) {
		int end = original.length();
		if (start + length < end) {
			return original.substring(start, start + length);
		}
		
		// e.g. loopingSubstring("hello_world", 6, 16)
		// We start at "w", at position 6
		// The length of the entire string is at position 10, at "d"
		// 6 7 8 9 10 | 11 12 13 14 15 16 17 18 19 20 21 
		// w o r l d  | h  e  l  l  o  _  w  o  r  l  d  
		//
		String untilEnd = original.substring(start, end);
		length -= untilEnd.length();
		
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(untilEnd);
		int numberOfTimesToLoop = (int) length / original.length();
		
		for (int i = 0; i < numberOfTimesToLoop; i++) {
			toReturn.append(original);
		}
		
		length -= numberOfTimesToLoop * original.length();
		
		toReturn.append(original.substring(0, length));
		
		return toReturn.toString();
	}
}
