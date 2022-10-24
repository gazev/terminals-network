package prr.terminals;

import prr.clients.Client;

public class FancyTerminal extends BasicTerminal {
    public FancyTerminal(String key, Client owner) {
        super(key, owner);
    }

    public FancyTerminal(String key, Client owner, TerminalState state) {
        super(key, owner, state);
    }

    /** 
     * @see prr.terminals.Terminal#toString() 
     */
    @Override
    public String toString() {
        return super.toString().replace("BASIC", "FANCY");
    }
}
