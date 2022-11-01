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

    /** @see prr.terminals.TerminalState#canStartCommunication(Terminal) */
    @Override
    public boolean canStartCommunication() {
        return true;
    }

    /** @see prr.terminals.TerminalState#canReceiveTextCommunication() */
    @Override
    public boolean canReceiveTextCommunication() {
        return true;
    }

    /** @see prr.terminals.TerminalState#canReceiveInteractiveCommunication() */
    @Override
    public boolean canReceiveInteractiveCommunication() {
        return true;
    }

    /** @see prr.terminals.TerminalState#isSameType(TerminalState) */
    @Override
    public boolean isSameType(TerminalState s) {
        return s.isIdle();
    }

    /** @see prr.terminals.TerminalState#isIdle() */
    @Override
    public boolean isIdle() { 
        return true;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "IDLE";
    }


}
