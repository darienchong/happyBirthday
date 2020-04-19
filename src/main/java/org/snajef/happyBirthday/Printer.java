package org.snajef.happyBirthday;

import java.io.FileNotFoundException;

/**
 * Prints out a still ASCII art from a given file.
 * @author froze
 *
 */
public class Printer {
	private String frame;
	
	public Printer(String fileName) throws FileNotFoundException {
		this.frame = Frames.readFramesFrom(fileName).get(0);
	}
	
	public void print() {
		System.out.println(frame);
	}
}
