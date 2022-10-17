package prr.communications;

import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    /** This communication's message */
    String _text;

    TextCommunication(Terminal sender, Terminal receiver, String text) {
        super(sender, receiver);
        _text = text;
        setUnits(text.length());
    }
    
}
