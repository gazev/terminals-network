package prr.communications;

import prr.clients.ClientType;
import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    /** This communication's message */
    String _text;

    // TODO

    public TextCommunication(Terminal sender, Terminal receiver, String text) {
        super(sender, receiver);
        _text = text;
        _finished = true;
        _units = text.length();
    }

    // public void determinePrice(TariffPlan tp, ClientType type) {
    //     _price = tp.calculatePrice(this, _sender.getOwner().getClientType());
    // }

    // @Override
    // public Integer determinePrice(prr.communications.TariffPlan tp) {
    //     // TODO Auto-generated method stub
    //     return null;
    // }
    
}
