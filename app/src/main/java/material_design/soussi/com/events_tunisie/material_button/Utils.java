package material_design.soussi.com.events_tunisie.material_button;

import android.content.res.Resources;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
/**
 * Created by Soussi on 23/04/2015.
 */
public class Utils {


    /**
     * Convert Dp to Pixel
     *
     */
    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    /**
     * @param value
     * @return float
     */
    public static float dipOrDpToFloat(String value) {
        if (value.indexOf("dp") != -1) {
            value = value.replace("dp", "");
        }
        else {
            value = value.replace("dip", "");
        }
        return Float.parseFloat(value);
    }


    /**

     * @param myView
     * @return
     */
    public static int getRelativeTop(View myView) {
        Rect bounds = new Rect();
        myView.getGlobalVisibleRect(bounds);
        return bounds.top;
    }

    public static int getRelativeLeft(View myView) {
//	    if (myView.getParent() == myView.getRootView())
        if(myView.getId() == android.R.id.content)
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

}
