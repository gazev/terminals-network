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
    
    /** @see prr.clients.ClientType#sendCommunication(Client) */
    @Override
    public void sendCommunication(Client context) {
        if(_consecutiveTextComms.equals(2) && context.getClientBalance() > 0)
            changeClientType(new GoldType(context), context);
        if(context.getClientBalance() < 0)
            changeClientType(new NormalType(context), context);
    }
    
    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "PLATINUM";
    }

}