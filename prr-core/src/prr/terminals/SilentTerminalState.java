package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

import prr.exceptions.SameTerminalStateException;

public class SilentTerminalState extends TerminalState implements Serializable {
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

    @Override
    public boolean canReceiveTextCommunication() {
        return true;
    }

    @Override
    public boolean canReceiveInteractiveCommunication() {
        return false;
    }

    public boolean SameType(SilentTerminalState s) throws SameTerminalStateException {
        throw new SameTerminalStateException();
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "SILENCE";
    }

    @Override
    public boolean isSameType(TerminalState state) {
        return state.isSilent();
    }

    @Override
    public boolean isSilent() {
        return true;
    }

	@Override
    public void changeTerminalState(Terminal context, TerminalState state) {
		if(state.isIdle()){
			context.doNotify("S2I", context.getKey());
		}
        context.setTerminalState(state);
   }

}
