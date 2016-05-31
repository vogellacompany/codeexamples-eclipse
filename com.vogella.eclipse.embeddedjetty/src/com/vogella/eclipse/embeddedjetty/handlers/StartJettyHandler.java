package com.vogella.eclipse.embeddedjetty.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
public class StartJettyHandler {

	@Execute
	public void execute()  throws Exception{
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Server server = new Server(8080);
		        try {
		            server.getConnectors()[0].getConnectionFactory(HttpConnectionFactory.class);
		            server.setHandler(new HelloHttpRequestHandler());

		            server.start();
		            server.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
		
		
	}
}
