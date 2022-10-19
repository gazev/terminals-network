package prr.exceptions;

import java.io.Serial;

/**
 * Thrown when a Terminal key is duplicated 
 */
public class DuplicateTerminalKeyException extends Exception {
	/** Serial number for serialization. */
    @Serial
	private static final long serialVersionUID = 202208091753L;

    private String _key;

	/** @param key the duplicated key */
	public DuplicateTerminalKeyException(String key) {
        _key = key;
	}

    public String getKey() {
        return _key;
    }

}
