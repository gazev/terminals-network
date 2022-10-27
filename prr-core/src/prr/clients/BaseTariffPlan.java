package prr.clients;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public class BaseTariffPlan extends TariffPlan {
    public class TextTable {
        public static final Integer MIN_NORMAL_PRICE = 10;
        public static final Integer MED_NORMAL_PRICE = 16; 
        public static final Integer MAX_NORMAL_MULTIPLIER = 2;
        public static final Integer MIN_GOLD_PRICE = 10;
        public static final Integer MED_GOLD_PRICE = 10; 
        public static final Integer MAX_GOLD_MULTIPLIER = 2; 
        public static final Integer MIN_PLATINUM_PRICE = 0;
        public static final Integer MED_PLATINUM_PRICE = 4;
        public static final Integer MAX_PLATINUM_PRICE = 4;

    } 


    @Override
    public Double calculatePrice(TextCommunication c, ClientType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double calculatePrice(VoiceCommunication c, ClientType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double calculatePrice(VideoCommunication c, ClientType type) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
