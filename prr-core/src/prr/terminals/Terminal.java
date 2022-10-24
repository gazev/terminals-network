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
import prr.exceptions.SameTerminalStateException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnavailableTerminalException;

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

        protected Integer _paidBalance = 0;

        protected Integer _debtBalance = 0;

        /** Current ongoing communication */
        protected Communication _activeCommunication;

        /** List of Clients that are awaiting this Terminal State update */
        protected List<Client> _clientObservers = new ArrayList<>();

        /** List of communications started by this Terminal */
        protected List<Communication> _receivedCommunications = new ArrayList<>();

        /** List of communications recieved by this Terminal */
        protected List<Communication> _sentCommunications = new ArrayList<>();

        /** The current State of this Terminal */
        protected TerminalState _state;

        /** Terminal friends of this Terminal */
        protected Map<String, Terminal> _friends = new TreeMap<>();

        /**
         * 
         * @param key Terinal identifying key
         * @param owner Terminal's Client owner
         */
        public Terminal(String key, Client owner) {
            _key = key;
            _owner = owner;
            _state = new OnTerminalState();
        }

        public Terminal(String key, Client owner, TerminalState state) {
            _key = key;
            _owner = owner;
            _state = state;
        }

        /**
         * Returns Terminal's identifying key
         * 
         * @return Terminal key
         */
        public String getKey() { return _key; }

        /**
         * Returns Terminal's owner
         * @return
         */
        public Client getOwner() { return _owner; }

        /**
         * Returns Terminal's total paid balance in Communication's prices
         * 
         * @return paid balance
         */
        public Integer getPaidBalance() { return _paidBalance; }

        /**
         * Returns Terminal's total debt balance in Communication's prices
         * 
         * @return debt balance
         */
        public Integer getDebtBalance() { return _debtBalance; }

        public TerminalState getTerminalState() { return _state; }

        /**
         * Returns current Terminal's state
         * 
         * @return Terminal's state
         */
        public TerminalState getState() { return _state; }

        public void setTerminalState(TerminalState state) { _state = state; }

        /**
         * Returns a List of all Communications started by Terminal
         * 
         * @return List of Communications started by this Terminal
         */
        public List<Communication> getStartedCommunications() { return _sentCommunications; }

        /**
         * Returns a List of all Communications received by Terminal
         * 
         * @return List of Communications received by Terminal
         */
        public List<Communication> getReceivedCommunications() { return _receivedCommunications; }

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

        /**
         * Adds given Terminal to Terminal's friend list
         *
         * @param t Terminal to be added to the Friends list
         */
        public void addFriend(Terminal terminal) {
            // if Terminal already in friends list
            if(isFriend(terminal)) {
                return;
            }
            _friends.put(terminal.getKey(), terminal);
            // add this Terminal to other Terminal's friend list
            terminal.addFriend(this);
        }

        /**
         * Removes given Terminal from Terminal's friend list
         * 
         * @param terminal Terminal to be removed from Friends list
         */
        public void removeFriend(Terminal terminal) {
            if(!isFriend(terminal)) {
                return;
            }
            _friends.remove(terminal.getKey());
            // remove this Terminal from other's Terinal friends list
            terminal.removeFriend(this);
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
        public boolean isFriend(Terminal terminal) {
            return _friends.containsKey(terminal.getKey());
        }


        public void changeTerminalState(TerminalState state) throws 
                                                        SameTerminalStateException {
            // if trying to change into the same state
            if(state.getClass().equals(_state.getClass())) {
                throw new SameTerminalStateException();
            }
            _state.changeTerminalState(this, state);
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
            return _state.canEndCurrentCommunication();
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
            return _state.canStartCommunication();
        }

        /**
         * True if Terminal can receive a text communication, that is
         * if it isn't off or busy (with an active communication)
         *  
         * @return true if Terminal can receive a text communication
         */
        public boolean canReceiveTextCommunication() {
            return _state.canReceiveTextCommunication();
        }

        public boolean canReceiveInteractiveCommunication() {
            return _state.canReceiveInteractiveCommunication();
        }

        
        public void sendTextCommunication(Terminal origin, String text) throws 
                                                        UnavailableTerminalException {
        }

        public void sentInteractiveCommunication(Terminal origin) {
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
