package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

import prr.exceptions.NoActiveCommunication;

public class BusyTerminalState implements TerminalState, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

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

    @Override
    public boolean canStartCommunication(Terminal context) {
        return false;
    }

    @Override
    public String toString() {
        return "BUSY";
    }

}
