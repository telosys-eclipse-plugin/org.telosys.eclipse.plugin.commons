package org.telosys.eclipse.plugin.commons;

import org.eclipse.swt.widgets.Event;

public class Logger {
	
	private static final boolean active = true;

	private static void println(String msg) {
		System.out.println(msg);
		System.out.flush();
	}
	
	public static void log(String msg) {
		if ( active ) {
			println("[LOG] - " + msg);
		}
	}
	public static void log(String msg, Event event) {
		if ( active ) {
			String widgetClass = event.widget != null ? event.widget.getClass().getName() : "null" ;
			String itemClass   = event.item != null ? event.item.getClass().getName() : "null" ;
			
			println("[LOG] - " + msg + " Event: (type=" + event.type+")" 
					+ " widget:" + widgetClass + " item:" + itemClass + " item-index=" + event.index 
					+ " button=" + event.button + " count=" + event.count 
					+ " (x,y)=(" + event.x +","+ event.y+")"
					+ " stateMask=" + event.stateMask 
					+ " detail=" + event.detail);
		}
	}
	
	public static void error(String msg) {
		println("[ERROR] - " + msg);
	}

}
