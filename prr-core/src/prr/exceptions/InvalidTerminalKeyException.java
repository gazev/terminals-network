package prr.exceptions;

import java.io.Serial;

/**
 * Exception thrown when an invalid key is provided to register a new Terminal
 */
public class InvalidTerminalKeyException extends Exception {
	/** Serial number for serialization. */
    @Serial
	private static final long serialVersionUID = 202208091753L;

    private String _key;

	/** @param key the duplicated key */
	public InvalidTerminalKeyException(String key) {
        _key = key;
	}

    public String getKey() {
        return _key;
    }

}