package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public class GoldType implements ClientType, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    @Override
    public String toString() {
        return "GOLD";
    }
}
