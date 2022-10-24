package prr.communications;

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
        _price = determinePrice(_units);
    }

    public Integer determinePrice(Integer _units) {
        return _units;
    }
    
}
