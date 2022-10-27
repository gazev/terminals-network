package prr.communications;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    
    InteractiveCommunication(Terminal sender, Terminal receiver) {
        super(sender, receiver);
        _finished = false;
        _price = 0.0;
        _units = 0;
    }

    // public abstract boolean determinePrice(TariffPlan tp);
    
}
