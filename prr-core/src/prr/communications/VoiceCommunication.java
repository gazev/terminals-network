package prr.communications;

import prr.clients.TariffTable;
import prr.terminals.Terminal;

/** A Voice Communication */
public class VoiceCommunication extends InteractiveCommunication {
    public VoiceCommunication(Terminal sender, Terminal receiver) {
        super(sender, receiver);
        _price = 0.0;
    }
    
    /**
     * @see prr.communications.Communication#determinePrice(TariffTable)
     */
    @Override
    public void determinePrice(TariffTable tp) {
        setPrice(tp.calculatePrice(this));
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return super.toString().replace("TEXT", "VOICE");
    }
}
