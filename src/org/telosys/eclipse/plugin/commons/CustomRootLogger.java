package org.telosys.eclipse.plugin.commons;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CustomRootLogger {

	public static void setup(String rootName, Level rootLevel, Handler... handlers) {
		Logger customRootLogger = Logger.getLogger(rootName);

		// Prevent logs from propagating to the default root logger (which has ConsoleHandler)
		customRootLogger.setUseParentHandlers(false);

		// Set desired logging level
		customRootLogger.setLevel(rootLevel);

		// Add all the given handlers
	    for ( Handler handler : handlers ) {
	    	// Configure handler
	    	handler.setLevel(rootLevel);
	    	handler.setFormatter( getLoggerFormatter() );
			// Add the handler to the custom root logger
			customRootLogger.addHandler(handler);
	    }
	}
	
	private static final Formatter getLoggerFormatter() {
		return new SimpleFormatter() {
            private static final String FORMAT = "[%1$tF %1$tT] %2$s : %3$s %n";

            @Override
            public String format(LogRecord record) {
                return String.format(FORMAT,
                    record.getMillis(),    // Timestamp
                    //record.getLevel(),     // Log Level (INFO, WARNING, etc.)
                    record.getLoggerName(),
                    record.getMessage()    // Log Message
                );
            }
        };
	}
}
