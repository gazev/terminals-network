package prr.clients;

import java.io.Serial;
import java.io.Serializable;

public abstract class TariffPlan implements Serializable {
    @Serial
    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    protected TariffTable _normalTable;
    protected TariffTable _goldTable;
    protected TariffTable _platinumTable;

    public TariffTable getNormalTable() {
        return _normalTable;
    }

    public TariffTable getGoldTable() {
        return _goldTable;
    }

    public TariffTable getPlatinumTable() {
        return _platinumTable;
    }
}
