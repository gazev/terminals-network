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
            // never happens
            return false;
        }
    }

    // TODO

    /** @see prr.terminals.TerminalState#canStartCommunication(Terminal) */
    @Override
    public boolean canStartCommunication() {
        return false;
    }

    @Override
    public boolean canReceiveTextCommunication() {
        return true;
    }

    @Override
    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    @Override
    public void changeTerminalState(Terminal context, TerminalState state) {
		if(state.isIdle()){
			context.doNotify("B2I", context.getKey());
		}
        context.setTerminalState(state);
   }

    public boolean SameType(BusyTerminalState s) {
        return true;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "BUSY";
    }

    @Override
    public boolean isSameType(TerminalState state) {
        return state.isBusy();
    }

    @Override
    public boolean isBusy() {
        return true;
    }

}
