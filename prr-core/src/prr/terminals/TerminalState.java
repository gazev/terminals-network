package prr.terminals;

public interface TerminalState {
    /**
     * Returns True if Terminal can end current ongoing Communication
     * and false elsewise
     *
     * @param context Terminal
     * @return boolean
     */
    public boolean canEndCurrentCommunication(Terminal context);

    
    /**
     * Returns true if Terminal can start a new interactive
     * Communication and false elsewise
     * 
     * @return boolean
     */
    public boolean canStartCommunication();

    
    public boolean canReceiveTextCommunication();
    public boolean canReceiveInteractiveCommunication();

    public void changeTerminalState(Terminal context, TerminalState state);
}
