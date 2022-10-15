package prr.terminals;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import prr.clients.Client;
import prr.communications.Communication;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

        /** Terminal identifying key */
        private String _key;

        /** Client that owns this Terminal */
        private Client _owner;

        /** List of Clients that are awaiting this Terminal State update */
        private List<Client> _clientObservers;

        /** List of communications started by this Terminal */
        private Map<Integer, Communication> _communications;

        /** The current State of this Terminal */
        private TerminalState _state;

        /** Terminal friends of this Terminal */
        private Map<String, Terminal> _friends = new TreeMap<>();

        // FIXME define contructor(s)
        // FIXME define methods


        public Terminal(String key, Client owner) {
            _key = key;
            _owner = owner;
            _state = new OnTerminalState();
        }

        public String getKey() {
            return _key;
        }

        public Client getOwner() {
            return _owner;
        }

        public Integer getPaid() {
            return 0;
        }

        public Integer getDebt() {
            return 0;
        }

        public Integer getBalance() {
            return 0;
        }

        public TerminalState getState() {
            return _state;
        }

        /**
         * 
         * @param t
         */
        public void addFriend(Terminal t) {
            _friends.put(t.getKey(), t);
        }

        public TerminalState getTerminalState() {
            return _state;
        } 

        public void setTerminalState(TerminalState state) {
            _state = state;
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
        	return true;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
        	return true;
        }

        /**
         * Returns string that represents this Terminal
         * <p>
         * Formats:
         * {@code terminal-key|owner-key|state|debt|paid}
         * {@code terminal-key|owner-key|state|debt|paid|friend1,friend2,...,friendN}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            Set<String> friendSet;
            String friendsString = "";
            // if Terminal has friends, compose a string with friend's keys
            if(!_friends.isEmpty()) {
                friendSet = _friends.keySet();
                friendsString = String.join(",", friendSet);
            }

            return 
                getKey() + "|" +
                getOwner().getKey() + "|" + 
                getState() + "|" +
                getPaid() + "|" +
                getDebt() + 
                (friendsString.isEmpty() ? "" : "|" + friendsString);
        }
}
