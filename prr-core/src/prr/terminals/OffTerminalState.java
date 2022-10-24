package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

public class OffTerminalState implements TerminalState, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    /** @see prr.terminals.TerminalState#canEndCurrentCommunication(Terminal) */
    @Override
    public boolean canEndCurrentCommunication() {
        return false;
    }

    /** @see prr.terminals.TerminalState#canStartCommunication(Terminal) */
    @Override
    public boolean canStartCommunication() {
        return false;
    }

    @Override
    public boolean canReceiveTextCommunication() {
        return false;
    }

    @Override
    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    @Override
    public void changeTerminalState(Terminal context, TerminalState state) {
        // cannot change from off to busy 
        if(state.getClass().equals(BusyTerminalState.class)) {
            return;
        }
        context.setTerminalState(state);
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "OFF";
    }

}
