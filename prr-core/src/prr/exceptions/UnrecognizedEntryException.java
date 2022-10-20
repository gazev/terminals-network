package prr.exceptions;

/**
 * Exception thrown when an unknown entity is specified when 
 * importing from a file
 */
public class UnrecognizedEntryException extends Exception {

	/** Class serial number. */
	private static final long serialVersionUID = 202208091753L;

	/** Unrecognized entry specification. */
	private String _entrySpecification;

	/**
	 * @param entrySpecification
	 */
	public UnrecognizedEntryException(String entrySpecification) {
		_entrySpecification = entrySpecification;
	}

	/**
	 * @param entrySpecification
	 * @param cause
	 */
	public UnrecognizedEntryException(String entrySpecification, Exception cause) {
		super(cause);
		_entrySpecification = entrySpecification;
	}

	/**
	 * @return the bad entry specification.
	 */
	public String getEntrySpecification() {
		return _entrySpecification;
	}

}
