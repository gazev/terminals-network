package prr.clients;

/** 
 * A class that represents the Type of Clients in the Network, having impact on the
 * price of their communications
 */
public abstract class ClientType {
    protected TariffTable _tariffTable;

    public TariffTable getTariffTable() {
        return _tariffTable;
    }
}


