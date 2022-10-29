package prr.clients;


import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public interface TariffPlan {
    public abstract Double calculatePrice(TextCommunication c, ClientType type);
    public abstract Double calculatePrice(VideoCommunication c, ClientType type);
    public abstract Double calculatePrice(VoiceCommunication c, ClientType type);
}
