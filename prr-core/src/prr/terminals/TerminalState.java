package prr.terminals;

public interface TerminalState {
    public boolean canEndCurrentCommunication(Terminal context);
    public boolean canStartCommunication(Terminal context);
}
