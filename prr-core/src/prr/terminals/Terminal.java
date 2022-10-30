package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import prr.Network;
import prr.clients.Client;
import prr.communications.Communication;
import prr.communications.InteractiveCommunication;
import prr.exceptions.InvalidCommunicationPaiment;
import prr.exceptions.NoActiveCommunicationException;
import prr.exceptions.SameTerminalStateException;
import prr.exceptions.UnavailableTerminalException;
import prr.exceptions.UnsupportedOperationException;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

        /** Terminal identifying key */
        protected String _key;

        /** Client that owns this Terminal */
        protected Client _owner;

        protected Double _paidBalance = 0.0;

        protected Double _debtBalance = 0.0;

        /** Current ongoing communication */
        protected InteractiveCommunication _activeCommunication;

        /** List of Clients that are awaiting this Terminal State update */
        protected List<Client> _clientObservers = new ArrayList<>();

        /** List of communications received by this Terminal */
        protected List<Communication> _receivedCommunications = new ArrayList<>();

        /** List of communications started by this Terminal */
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
        public Double getPaidBalance() { return _paidBalance; }

        /**
         * Returns Terminal's total debt balance in Communication's prices
         *
         * @return debt balance
         */
        public Double getDebtBalance() { return _debtBalance; }

        public TerminalState getTerminalState() { return _state; }

        public Map<String, Terminal> getFriends() { return _friends; }

        /**
         * Returns current Terminal's state
         *
         * @return Terminal's state
         */
        public TerminalState getState() { return _state; }

        public void setActiveCommunication(InteractiveCommunication c) {
            _activeCommunication = c;
        }

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

        public List<Client> getClientsObserver() { return _clientObservers; }

        /**
         * Returns Terminal's currently active Communication
         *
         * @return Current active Communication
         *
         * @throws NoActiveCommunication if there is not active communication
         */
        public Communication getActiveCommunication() throws NoActiveCommunicationException {
            if(_activeCommunication == null) {
                throw new NoActiveCommunicationException();
            }
            return _activeCommunication;
        }

		public Communication getUnpaidCommunication(Integer id) throws InvalidCommunicationPaiment{
			for(Communication c : _sentCommunications){
				if(c.getNumber().equals(id)){
					if(c.isFinished() && !c.isPaid()){
						return c;
					}
					else{
						break;
					}
				}
			}
			throw new InvalidCommunicationPaiment(id);
		}

        public void addFriend(String key, Network context)
                                    throws prr.exceptions.UnknownTerminalKeyException {
            Terminal t = context.getTerminalByKey(key);

            // if trying to add Terminal to its own friends
            if(key.equals(_key)) {
                return;
            }
            // if terminal is already a friend
            if(isFriend(t)) {
                return;
            }

            // add to friends list
            _friends.put(t.getKey(), t);
            // add this Terminal to other Terminal's friend list
            t.getFriends().put(_key, this);
        }

        /**
         *
         * @param key Key of Terminal to be removed from Terminals Friends
         * @param context The network context
         * @throws prr.exceptions.UnknownTerminalKeyException If Terminal with
         *                                                    specified key doesn't exist
         */
        public void removeFriend(String key, Network context)
                                        throws prr.exceptions.UnknownTerminalKeyException {
            Terminal t = context.getTerminalByKey(key);

            // if trying to remove same Terminal from its friends list
            if(key.equals(_key)) {
                return;
            }

            // if Terminal is not a friend
            if(!isFriend(t)) {
                return;
            }

            // remove Terminal from friends
            _friends.remove(t.getKey());
            // remove this Terminal from other Terminal friends
            t.getFriends().remove(_key);
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

		public void payCommunication(Integer idComm) throws InvalidCommunicationPaiment {
			Communication c = getUnpaidCommunication(idComm);
			//TODO

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

        public abstract boolean canReceiveInteractiveCommunication(String commType)
                    throws UnsupportedOperationException;


        public abstract void sendTextCommunication(String key, String text, Network context)
                    throws UnavailableTerminalException, prr.exceptions.UnknownTerminalKeyException;

        public abstract void sendInteractiveCommunication(String key, String commType, Network context)
                    throws UnavailableTerminalException, prr.exceptions.UnknownTerminalKeyException,
                        prr.exceptions.UnsupportedOperationException;

        public abstract Integer endInteractiveCommunication(Integer duration);

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
                (int) Math.round(_paidBalance) + "|" +
                (int) Math.round(_debtBalance) +
                (friendsString.isEmpty() ? "" : "|" + friendsString);
        }
}
