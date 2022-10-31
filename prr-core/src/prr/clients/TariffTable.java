package prr.clients;

import java.io.Serial;
import java.io.Serializable;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public abstract class TariffTable implements Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
    
    public abstract Double calculatePrice(TextCommunication c);
    public abstract Double calculatePrice(VideoCommunication c);
    public abstract Double calculatePrice(VoiceCommunication c);
}
