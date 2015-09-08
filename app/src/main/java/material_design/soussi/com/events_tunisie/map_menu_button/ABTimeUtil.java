package material_design.soussi.com.events_tunisie.map_menu_button;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by Soussi on 10/05/2015.
 */
public class ABTimeUtil {
    public static final String TAG = ABTimeUtil.class.getSimpleName();

    /**
     * ????????????????????/?/?/?????24903600 --> 06??55?03?600????
     *
     * @param millis   ????????
     * @param isWhole  ??????????/?/?/???
     * @param isFormat ?????????????true???????????false??????????
     * @return ??????????/?/?/????????24903600 --> 06??55?03?600????
     * @author wangjie
     */
    public static String millisToString(long millis, boolean isWhole, boolean isFormat) {
        String h = "";
        String m = "";
        String s = "";
        String mi = "";
        if (isWhole) {
            h = isFormat ? "00??" : "0??";
            m = isFormat ? "00?" : "0?";
            s = isFormat ? "00?" : "0?";
            mi = isFormat ? "00??" : "0??";
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += "??";
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += "?";
        }
        temp = temp % mper;

        if (temp / sper > 0) {
            if (isFormat) {
                s = temp / sper < 10 ? "0" + temp / sper : temp / sper + "";
            } else {
                s = temp / sper + "";
            }
            s += "?";
        }
        temp = temp % sper;
        mi = temp + "";

        if (isFormat) {
            if (temp < 100 && temp >= 10) {
                mi = "0" + temp;
            }
            if (temp < 10) {
                mi = "00" + temp;
            }
        }

        mi += "??";
        return h + m + s + mi;
    }

    /**
     * ????????????????????/?/?/?????24903600 --> 06??55?03???
     *
     * @param millis   ????????
     * @param isWhole  ??????????/?/?/???
     * @param isFormat ?????????????true???????????false??????????
     * @return ??????????/?/?/????????24903600 --> 06??55?03???
     * @author wangjie
     */
    public static String millisToStringMiddle(long millis, boolean isWhole, boolean isFormat) {
        return millisToStringMiddle(millis, isWhole, isFormat, "??", "??", "?");
    }
    public static String millisToStringMiddle(long millis, boolean isWhole, boolean isFormat, String hUnit, String mUnit, String sUnit) {
        String h = "";
        String m = "";
        String s = "";
        if (isWhole) {
            h = isFormat ? "00" + hUnit : "0" + hUnit;
            m = isFormat ? "00" + mUnit : "0" + mUnit;
            s = isFormat ? "00" + sUnit : "0" + sUnit;
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += hUnit;
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += mUnit;
        }
        temp = temp % mper;

        if (temp / sper > 0) {
            if (isFormat) {
                s = temp / sper < 10 ? "0" + temp / sper : temp / sper + "";
            } else {
                s = temp / sper + "";
            }
            s += sUnit;
        }
        return h + m + s;
    }

    /**
     * ????????????????????/?/?/?????24903600 --> 06??55????
     *
     * @param millis   ????????
     * @param isWhole  ??????????/??
     * @param isFormat ?????????????true???????????false??????????
     * @return ??????????/?/?/????????24903600 --> 06??55????
     * @author wangjie
     */
    public static String millisToStringShort(long millis, boolean isWhole, boolean isFormat) {
        String h = "";
        String m = "";
        if (isWhole) {
            h = isFormat ? "00??" : "0??";
            m = isFormat ? "00??" : "0??";
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += "??";
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += "??";
        }

        return h + m;
    }

