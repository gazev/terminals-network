package prr.clients;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public abstract class TariffPlan {
    public abstract class TariffTable {
        public abstract class TextTable {
            public abstract Double textPrice(NormalType type);
            public abstract Double textPrice(GoldType type);
            public abstract Double textPrice(PlatinumType type);
        }

    };

    public abstract Double calculatePrice(TextCommunication c, ClientType type);
    public abstract Double calculatePrice(VoiceCommunication c, ClientType type);
    public abstract Double calculatePrice(VideoCommunication c, ClientType type);
}
