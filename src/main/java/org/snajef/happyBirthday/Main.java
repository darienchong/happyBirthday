package org.snajef.happyBirthday;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		Scroller scroller = new Scroller("HappyBirthdayFull.txt", 60, 128, 2, false);
		scroller.start();
		
		Printer printer = new Printer("KawausoSan.txt");
		printer.print();
		
		System.out.print("\n\n");
		System.out.println(Constants.BIRTHDAY_MESSAGE);
		System.out.println("\n\n");
	}
}
