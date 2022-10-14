package prr.exceptions;

import java.io.Serial;

/* Exception thrown when trying to import entities that violate Network
 * constraints, such as, Clients with same keys, Terminals with unexistant
 * Clients, etc.
 */
public class IllegalEntryException extends Exception {
    
    @Serial
    private static final long serialVersionUID = 202208091753L; 

    /** Entry fields */
    private String _entryFields;

    /**
     * @param entryFields
     */
    public IllegalEntryException(String entryFields) {
        _entryFields = entryFields;
    }

    /**
     * @param entryFields
     * @param cause
     */
    public IllegalEntryException(String entryFields, Exception cause) {
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
