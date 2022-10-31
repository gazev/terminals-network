package prr.communications;

import prr.clients.TariffTable;
import prr.terminals.Terminal;

/** A Text Communication */
public class TextCommunication extends Communication {

    /** This communication's message */
    private String _text;

    /**
     * 
     * @param sender Terminal that started this communication
     * @param receiver Terminal receiving this communication
     * @param text The text body of this communication
     */
    public TextCommunication(Terminal sender, Terminal receiver, String text) {
        super(sender, receiver);
        _text = text;
        _finished = true;
        _units = text.length();
    }

    /**
     * @see prr.communications.Communication#determinePrice(TariffTable)
     */
    @Override
    public void determinePrice(TariffTable tp) {
        setPrice(tp.calculatePrice(this));
    }
    
}
