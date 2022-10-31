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

    /**
     * Checks if given state is of the same type
     * 
     * @param state Terminal state to be checked against
     * @return True if given State is the same as this State
     */
    public abstract boolean isSameType(TerminalState state); 

    /** Fallback functions, each type implements its own */
    public boolean isSilent() { return false; }
    public boolean isOff() { return false; }
    public boolean isBusy() { return false; }
    public boolean isIdle() { return false; }
}
