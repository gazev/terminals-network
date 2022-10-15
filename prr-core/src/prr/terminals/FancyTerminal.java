package prr.terminals;

import prr.clients.Client;

public class FancyTerminal extends BasicTerminal {
    public FancyTerminal(String key, Client owner) {
        super(key, owner);
    }

    public String toString() {
        return "FANCY|" + super.toString();
    }
    
}
