package material_design.soussi.com.events_tunisie.menu_choix.interfacesm_choix;

import android.view.View;

/**
 * Created by Soussi on 24/04/2015.
 */
public interface IViewObserver {
    /**
     * @param v View which is getting removed
     * @param position View position in adapter
     */
    void onViewRemovedFromParent(View v, int position);
}

