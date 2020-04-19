package org.snajef.happyBirthday;

import java.io.FileNotFoundException;
import java.util.List;

import org.fusesource.jansi.Ansi;

/**
 * Takes in a List<String> of frames and draws them at a specified fps.
 * @author froze
 *
 */
public class Drawer {
	public static final int DEFAULT_FPS = 24;
	private List<String> frames;
	
	// The time between frames, in milliseconds.
	private long wait;
	
	// Whether to loop the animation or not
	private boolean isLoop;
	
	// How many times to loop
	private int loopTimes;

	/**
	 * C'tor for the Drawer object
	 * @param frames The list of frames to draw.
	 * @param fps The number of frames to draw per second.
	 * @param loopTimes Number of times to loop. A value of (-1) corresponds to looping infinitely.
	 */
	private Drawer(List<String> frames, int fps, int loopTimes) {
		this.frames = frames;
		this.wait = 1000 / fps;
		this.isLoop = (loopTimes == 0) ? false : true;
		this.loopTimes = loopTimes;
	}
	
	public Drawer getDrawer(String fileName, int fps, int loopTimes) throws FileNotFoundException {
		Drawer drawer = new Drawer(Frames.readFramesFrom(fileName), fps, loopTimes);
		return drawer;
	}
	
	public void start() throws InterruptedException {
		boolean isLoopInfinite = false;
		if (loopTimes == -1) {
			isLoopInfinite = true;
		}
		
		while(true) {
			draw(frames);
			
			if (isLoopInfinite) {
				continue;
			}
			
			if (!isLoop) {
				break;
			}
			
			if (isLoop && loopTimes > 0) {
				loopTimes--;
				continue;
			}
			
			if (isLoop && loopTimes == 0) {
				break;
			}
		}
	}
	
	public void clearScreen() {
		System.out.print(Ansi.ansi().cursor(0, 0));
		System.out.print(Ansi.ansi().eraseScreen());
	}
	
	private void draw(List<String> frames) throws InterruptedException {
		for (int i = 0; i < frames.size(); i++) {
			String marqueeFrame = frames.get(i);

			System.out.print(Ansi.ansi().cursor(0, 0));
			Thread.sleep(wait);
			System.out.print(Ansi.ansi().eraseScreen());
			System.out.println(marqueeFrame);
		}
	}
}
