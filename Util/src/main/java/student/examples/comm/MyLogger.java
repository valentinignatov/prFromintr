package student.examples.comm;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
	private Logger logger = Logger.getLogger(MyLogger.class.getName());
	
	public static void main(String[] args) {

        // set log level to SEVERE, default level info
        //logger.setLevel(Level.SEVERE);
		
		MyLogger mylogger = new MyLogger();

        // Log a info level msg
		mylogger.getLogger().info("This is level info logging");

		mylogger.getLogger().log(Level.WARNING, "This is level warning logging");

		mylogger.getLogger().log(Level.SEVERE, "This is level severe logging");

        System.out.println("Hello Java Logging APIs.");
    }
	
	public Logger getLogger( ) {
		return logger;
	}

}