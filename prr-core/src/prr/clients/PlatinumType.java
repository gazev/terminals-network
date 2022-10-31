package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public class PlatinumType extends ClientType implements Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public PlatinumType(Client context) {
        _tariffTable = context.getTariffPlan().getPlatinumTable(); 
    }
    
    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "PLATINUM";
    }

}