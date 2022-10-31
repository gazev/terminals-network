package prr.terminals;

import prr.Network;
import prr.clients.Client;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnavailableTerminalException;

public class FancyTerminal extends BasicTerminal {
    public FancyTerminal(String key, Client owner) {
        super(key, owner);
    }

    public FancyTerminal(String key, Client owner, TerminalState state) {
        super(key, owner, state);
    }

    @Override
    public boolean canReceiveInteractiveCommunication(String commType) {
        return _state.canReceiveInteractiveCommunication();
    }

    @Override
    public void sendInteractiveCommunication(String key, String commType, Network context)
                                        throws UnavailableTerminalException, 
                                            UnknownTerminalKeyException,
                                                prr.exceptions.UnsupportedOperationException {
        Terminal destination = context.getTerminalByKey(key);

        if(!destination.canReceiveInteractiveCommunication(commType)) {
            destination.getClientsObserver().add(_owner);
            throw new UnavailableTerminalException(destination.getKey(), destination.getState().toString());
        }

        // create communication
        if(commType.equals("VOICE")) {
            new VoiceCommunication(this, destination);
        } else {
            new VideoCommunication(this, destination);
        }
        // set context dirty
        context.setDirty();
    }

    /** 
     * @see prr.terminals.Terminal#toString() 
     */
    @Override
    public String toString() {
        return super.toString().replace("BASIC", "FANCY");
    }
}
