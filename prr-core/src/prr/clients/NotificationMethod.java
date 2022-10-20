package prr.clients;

import java.io.Serial;
import java.io.Serializable;

/** Strategy to deliver Notifications to a Client */
public abstract class NotificationMethod implements Serializable {

    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    // TODO
    
    public void deliverNotifications() {
        // EMPTY
    };
}
