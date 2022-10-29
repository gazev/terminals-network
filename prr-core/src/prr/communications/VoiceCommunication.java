package prr.communications;

import prr.clients.ClientType;
import prr.clients.TariffPlan;
import prr.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {
    // TODO

    public VoiceCommunication(Terminal sender, Terminal receiver) {
        super(sender, receiver);
        _price = 0.0;
    }
    
    @Override
    // accept
    public void determinePrice(TariffPlan tp, ClientType type) {
        // tp.calculatePrice -> visit
        _price = tp.calculatePrice(this, type);
    }
    
    @Override
    public String toString() {
        return super.toString().replace("TEXT", "VOICE");
    }
}
