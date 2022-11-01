package prr.clients;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

import prr.terminals.Terminal;

/** A Client in the Network */
public class Client implements Serializable {

    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    /** Client's key */
    private String _key;

    /** Client's name */
    private String _name;

    /** Client's tax ID */
    private int _taxId;

    /** Client's type */
    private ClientType _type;

    // TariffPlan _tariffPlan = new BaseTariffPlan();

    /** Map of this Client's Terminals and their respective keys */
    private Map<String, Terminal> _terminals = new TreeMap<>();

    /** Client's notifications not yet delivered */
    private Queue<Notification> _unhandledNotificationsLog = new LinkedList<>();

    /** A log of all Client notifications */
    private Queue<Notification> _notificationsLog = new LinkedList<>();

    /** Flag to determine if Client should be notified */
    private boolean _notificationsOn = true;

    /** Base TariffPlan */
    private TariffPlan _tariffPlan = new BaseTariffPlan();

    /** Notification method, initialized with default NotificationMethod */
    NotificationMethod _notificationMethod = new NotificationMethod() {
        /* The default notification method does not perform any relevant action,
         * it only registers the notification in a log of notifications that were
         * not attended at the time of their occurence 
         * and will be displayed once a user is visualized 
         */
        @Serial
        /** Serial number for serialization. */
	    private static final long serialVersionUID = 202208091753L;

        @Override
        /** The default notification method simply registers a Notification that was unhandled */
        public void deliverNotification(Notification n) {
			_unhandledNotificationsLog.add(n);
        }
    };

    /** Notificaitons that Clients receive */
    public class Notification {
        /** ID of the Terminal that originated the Notification */
		private String _terminalSenderKey;

        /** String that describes the Type of Notification
         * <p>
         * O2I: Off-to-Silent
         * <p>
         * O2S: Off-to-Silent
         * <p>
         * B2I: Busy-to-Idle
         * <p>
         * S2I: Silent-to-Idle
         */
		private String _notificationType;

        public Notification(String terminalSenderKey, String notificationType) {
			_terminalSenderKey = terminalSenderKey;
			_notificationType = notificationType;
            _notificationsLog.add(this);
		}

        /** @see java.lang.Object#toString() */
		public String toString(){
			return _notificationType + "|" + _terminalSenderKey;
		}
    }


    /**
     *
     * @param key Client's identifying key
     * @param name Client's name
     * @param taxId Client's tax ID
     */
    public Client(String key, String name, int taxId) {
        _key = key;
        _name = name;
        _taxId = taxId;
        _type = new NormalType(this);
    }

    /**
     *
     * @return Client's identifying key
     */
    public String getKey() {
        return _key;
    }

    /**
     *
     * @return Client's name
     */
    public String getName() {
        return _name;
    }

    /**
     *
     * @return Client's Tax Id
     */
    public int getTaxId() {
        return _taxId;
    }

    /**
     *
     * @return Client's type
     */
    public ClientType getClientType() {
        return _type;
    }

    /**
     * 
     * @return Client's TariffPlan
     */
    public TariffPlan getTariffPlan() { return _tariffPlan; }

    public Collection<Terminal> getTerminals() {
        return _terminals.values();
    }

    /**
     *
     * @return Client's notification flag
     */
    public boolean notificationsOn() {
        return _notificationsOn;
    }

    /**
     *
     * @return number of Terminals owned by the Client
     */
    public Integer getNumberOfTerminals() {
        return _terminals.size();
    }

    /**
     * 
     * @return The Notification Method of the Client
     */
	public NotificationMethod getNotificationMethod(){ return _notificationMethod; }


    /**
     * Returns this Client's balance in debt by checking each of his Terminal's
     * debt balance.
     *
     * @return Client's debt balance
     *
     */
    public Double getClientDebtBalance() {
        Double sum = 0.0;
        for(Terminal t : getTerminals())
            sum += t.getDebtBalance();

        return sum;
    }

    /**
     * Returns this Client's balance paid by checking each of his Terminal's
     * paid balance.
     *
     * @return Client's paid balance
     */
    public Double getClientPaidBalance() {
        Double sum = 0.0;
        for(Terminal t : getTerminals())
            sum += t.getPaidBalance();

        return sum;
    }

    /**
     * Returns the balance of this Client. The balance is defined as the
     * difference between the Client's paid balance and debt balance
     * 
     * @return Client's total balance
     */
    public Double getClientBalance() {
        return getClientPaidBalance() - getClientDebtBalance();
    }

    /**
     * Returns Client's unhandled Notifications
     * 
     * @return All Client's unhandled Notifications
     */
	public Collection<Notification> getUnhandledNotification(){
		List<Notification> aux = new ArrayList<>();
		while(!_unhandledNotificationsLog.isEmpty()){
			aux.add(_unhandledNotificationsLog.remove());
		}
		return aux;
	}

    /**
     * Sets notifications flag to given boolean
     *
     * @param notificationsOn true if Client whishes to get notified and false elsewise
     */
    public void setNotificationsOn(boolean notificationsOn) {
        _notificationsOn = notificationsOn;
    }

    /**
     * Sets Client's Type to given Type
     *
     * @param type new Client's Type
     */
    public void setClientType(ClientType type) {
        _type = type;
    }

    /**
     * Deliver Client notification using current Client's Notification Method
     */
    public void notify(Notification n) {
        _notificationMethod.deliverNotification(n);
    }

    /**
     * Adds given Terminal to the Client's owned Terminals
     *
     * @param terminal Terminal to be added to user's owned Terminals
     */
    public void addTerminal(Terminal terminal) {
        _terminals.put(terminal.getKey(), terminal);
    }

    /**
     * Performs the required actions on a Client after a payment
     */
    public void pay() {
        _type.pay(this);
    }

    /**
     * Performs the required actions on a Client after starting a
     * communication
     */
    public void sendCommunication() {
        _type.sendCommunication(this);
    }

    /**
     * Increments number of consecutive Text Comms of a Client
     */
    public void incrementConsecutiveTextComms() {
        _type.incrementConsecutiveTextComms();
    }

    /**
     * Increments number of consecutive Voice Comms of a Client
     */
    public void incrementConsecutiveVoiceComms() {
        _type.incrementConsecutiveVoiceComms();
    }

    /**
     * Increments number of consecutive Video Comms of a Client
     */
    public void incrementConsecutiveVideoComms() {
        _type.incrementConsecutiveVideoComms();
    }

    /**
     * Returns a string that represents this Client
     *
     * Format:
     * <p>
     * {@code CLIENT|key|name|taxId|type|NOTIFICATIONS|nr-terminals|debt|paid}
     * <p>
     * NOTIFICATIONS is either YES or NO depending if Client whishes to be notified
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CLIENT|" +
                _key + "|" +
                _name + "|" +
                _taxId + "|" +
                _type + "|" +
                (notificationsOn() ? "YES" : "NO") + "|" +
                _terminals.size() + "|" +
                (int) Math.round(getClientPaidBalance()) + "|" +
                (int) Math.round(getClientDebtBalance());
    }
}
