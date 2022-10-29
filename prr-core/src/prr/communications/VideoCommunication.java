package prr.communications;

import prr.clients.ClientType;
import prr.clients.TariffPlan;
import prr.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {
    // TODO

    public VideoCommunication(Terminal sender, Terminal receiver) {
        super(sender, receiver);
        _price = 0.0;
    }

    @Override
    public void determinePrice(TariffPlan tp, ClientType type) {
        _price =  tp.calculatePrice(this, type);
    }

    @Override
    public String toString() {
        return super.toString().replace("TEXT", "VIDEO");
    }
}
