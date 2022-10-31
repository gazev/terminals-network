package prr.terminals;

public abstract class TerminalState {
    /**
     * Returns True if Terminal can end current ongoing Communication
     * and false elsewise
     *
     * @param context Terminal
     * @return boolean
     */
    public abstract boolean canEndCurrentCommunication(Terminal context);

    
    /**
     * Returns true if Terminal can start a new interactive
     * Communication and false elsewise
     * 
     * @return boolean
     */
    public abstract boolean canStartCommunication();

    
    public abstract boolean canReceiveTextCommunication();
    public abstract boolean canReceiveInteractiveCommunication();

    public void changeTerminalState(Terminal context, TerminalState state) {
        context.setTerminalState(state);
    }

    /** Fallback function */
    public boolean SameType(TerminalState state) {
        return false;
    }
}
