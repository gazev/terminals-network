package prr.clients;
    
public abstract class TariffPlanTable {
    public abstract double calculateText(NormalType type, Integer length);
    public abstract double calculateText(GoldType type, Integer length);
    public abstract double calculateText(PlatinumType type, Integer length);

    public abstract double calculateVoice(NormalType type, Integer length);
    public abstract double calculateVoice(GoldType type, Integer length);
    public abstract double calculateVoice(PlatinumType type, Integer length);
    
    public abstract double calculateVideo(NormalType type, Integer length);
    public abstract double calculateVideo(GoldType type, Integer length);
    public abstract double calculateVideo(PlatinumType type, Integer length);
}
