package prr.exceptions;

import java.io.Serial;

import prr.terminals.TerminalState;

/**
 * Exception thrown when a communication cannot be sent to a Terminal 
 */
public class UnavailableTerminalException extends Exception {
	/** Serial number for serialization. */
    @Serial
	private static final long serialVersionUID = 202208091753L;

    private String _key;
    // state of the Terminal 
    private TerminalState _state;

	/** @param key unavailable Terminal key */
	public UnavailableTerminalException(String key, TerminalState state) {
        _key = key;
        _state = state;
	}

    public String getKey() {
        return _key;
    }

    public TerminalState getState() {
        return _state;
    }
}

