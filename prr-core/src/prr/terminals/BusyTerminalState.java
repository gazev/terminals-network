package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

import prr.exceptions.NoActiveCommunicationException;

public class BusyTerminalState extends TerminalState implements Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    /** @see prr.terminals.TerminalState#canEndCurrentCommunication(Terminal) */
    @Override
    public boolean canEndCurrentCommunication(Terminal context) {
        try {
            return context.getActiveCommunication().getSender().equals(context);
        } catch (NoActiveCommunicationException e) {
            return false;
        }
    }

    /** @see prr.terminals.TerminalState#canStartCommunication(Terminal) */
    @Override
    public boolean canStartCommunication() {
        return false;
    }

    /** @see prr.terminals.TerminalState#canReceiveTextCommunication() */
    @Override
    public boolean canReceiveTextCommunication() {
        return true;
    }

    /** @see prr.terminals.TerminalState#canReceiveInteractiveCommunication() */
    @Override
    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    /** @see prr.terminals.TerminalState#changeTerminalState(Terminal, TerminalState) */
    @Override
    public void changeTerminalState(Terminal context, TerminalState state) {
		if(state.isIdle()){
			context.doNotify("B2I", context.getKey());
		}
        context.setTerminalState(state);
   }

    /** @see prr.terminals.TerminalState#isSameType(TerminalState) */
    @Override
    public boolean isSameType(TerminalState state) {
        return state.isBusy();
    }

    /** @see prr.terminals.TerminalState#isBusy() */
    @Override
    public boolean isBusy() {
        return true;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "BUSY";
    }
}
