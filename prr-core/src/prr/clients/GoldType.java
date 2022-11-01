package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public class GoldType extends ClientType implements Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public GoldType(Client context) {
        _tariffTable = context.getTariffPlan().getGoldTable();
    }

    /** @see prr.clients.ClientType#sendCommunication(Client) */
    @Override
    public void sendCommunication(Client context) {
        if(context.getClientBalance() < 0)
            changeClientType(new NormalType(context), context);
        if(_consecutiveVideoComms.equals(5) && context.getClientBalance() > 0)
            changeClientType(new PlatinumType(context), context);
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "GOLD";
    }
}
