package prr.terminals;

import prr.Network;
import prr.clients.Client;
import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.communications.VoiceCommunication;
import prr.exceptions.UnavailableTerminalException;
import prr.exceptions.UnknownTerminalKeyException;

public class BasicTerminal extends Terminal {
    public BasicTerminal(String key, Client owner) {
        super(key, owner);
    }

    public BasicTerminal(String key, Client owner, TerminalState state) {
        super(key, owner, state);
    }


    @Override
    public void sendTextCommunication(String key, String text, Network context) throws
                                                    UnavailableTerminalException, 
                                                        prr.exceptions.UnknownTerminalKeyException {
        Terminal destination = context.getTerminalByKey(key);

        // cannot send communication to itself
        if(key.equals(this.getKey())) {
            return;
        }

        // check if destination Terminal can receive a text communication
        if(!destination.canReceiveTextCommunication()) {
            destination.getClientsObserver().add(_owner);
            throw new UnavailableTerminalException(destination.getKey(), destination.getState().toString());
        }
        
        Communication c = new TextCommunication(this, destination, text);
        // add to this Terminal's sent communications
        this._sentCommunications.add(c);
        // add to receiver Terminal's received communications
        destination.getReceivedCommunications().add(c);
        // determine the cost of the communication
        c.determinePrice(_owner.getClientType().getTariffTable());
        _debtBalance += c.getPrice();
        // set context dirty
        context.setDirty();
    }

    @Override
    public boolean canReceiveInteractiveCommunication(String commType) 
                                    throws prr.exceptions.UnsupportedOperationException {
        // basic Terminal cannot perform Video Communications
        if(commType.equals("VIDEO")) {
            throw new prr.exceptions.UnsupportedOperationException(this.getKey());
        }

        return _state.canReceiveInteractiveCommunication();
    }

    @Override
    public void sendInteractiveCommunication(String key, String commType, Network context) 
                                throws UnavailableTerminalException, UnknownTerminalKeyException,
                                    prr.exceptions.UnsupportedOperationException {
        // get destionation Terminal
        Terminal destination = context.getTerminalByKey(key);

        // if trying to do a Video communication on a Basic Terminal
        if(commType.equals("VIDEO")) {
            throw new prr.exceptions.UnsupportedOperationException(this.getKey());
        }

        // if destination can receive an interactive communication
        if(!destination.canReceiveInteractiveCommunication(commType)) {
            destination.getClientsObserver().add(_owner);
            throw new UnavailableTerminalException(destination.getKey(), destination.getState().toString());
        }

        // create new communication
        new VoiceCommunication(this, destination);
        // set context dirty
        context.setDirty();
    }

    /**
     * @see prr.terminals.Terminal#toString()
     */
    @Override
    public String toString() {
        return "BASIC|" + super.toString();
    }

}
