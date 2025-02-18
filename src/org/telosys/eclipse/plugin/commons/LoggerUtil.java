package org.telosys.eclipse.plugin.commons;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
	
	private static final String defaultLoggerFileName = "telosys-plugin-log.txt" ;
	private static final Level  defaultLoggerLevel = Level.FINE ;
	
	/**
	 * Find or create a default logger 
	 * @param loggerName
	 * @return
	 */
	public static final Logger getLogger(String loggerName) {
		return getConsoleLoggerStdOut(loggerName);
		// log file is in Eclipse root dir => not handy 
		//return getFileLogger(loggerName, false);
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

    // Custom ConsoleHandler to choose OutputStream (stdout, etc) instead of stderr
    static private class CustomConsoleHandler extends ConsoleHandler {    	
        public CustomConsoleHandler(OutputStream outputStream) {
			super();
			super.setOutputStream(outputStream);
		}
    }
	
	/**
	 * Find or create a console logger writing in 'stderr'
	 * @param loggerName
	 * @return
	 */
	public static final Logger getConsoleLoggerStdErr(String loggerName) {
		// Find or create a logger for a named subsystem. 
		// If a logger has already been created with the given name it is returned. Otherwise a new logger is created. 
		Logger logger = Logger.getLogger(loggerName); 
		logger.setUseParentHandlers(false); // Prevents logging from parent handlers
		if ( logger.getHandlers().length == 0 ) { // No handler (it's a new logger instance)
	        // Create and add a ConsoleHandler
	        ConsoleHandler consoleHandler = new ConsoleHandler(); // write in 'stderr'
	        consoleHandler.setFormatter( getLoggerFormatter() );
	        consoleHandler.setLevel(Level.ALL);
	        logger.addHandler(consoleHandler);
		}
		logger.setLevel(defaultLoggerLevel);
		return logger;
	}
	
	/**
	 * Find or create a console logger writing in 'stdout'
	 * @param loggerName
	 * @return
	 */
	public static final Logger getConsoleLoggerStdOut(String loggerName) {
		// Find or create a logger for a named subsystem. 
		// If a logger has already been created with the given name it is returned. Otherwise a new logger is created. 
		Logger logger = Logger.getLogger(loggerName); 
		logger.setUseParentHandlers(false); // Prevents logging from parent handlers
		if ( logger.getHandlers().length == 0 ) { // No handler (it's a new logger instance)
	        // Create and add a ConsoleHandler
	        ConsoleHandler consoleHandler = new CustomConsoleHandler(System.out); // write in 'stdout'
	        consoleHandler.setFormatter( getLoggerFormatter() );
	        consoleHandler.setLevel(Level.ALL);
	        logger.addHandler(consoleHandler);
		}
		logger.setLevel(defaultLoggerLevel);
		return logger;
	}
	
	/**
	 * Find or create a file logger using the default file name 
	 * @param loggerName
	 * @param append
	 * @return
	 */
	public static final Logger getFileLogger(String loggerName, boolean append) {
		return getFileLogger(loggerName, defaultLoggerFileName, append); 
	}
	
	/**
	 * Find or create a file logger 
	 * @param loggerName
	 * @param fileName
	 * @param append
	 * @return
	 */
	public static final Logger getFileLogger(String loggerName, String fileName, boolean append) {
		// Find or create a logger for a named subsystem. 
		// If a logger has already been created with the given name it is returned. Otherwise a new logger is created. 
		Logger logger = Logger.getLogger(loggerName); 
		logger.setUseParentHandlers(false); // Prevents logging from parent handlers
		if ( logger.getHandlers().length == 0 ) { // No handler (it's a new logger instance)
	        // Create and add a FileHandler
			String errMessage = "getFileLogger: Cannot create FileHandler" ;
			FileHandler fileHandler;
			try {
				// Without a full path the log file is created in the current working directory.
				fileHandler = new FileHandler(fileName, append);
			} catch (SecurityException e) {
				throw new RuntimeException(errMessage, e);
			} catch (IOException e) {
				throw new RuntimeException(errMessage, e);
			}
			fileHandler.setFormatter( getLoggerFormatter() );
			fileHandler.setLevel(Level.ALL);
	        logger.addHandler(fileHandler);
		}
		logger.setLevel(defaultLoggerLevel);
		return logger;
	}

	/**
	 * Find or create a rotating file logger 
	 * @param loggerName
	 * @param fileName
	 * @param maxNumBytes
	 * @param numberOfFiles
	 * @param append
	 * @return
	 */
	public static final Logger getRotatingFileLogger(String loggerName, String fileName, int maxNumBytes, int numberOfFiles, boolean append) {
		// Find or create a logger for a named subsystem. 
		// If a logger has already been created with the given name it is returned. Otherwise a new logger is created. 
		Logger logger = Logger.getLogger(loggerName); 
		logger.setUseParentHandlers(false); // Prevents logging from parent handlers
		if ( logger.getHandlers().length == 0 ) { // No handler (it's a new logger instance)
	        // Create and add a FileHandler
			String errMessage = "getFileLogger: Cannot create FileHandler" ;
			FileHandler fileHandler;
			try {
				// Without a full path the log file is created in the current working directory.
				fileHandler = new FileHandler(fileName, maxNumBytes , numberOfFiles , append);
			} catch (SecurityException e) {
				throw new RuntimeException(errMessage, e);
			} catch (IOException e) {
				throw new RuntimeException(errMessage, e);
			}
			fileHandler.setFormatter( getLoggerFormatter() );
			fileHandler.setLevel(Level.ALL);
	        logger.addHandler(fileHandler);
		}
		logger.setLevel(defaultLoggerLevel);
		return logger;
	}

}
