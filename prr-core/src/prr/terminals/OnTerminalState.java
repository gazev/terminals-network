package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

public class OnTerminalState extends TerminalState implements Serializable {
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
    public boolean canStartCommunication() {
        return true;
    }

    @Override
    public boolean canReceiveTextCommunication() {
        return true;
    }

    @Override
    public boolean canReceiveInteractiveCommunication() {
        return true;
    }

    public boolean SameType(OnTerminalState s) {
        return true;
    }
    
    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "IDLE";
    }

}
