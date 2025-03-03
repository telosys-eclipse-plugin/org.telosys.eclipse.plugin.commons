package org.telosys.eclipse.plugin.commons;

public class TelosysPluginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public TelosysPluginException(String message) {
        super(message);
    }

	public TelosysPluginException(String message, Throwable cause) {
		super(message, cause);
	}
}
