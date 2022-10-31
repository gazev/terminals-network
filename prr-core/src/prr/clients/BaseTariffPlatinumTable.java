package prr.clients;

import java.io.Serial;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public class BaseTariffPlatinumTable extends TariffTable {
    
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    @Override
    public Double calculatePrice(TextCommunication c) {
        if(c.getUnits() < 50) {
            return 0.0;
        } else if (c.getUnits() < 100) {
            return 4.0;
        } else {
            return 4.0;
        }
    }

    @Override
    public Double calculatePrice(VoiceCommunication c) {
        return 10.0*c.getUnits();
    }

    @Override
    public Double calculatePrice(VideoCommunication c) {
        return 10.0*c.getUnits();
    }
    
}