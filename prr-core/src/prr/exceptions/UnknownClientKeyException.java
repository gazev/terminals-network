package prr.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a Client with a given key doesn't exist
 */
public class UnknownClientKeyException extends Exception {
	/** Serial number for serialization. */
    @Serial
	private static final long serialVersionUID = 202208091753L;

    private String _key;

	/** @param key the duplicated key */
	public UnknownClientKeyException(String key) {
        _key = key;
	}

    public String getKey() {
        return _key;
    }

}
