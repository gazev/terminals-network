package prr.clients;

import java.io.Serial;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public class BaseTariffGoldTable extends TariffTable {

    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    @Override
    public Double calculatePrice(TextCommunication c) {
        if(c.getUnits() < 50) {
            return 10.0;
        } else if (c.getUnits() < 100) {
            return 10.0;
        } else {
            return 2.0*c.getUnits();
        }
    }

    @Override
    public Double calculatePrice(VoiceCommunication c) {
        return 10.0*c.getUnits();
    }

    @Override
    public Double calculatePrice(VideoCommunication c) {
        return 20.0*c.getUnits();
    }   

    
}
