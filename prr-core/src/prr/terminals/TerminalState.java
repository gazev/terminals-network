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
     * @param context the Terminal
     * 
     * @return boolean
     */
    public boolean canStartCommunication(Terminal context);
}
