package prr.terminals;

import prr.Network;
import prr.clients.Client;
import prr.communications.Communication;
import prr.communications.InteractiveCommunication;
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
        c.determinePrice(_owner.getTariffPlan(), _owner.getClientType());
        _debtBalance += c.getPrice();
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

        // if destination can receive and interactive communication
        if(!destination.canReceiveInteractiveCommunication(commType)) {
            destination.getClientsObserver().add(_owner);
            throw new UnavailableTerminalException(destination.getKey(), destination.getState().toString());
        }
        InteractiveCommunication c = new VoiceCommunication(this, destination);
        // add to Terminal's sent communications
        this._sentCommunications.add(c);
        // add to destination Terminal's received communications
        destination.getReceivedCommunications().add(c);
        // add current communication to active communications in both Terminals
        this._activeCommunication = c;
        destination.setActiveCommunication(c);
        // set Terminals to busy
        this._state = new BusyTerminalState();
        destination.setTerminalState(new BusyTerminalState());
    }

    @Override
    public Integer endInteractiveCommunication(Integer duration) {
        // define units of interactive communication (duration)
        _activeCommunication.setUnits(duration);

        // calculate and set communication price
        _activeCommunication.determinePrice(_owner.getTariffPlan(), _owner.getClientType());

        // get price to return
        Double price = _activeCommunication.getPrice();

        // set communication as finished and remove references in sender and receiver terminal
        _activeCommunication.setFinished();

        // add to Terminal's debt
        _debtBalance += price;

        return (int) Math.round(price);
    }

    /**
     * @see prr.terminals.Terminal#toString()
     */
    @Override
    public String toString() {
        return "BASIC|" + super.toString();
    }

}
