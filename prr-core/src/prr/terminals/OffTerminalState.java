package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

import prr.exceptions.SameTerminalStateException;

public class OffTerminalState extends TerminalState implements Serializable {
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

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "OFF";
    }

    @Override
    public boolean isSameType(TerminalState state) {
        return state.isOff();
    }

    @Override
    public boolean isOff() {
        return true;
    }

    @Override
    public void changeTerminalState(Terminal context, TerminalState state) {
        // when changing from Off notify Clients awaiting
    }

}
