
package com.vogella.rcp.headlesssimple.addon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleAddon {
	String userName = null;
	public ConsoleAddon() {

		// open up standard input
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


		Runnable runnable = new  Runnable() {

			@Override
			public void run() {
				try {
					userName = br.readLine();
					System.out.println(userName);
				} catch (IOException ioe) {
					System.out.println("IO error trying to read your name!");
					System.exit(1);
				}
			}
		};
		new Thread(runnable).start();
		// read the username from the command-line; need to use try/catch with
		// the
		// readLine() method

	}

}
