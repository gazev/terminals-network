package prr.communications;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {

    // TODO
    
    InteractiveCommunication(Terminal sender, Terminal receiver) {
        super(sender, receiver);
        _finished = false;
    }
    
}
