package org.telosys.eclipse.plugin.commons;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class DefaultFormatter extends Formatter {

	private static final String FORMAT = "[%1$tF %1$tT] %2$s : %3$s %n";

	@Override
	public String format(LogRecord record) {
		return String.format(FORMAT, record.getMillis(), // Timestamp
				// record.getLevel(), // Log Level (INFO, WARNING, etc.)
				record.getLoggerName(), record.getMessage() // Log Message
		);
	}

}
