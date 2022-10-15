package prr.terminals;

import java.io.Serial;
import java.io.Serializable;

public class BusyTerminalState implements TerminalState, Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
   // TODO 

    @Override
    public String toString() {
        return "BUSY";
    }
}
