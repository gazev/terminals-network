package prr.exceptions;

import java.io.Serial;

/** Thrown when a Terminal doesn't support an operation */
public class UnsupportedOperationException extends Exception {

    @Serial
	private static final long serialVersionUID = 202208091753L;
    private String _key;

	/** @param key the duplicated key */
	public UnsupportedOperationException(String key) {
        _key = key;
	}

    public String getKey() {
        return _key;
    }

}
