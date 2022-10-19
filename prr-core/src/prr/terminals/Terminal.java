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
import prr.exceptions.NoActiveCommunication;

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

        private Integer _paidBalance = 0;

        private Integer _debtBalance = 0;

        /** Current ongoing communication */
        private Communication _activeCommunication;

        /** List of Clients that are awaiting this Terminal State update */
        private List<Client> _clientObservers = new ArrayList<>();

        /** List of communications started by this Terminal */
        private List<Communication> _receivedCommunications = new ArrayList<>();

        /** List of communications recieved by this Terminal */
        private List<Communication> _sentCommunications = new ArrayList<>();

        /** The current State of this Terminal */
        private TerminalState _state;

        /** Terminal friends of this Terminal */
        private Map<String, Terminal> _friends = new TreeMap<>();

        public Terminal(String key, Client owner) {
            _key = key;
            _owner = owner;
            _state = new OnTerminalState();
        }

        public String getKey() {
            return _key;
        }

        /**
         * Returns Terminal's currently active Communication
         *
         * @return Current active Communication
         *
         * @throws NoActiveCommunication if there is not active communication
         */
        public Communication getActiveCommunication() throws NoActiveCommunication {
            // terminal doesn't have an active communication
            if(_activeCommunication == null)
                throw new NoActiveCommunication();

            return _activeCommunication;
        }

        public Client getOwner() { return _owner; }

        public Integer getPaidBalance() { return _paidBalance; }

        public Integer getDebtBalance() { return _debtBalance; }

        public TerminalState getState() { return _state; }

        public List<Communication> getStartedCommunications() {
            return _sentCommunications;
        }

        public List<Communication> getReceivedCommunications() {
            return _receivedCommunications;
        }

        /**
         * Adds given Terminal to Terminal's friend list
         *
         * @param t Terminal to be added to the Friends list
         */
        public void addFriend(Terminal terminal) {
            // TODO integrity check
            _friends.put(terminal.getKey(), terminal);
        }

        /**
         * Removes given Terminal from Terminal's friend list
         * 
         * @param terminal Terminal to be removed from Friends list
         */
        public void removeFriend(Terminal terminal) {
            // TODO integrity check
            _friends.remove(terminal.getKey());
        }

        /**
         * Returns True if Terminal with given key is a 
         * friend 
         *
         * @param key Terminal's key
         *
         * @return true if Terminal with given key is friends with
         *         given Terminal
         */
        public boolean isFriend(String key) {
            return _friends.containsKey(key);
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
            return _state.canEndCurrentCommunication(this);
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
            return _state.canStartCommunication(this);
        }

        /**
         * Returns String representation of the Terminal
         * 
         * Formats:
         * <p>
         * {@code type|terminal-key|owner-key|state|debt|paid}
         * <p>
         * {@code type|terminal-key|owner-key|state|debt|paid|friend1,friend2,...,friendN}
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
                _key + "|" +
                _owner.getKey() + "|" +
                _state + "|" +
                _paidBalance + "|" +
                _debtBalance +
                (friendsString.isEmpty() ? "" : "|" + friendsString);
        }
}
