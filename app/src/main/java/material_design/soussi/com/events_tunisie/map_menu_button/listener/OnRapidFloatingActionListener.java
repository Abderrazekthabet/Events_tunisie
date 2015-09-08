package material_design.soussi.com.events_tunisie.map_menu_button.listener;
import com.nineoldandroids.animation.AnimatorSet;

import material_design.soussi.com.events_tunisie.map_menu_button.RapidFloatingActionButton;
import material_design.soussi.com.events_tunisie.map_menu_button.RapidFloatingActionContent;
import material_design.soussi.com.events_tunisie.map_menu_button.RapidFloatingActionLayout;

/**
 * Created by Soussi on 10/05/2015.
 */
public interface OnRapidFloatingActionListener {
    /**
     *
     */
    void onRFABClick();

    /**
     *
     */
    void toggleContent();

    /**
     *
     */
    void expandContent();

    /**
     *
     */
    void collapseContent();

    /**
     *
     *
     * @return
     */
    RapidFloatingActionLayout obtainRFALayout();

    /**
     *
     *
     * @return
     */
    RapidFloatingActionButton obtainRFAButton();

    /**
     *
     *
     * @return
     */
    RapidFloatingActionContent obtainRFAContent();

    /**
     *
     *
     * @param animatorSet
     */
    void onExpandAnimator(AnimatorSet animatorSet);

    /**
     *
     *
     * @param animatorSet
     */
    void onCollapseAnimator(AnimatorSet animatorSet);

}

