package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

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

    /** @see prr.terminals.TerminalState#canReceiveTextCommunication() */
    @Override
    public boolean canReceiveTextCommunication() {
        return false;
    }

    /** @see prr.terminals.Terminal#canReceiveInteractiveCommunication(String) */
    @Override
    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    /** @see prr.terminals.TerminalState#isSameType(TerminalState) */
    @Override
    public boolean isSameType(TerminalState state) {
        return state.isOff();
    }

    /** @see prr.terminals.TerminalState#isOff() */
    @Override
    public boolean isOff() {
        return true;
    }

    /** @see prr.terminals.TerminalState#changeTerminalState(Terminal, TerminalState) */
    @Override
    public void changeTerminalState(Terminal context, TerminalState state) {
        // notify the Clients of the State change
		if(state.isIdle()) {
			context.doNotify("O2I", context.getKey());
		} else{
			context.doNotify("O2S", context.getKey());
		}

        context.setTerminalState(state);
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "OFF";
    }

}
