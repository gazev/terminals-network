package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public class NormalType extends ClientType implements Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public NormalType(Client context) {
        _tariffTable = context.getTariffPlan().getNormalTable();
    }

    /** @see prr.clients.ClientType#pay(Client) */
    @Override
    public void pay(Client context) {
        if(context.getClientBalance() > 500) {
            changeClientType(new GoldType(context), context);
        }
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "NORMAL";
    }
}
