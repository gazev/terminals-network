package prr.clients;

/** 
 * A class that represents the Type of Clients in the Network, having impact on the
 * price of their communications
 */
public abstract class ClientType {
    protected TariffTable _tariffTable;
    protected Integer _consecutiveTextComms = 0;
    protected Integer _consecutiveVoiceComms = 0;
    protected Integer _consecutiveVideoComms = 0;

    public TariffTable getTariffTable() {
        return _tariffTable;
    }

    /** Updates Client Type on payment action */
    public void pay(Client context) {
        //
    }

    /** Updates Client Type on starting a communication */
    public void sendCommunication(Client context) {
        //
    }

    /** Changes Clients Type to given ClientType */
    public void changeClientType(ClientType t, Client context) {
        context.setClientType(t);
    }

    /** Increments counter of consecutive Text Communications */
    public void incrementConsecutiveTextComms() {
        _consecutiveTextComms++;
        _consecutiveVoiceComms = 0;
        _consecutiveVideoComms = 0;
    }

    /** Increments counter of consecutive Voice Communications */
    public void incrementConsecutiveVoiceComms() {
        _consecutiveVoiceComms++;
        _consecutiveTextComms = 0;
        _consecutiveVideoComms = 0;

    /** Increments counter of consecutive Video Communications */
    }public void incrementConsecutiveVideoComms() {
        _consecutiveVideoComms++;
        _consecutiveTextComms = 0;
        _consecutiveVoiceComms = 0;
    }
}