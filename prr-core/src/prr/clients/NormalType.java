package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public class NormalType implements ClientType, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    // TODO

    @Override
    public String toString() {
        return "NORMAL";
    }
    
}
