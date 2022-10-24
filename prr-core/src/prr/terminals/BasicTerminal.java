package prr.terminals;

import prr.clients.Client;
import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.exceptions.UnavailableTerminalException;

public class BasicTerminal extends Terminal {
    public BasicTerminal(String key, Client owner) {
        super(key, owner);
    }

    public BasicTerminal(String key, Client owner, TerminalState state) {
        super(key, owner, state);
    }

    public void sendTextCommunication(Terminal destination, String text) throws
                                                    UnavailableTerminalException {
        // check if destination Terminal can receive a text communication
        if(!destination.canReceiveTextCommunication()) {
            throw new UnavailableTerminalException(destination.getKey());
        }
        Communication c = new TextCommunication(this, destination, text);
        // add to this Terminal's sent communications
        this._sentCommunications.add(c);
        // add to receiver Terminal's received communications
        destination.getReceivedCommunications().add(c);
        // add debt to terminal
        this._debtBalance += c.getPrice();
    }

    /**
     * @see prr.terminals.Terminal#toString()
     */
    @Override
    public String toString() {
        return "BASIC|" + super.toString();
    }
}
