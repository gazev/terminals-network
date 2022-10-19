package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

public class OffTerminalState implements TerminalState, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    /** @see prr.terminals.TerminalState#canEndCurrentCommunication(Terminal) */
    @Override
    public boolean canEndCurrentCommunication(Terminal context) {
        return false;
    }

    /** @see prr.terminals.TerminalState#canStartCommunication(Terminal) */
    @Override
    public boolean canStartCommunication(Terminal context) {
        return false;
    }

    @Override
    public String toString() {
        return "OFF";
    }

}
