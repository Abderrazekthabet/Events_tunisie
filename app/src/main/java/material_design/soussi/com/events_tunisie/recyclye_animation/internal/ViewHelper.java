package material_design.soussi.com.events_tunisie.recyclye_animation.internal;

import android.view.View;

/**
 * Created by Soussi on 07/07/2015.
 */
public final class ViewHelper {

    public static void clear(View v) {
        v.setAlpha(1f);
        v.setRotation(0f);
        v.setRotationX(0f);
        v.setRotationY(0f);
        v.setScaleX(1f);
        v.setScaleY(1f);
        v.setTranslationX(0f);
        v.setTranslationY(0f);
    }
}
