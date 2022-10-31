package prr.clients;

public class BaseTariffPlan extends TariffPlan {
    public BaseTariffPlan() {
        _normalTable = new BaseTariffNormalTable();
        _goldTable = new BaseTariffGoldTable();
        _platinumTable = new BaseTariffPlatinumTable();
    }
}
