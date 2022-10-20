package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

public class SilentTerminalState implements TerminalState, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    /** @see prr.terminals.TerminalState#canEndCurrentCommunication(Terminal) */
    @Override
    public boolean canEndCurrentCommunication(Terminal context) {
        return false;
    }
    
    // TODO
    
    /** @see prr.terminals.TerminalState#canStartCommunication(Terminal) */
    @Override
    public boolean canStartCommunication(Terminal context) {
        return true;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "SILENCE";
    }
}
