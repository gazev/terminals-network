package prr.communications;

import prr.terminals.BusyTerminalState;
import prr.terminals.Terminal;

/** An Interactive Communication (either Video or Voice) */
public abstract class InteractiveCommunication extends Communication {
    InteractiveCommunication(Terminal sender, Terminal receiver) {
        super(sender, receiver);
        _finished = false;
        _price = 0.0;
        _units = 0;
        // accordingly register Communication  
        registerCommunication();
    }

    /**
     * Registers Communication in the context of its Terminals
     * and  updates Terminals states
     */
    public void registerCommunication() {
        // register communication
        _sender.getStartedCommunications().add(this);
        _receiver.getReceivedCommunications().add(this);
        // add communication reference to endpoints
        _sender.setActiveCommunication(this);
        _receiver.setActiveCommunication(this);
        // save endpoints states
        _sender.setTerminalStateBeforeBusy(_sender.getState());
        _receiver.setTerminalStateBeforeBusy(_receiver.getState());
        // set endpoints states to busy
        _sender.getState().changeTerminalState(_sender, new BusyTerminalState());
        _receiver.getState().changeTerminalState(_sender, new BusyTerminalState());
    }

    /**
     * Sets Communication as finished and updates Terminals states
     */
    public void setFinished() {
        _finished = true; 
        // remove active communication from Terminals
        _sender.setActiveCommunication(null);
        _receiver.setActiveCommunication(null);
        // restore Terminals' previous states
        _sender.getState().changeTerminalState(_sender, _sender.getStateBeforeBusy());
        _receiver.getState().changeTerminalState(_receiver, _receiver.getStateBeforeBusy());
    }

    /**
     * @see prr.communications.Communication#setPrice(Double)
     */
    @Override
    public void setPrice(Double price) {
        if(_sender.isFriend(_receiver)) {
            price /= 2;
        }
        _price = price;
    }
}
