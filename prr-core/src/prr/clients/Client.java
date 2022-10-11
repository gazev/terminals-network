package prr.clients;

import java.util.ArrayList;

import prr.terminals.Terminal;

public class Client {
    /** Client's key */
    String _key;

    /** Client's name */
    String _name;

    /** Client's tax ID */
    int _taxId;

    /** Client's type */
    ClientType _type;

    /** Client's notifications not yet delivered */
    ArrayList<Notification> _notificationsLog = new ArrayList<>();

    /** Notification method, initialized with default NotificationMethod */
    NotificationMethod _notificationMethod = new NotificationMethod() {
        public void notify(ArrayList<Notification> notificationsLog) {
            System.out.println("Hi");
        }
    }; 
        
    /** Flag to determine if Client should be notified */
    boolean _notificationsOn = true;
    
    public Client(String key, String name, int taxId) {
        _key = key;
        _name = name;
        _taxId = taxId;
    }

    public String getKey() {
        return _key;
    }

    public String getName() {
        return _name;
    }

    public int getTaxId() {
        return _taxId;
    }

    public boolean getNotificationsOn() {
        return _notificationsOn;
    }

    public ClientType getClientType() {
        return _type;
    }

    public void setNotificationsOn(boolean notificationsOn) {
        _notificationsOn = notificationsOn;
    }

    public void setClientType(ClientType type) {
        _type = type;
    }

    public void doNotify() {
        _notificationMethod.notify();
    }

    public void addTerminal(Terminal t) {
        // TODO
    }
}