    /**
     * ?????????????????yyyy-MM-dd HH:mm:ss?
     *
     * @param millis ??????????
     * @return ??????????"2013-02-19 11:48:31"??
     * @author wangjie
     */
    public static String millisToStringDate(long millis) {
        return millisToStringDate(millis, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * ????????????
     *
     * @param millis  ??????????
     * @param pattern ?????????????yyyy-MM-dd HH:mm:ss??
     * @return ????????
     * @author wangjie
     */
    public static String millisToStringDate(long millis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(millis));

    }

    /**
     * ?????????????????
     *
     * @param millis  ??????????
     * @param pattern ?????????????yyyy-MM-dd HH:mm:ss??
     * @return ????????yyyy_MM_dd_HH_mm_ss??
     * @author wangjie
     */
    public static String millisToStringFilename(long millis, String pattern) {
        String dateStr = millisToStringDate(millis, pattern);
        return dateStr.replaceAll("[- :]", "_");
    }

    /**
     * ?????????????????
     *
     * @param millis ??????????
     * @return ????????yyyy_MM_dd_HH_mm_ss??
     * @author wangjie
     */
    public static String millisToStringFilename(long millis) {
        String dateStr = millisToStringDate(millis, "yyyy-MM-dd HH:mm:ss");
        return dateStr.replaceAll("[- :]", "_");
    }


    public static long oneHourMillis = 60 * 60 * 1000; // ???????
    public static long oneDayMillis = 24 * oneHourMillis; // ??????
    public static long oneYearMillis = 365 * oneDayMillis; // ??????

    /**
     * ?????
     * 1???????????
     * ??1????????????
     * ???????????
     * ??????????
     * ??1??????
     *
     * @param millis
     * @return
     */
    public static String millisToLifeString(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (now - millis <= oneHourMillis && now - millis > 0l) { // ????
            String m = millisToStringShort(now - millis, false, false);
            return "".equals(m) ? "1???" : m + "?";
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // ????????????????????????????
            return "?? " + millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis) { // ???????????????????
            return "?? " + millisToStringDate(millis, "HH:mm");
        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"), "yyyy");
        if (millis > thisYearStart) { // ????????
            return millisToStringDate(millis, "MM?dd? HH:mm");
        }

        return millisToStringDate(millis, "yyyy?MM?dd? HH:mm");
    }

    /**
     * ?????
     * ????????????
     * ???????????
     * ??????????
     * ??1??????
     *
     * @param millis
     * @return
     */
    public static String millisToLifeString2(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (millis > todayStart + oneDayMillis && millis < todayStart + 2 * oneDayMillis) { // ??
            return "??" + millisToStringDate(millis, "HH:mm");
        }
        if (millis > todayStart + 2 * oneDayMillis && millis < todayStart + 3 * oneDayMillis) { // ??
            return "??" + millisToStringDate(millis, "HH:mm");
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // ????????????????????????????
            return "?? " + millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis && millis < todayStart) { // ???????????????????
            return "?? " + millisToStringDate(millis, "HH:mm");
        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"), "yyyy");
        if (millis > thisYearStart) { // ????????
            return millisToStringDate(millis, "MM?dd? HH:mm");
        }

        return millisToStringDate(millis, "yyyy?MM?dd? HH:mm");
    }

    /**
     * ?????
     * ????????????
     * ???????????
     * ??????????
     * ??1??????
     *
     * @param millis
     * @return
     */
    public static String millisToLifeString3(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"), "yyyy-MM-dd");

        if (millis > todayStart + oneDayMillis && millis < todayStart + 2 * oneDayMillis) { // ??
            return "??";
        }
        if (millis > todayStart + 2 * oneDayMillis && millis < todayStart + 3 * oneDayMillis) { // ??
            return "??";
        }

        if (millis >= todayStart && millis <= oneDayMillis + todayStart) { // ????????????????????????????
            return millisToStringDate(millis, "HH:mm");
        }

        if (millis > todayStart - oneDayMillis && millis < todayStart) { // ???????????????????
            return "?? ";
        }

        return millisToStringDate(millis, "MM?dd?");
    }

    /**
     * ?????????
     *
     * @param str
     * @param pattern
     * @return
     */
    public static long string2Millis(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        long millis = 0;
        try {
            millis = format.parse(str).getTime();
        } catch (ParseException e) {
            Logger.e(TAG, e);
        }
        return millis;
    }

    /**
     * ??????????
     *
     * @return
     */
    public static long getTodayStartMillis() {
//        String dateStr = millisToStringDate(System.currentTimeMillis(), "yyyy-MM-dd");
//        return string2Millis(dateStr, "yyyy-MM-dd");
        return getOneDayStartMillis(System.currentTimeMillis());
    }

    public static long getOneDayStartMillis(long millis) {
        String dateStr = millisToStringDate(millis, "yyyy-MM-dd");
        return string2Millis(dateStr, "yyyy-MM-dd");
    }


}