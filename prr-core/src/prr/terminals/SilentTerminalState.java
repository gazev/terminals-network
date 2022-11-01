package prr.terminals;

import java.io.Serial;
import java.io.Serializable;


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

    /** @see prr.terminals.TerminalState#isSameType(TerminalState) */
    @Override
    public boolean isSameType(TerminalState state) {
        return state.isSilent();
    }

    /** @see prr.terminals.TerminalState#isSilent() */
    @Override
    public boolean isSilent() {
        return true;
    }

    /** @see prr.terminals.Terminal#changeTerminalState(TerminalState, prr.Network) */
	@Override
    public void changeTerminalState(Terminal context, TerminalState state) {
        // notify Client observers of State change
		if(state.isIdle()){
			context.doNotify("S2I", context.getKey());
		}
        context.setTerminalState(state);
   }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "SILENCE";
    }
}
