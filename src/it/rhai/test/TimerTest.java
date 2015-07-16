package it.rhai.test;

import java.util.Timer;
import java.util.TimerTask;


public class TimerTest {

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("ciao".hashCode());					
			}
		}, 1000, 1000);
	}

}
