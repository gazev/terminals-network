package prr.exceptions;

import java.io.Serial;

/**
 * Exception for import file entries that do not provide the
 * correct fields for an entity
 */
public class BadEntryException extends Exception {

    @Serial
    private static final long serialVersionUID = 202208091753L; 

    /** Entry fields */
    private String _entryFields;

    /**
     * @param entryFields
     */
    public BadEntryException(String entryFields) {
        _entryFields = entryFields;
    }

    /**
     * @param entryFields
     * @param cause
     */
    public BadEntryException(String entryFields, Exception cause) {
        super(cause);
        _entryFields = entryFields;

    }

    /**
     * @return the bad entry fields
     */
    public String getEntryFields() {
        return _entryFields;
    }
}
