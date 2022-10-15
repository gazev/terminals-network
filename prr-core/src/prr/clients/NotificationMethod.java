package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public abstract class NotificationMethod implements Serializable {

    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public void deliverNotifications() {
        // EMPTY
    };
}
