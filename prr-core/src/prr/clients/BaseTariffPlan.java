package prr.clients;

import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public class BaseTariffPlan implements TariffPlan {
    public Double normalTextPrice(Communication c) {
        if(c.getUnits() < 50) {
            return 10.0;
        } else if(c.getUnits() < 100) {
            return 16.0;
        } else {
            return 2.0*c.getUnits();
        }
    } 

    public Double goldTextPrice(Communication c) {
        if(c.getUnits() < 100) {
           return 10.0;
        } else {
            return 2.0*c.getUnits();
        }
    } 

    public Double platinumTextPrice(Communication c) {
        if(c.getUnits() < 50) {
            return 0.0;
        } else {
            return 4.0;
        }
    }

    @Override
    public Double calculatePrice(TextCommunication c, ClientType type) {
        return switch(type.toString()) {
            case "NORMAL"   -> normalTextPrice(c);
            case "GOLD"     -> goldTextPrice(c);
            case "PLATINUM" -> platinumTextPrice(c);
            default -> 0.0;
        };
    }

    @Override
    public Double calculatePrice(VideoCommunication c, ClientType type) {
        return switch(type.toString()) {
            case "NORMAL"   -> 30.0*c.getUnits();
            case "GOLD"     -> 20.0*c.getUnits();
            case "PLATINUM" -> 10.0*c.getUnits();
            default -> 0.0;
        };
    }

    @Override
    public Double calculatePrice(VoiceCommunication c, ClientType type) {
        return switch(type.toString()) {
            case "NORMAL"   -> 20.0*c.getUnits();
            case "GOLD"     -> 10.0*c.getUnits();
            case "PLATINUM" -> 10.0*c.getUnits();
            default -> 0.0;
        };
    }
    
}
