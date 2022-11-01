package prr.terminals;

import prr.Network;
import prr.clients.Client;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnavailableTerminalException;

/** A Fancy Terminal that performs all types of Communications */
public class FancyTerminal extends BasicTerminal {
    public FancyTerminal(String key, Client owner) {
        super(key, owner);
    }

    public FancyTerminal(String key, Client owner, TerminalState state) {
        super(key, owner, state);
    }

    /** @see prr.terminals.Terminal#canReceiveInteractiveCommunication(String) */
    @Override
    public boolean canReceiveInteractiveCommunication(String commType) {
        return _state.canReceiveInteractiveCommunication();
    }

    /** @see prr.terminals.Terminal#sendInteractiveCommunication(String, String, Network) */
    @Override
    public void sendInteractiveCommunication(String key, String commType, Network context)
                                        throws UnavailableTerminalException,
                                            UnknownTerminalKeyException,
                                                prr.exceptions.UnsupportedOperationException {
        Terminal destination = context.getTerminalByKey(key);

        if(!destination.canReceiveInteractiveCommunication(commType)) {
			if(_owner.notificationsOn() && !destination.getClientsObserver().contains(_owner)){
				destination.getClientsObserver().add(_owner);
                context.setDirty();
			}
            throw new UnavailableTerminalException(destination.getKey(), destination.getState());
        }

        // create communication
        if(commType.equals("VOICE")) {
            new VoiceCommunication(this, destination);
            _owner.incrementConsecutiveVoiceComms();
        } else {
            new VideoCommunication(this, destination);
            _owner.incrementConsecutiveVideoComms();
        }
        _owner.sendCommunication();

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
