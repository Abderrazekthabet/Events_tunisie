package material_design.soussi.com.events_tunisie.map_menu_button.constants;

/**
 * Created by Soussi on 10/05/2015.
 */
public enum RFABSize {
    NORMAL(0, RFABConstants.SIZE.RFAB_NORMAL_SIZE_DP),
    MINI(1, RFABConstants.SIZE.RFAB_MINI_SIZE_DP);
    int code;
    int dpSize;

    RFABSize(int code, int dpSize) {
        this.code = code;
        this.dpSize = dpSize;
    }

    public static RFABSize getRFABSizeByCode(int code) {
        for (RFABSize rfabSize : RFABSize.values()) {
            if (code == rfabSize.code) {
                return rfabSize;
            }
        }
        return NORMAL;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDpSize() {
        return dpSize;
    }

    public void setDpSize(int dpSize) {
        this.dpSize = dpSize;
    }
}
