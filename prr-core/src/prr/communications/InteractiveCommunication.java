package prr.communications;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    InteractiveCommunication(Terminal sender, Terminal receiver) {
        super(sender, receiver);
    }

    public void endCommunication(Integer duration) {
        setUnits(duration);
    }
    
}
