package prr.clients;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import prr.terminals.Terminal;

/** A Client in the Network */
public class Client implements Serializable {

    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    /** Client's key */
    String _key;

    /** Client's name */
    String _name;

    /** Client's tax ID */
    int _taxId;

    /** Client's type */
    ClientType _type;

    // TariffPlan _tariffPlan = new BaseTariffPlan();

    /** Map of this Client's Terminals and their respective keys */
    Map<String, Terminal> _terminals = new TreeMap<>();

    /** Client's notifications not yet delivered */
    Queue<Notification> _notificationsLog = new LinkedList<>();

    /** Flag to determine if Client should be notified */
    boolean _notificationsOn = true;

    /** Notification method, initialized with default NotificationMethod */
    NotificationMethod _notificationMethod = new NotificationMethod() {
        // TODO
        // NOT YET CONCEPTUALIZED

        @Serial
        /** Serial number for serialization. */
	    private static final long serialVersionUID = 202208091753L;

        /** The default Notification Method currently does not perform any relevant action */
        public void deliverNotifications() {
            // EMPTY (for now)
        }
    }; 

    /** Notificaitons that Clients receive */
    public class Notification {
        public Notification() {}
        // TODO
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
        _type = new NormalType();
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
     * Returns this Client's balance in debt by checking each of his Terminal's
     * debt balance.
     * 
     * @return Client's debt balance
     * 
     */
    public Integer getClientDebtBalance() {
        // TODO explain and keep/remove for final version based on everyone's understanding 
        return _terminals.values().stream()
                                  .mapToInt(Terminal::getDebtBalance)//(t -> t.getDebtBalance())
                                  .sum();
    }

    /** 
     * Returns this Client's balance paid by checking each of his Terminal's
     * paid balance.
     * 
     * @reutrn Client's paid balance
     */
    public Integer getClientPaidBalance() {
        // TODO explain and keep/remove for final version based on everyone's understanding 
        return _terminals.values().stream() 
                                  .mapToInt(Terminal::getPaidBalance)//(t -> t.getPaidBalance())
                                  .sum();
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
    public void doNotify() {
        _notificationMethod.notify();
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
                getClientDebtBalance() + "|" +
                getClientPaidBalance(); 
    }
}
