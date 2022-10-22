package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

import prr.exceptions.NoActiveCommunication;

public class BusyTerminalState implements TerminalState, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    /** @see prr.terminals.TerminalState#canEndCurrentCommunication(Terminal) */
    @Override
    public boolean canEndCurrentCommunication(Terminal context) {
        // if terminal doesn't have an active communication, return false
        try {
            context.getActiveCommunication();
        } catch (NoActiveCommunication e) {
            return false;
        }
        return true;
    }

    // TODO

    /** @see prr.terminals.TerminalState#canStartCommunication(Terminal) */
    @Override
    public boolean canStartCommunication(Terminal context) {
        return false;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "BUSY";
    }

}
