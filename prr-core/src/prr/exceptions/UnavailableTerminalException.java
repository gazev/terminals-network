package prr.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a communication cannot be sent to a Terminal 
 */
public class UnavailableTerminalException extends Exception {
	/** Serial number for serialization. */
    @Serial
	private static final long serialVersionUID = 202208091753L;

    private String _key;

	/** @param key unavailable Terminal key */
	public UnavailableTerminalException(String key) {
        _key = key;
	}

    public String getKey() {
        return _key;
    }
}

