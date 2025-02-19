package org.telosys.eclipse.plugin.commons;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class EclipseConsoleAsLoggerHandler extends Handler {
	
    private MessageConsoleStream consoleStream;

    public EclipseConsoleAsLoggerHandler(String consoleName) {
        MessageConsole console = findConsole(consoleName);
        this.consoleStream = console.newMessageStream();
        super.setFormatter(new DefaultFormatter() ); // Formatter is hold by the super class
    }

    public EclipseConsoleAsLoggerHandler(String consoleName, Formatter formatter) {
        MessageConsole console = findConsole(consoleName);
        this.consoleStream = console.newMessageStream();
        super.setFormatter(formatter); // Formatter is hold by the super class
    }

    private MessageConsole findConsole(String name) {
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        if (plugin != null) {
            for (IConsole console : plugin.getConsoleManager().getConsoles()) {
                if (console.getName().equals(name)) {
                   	// Console found
                    return (MessageConsole) console;
                }
            }
        }
        // Create new console if not found
        MessageConsole newConsole = new MessageConsole(name, null);
        plugin.getConsoleManager().addConsoles(new MessageConsole[]{newConsole});
        return newConsole;
    }

    @Override
    public void publish(LogRecord record) {
        if (consoleStream != null && isLoggable(record)) {
            // consoleStream.println(formatRecord(record));
        	Formatter formatter = getFormatter();        	
            consoleStream.print(formatter.format(record));
        }
    }

    private String formatRecord(LogRecord record) {
        return record.getLevel() + ": " + record.getMessage();
    }

    @Override
    public void flush() {
        if (consoleStream != null) {
            try {
                consoleStream.flush();
            } catch (Exception ignored) {
                // Ignore exception
            }
        }
    }

    @Override
    public void close() {
        if (consoleStream != null) {
            try {
                consoleStream.close();
            } catch (Exception ignored) {
                // Ignore exception
            }
        }
    }
}
