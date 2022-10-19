package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public class PlatinumType implements ClientType, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    // TODO
    
    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "PLATINUM";
    }

}