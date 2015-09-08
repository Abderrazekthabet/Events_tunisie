package material_design.soussi.com.events_tunisie.menu_choix.interfacesm_choix;

import android.view.View;

/**
 * Created by Soussi on 24/04/2015.
 */
public interface IRemovableItemsAdapterComponent {
    /**
     * Called when item is removed from component by user clicking on remove button
     * @return true, if you removed item from adapter manually in this step
     */
    boolean onItemRemove(int position, View view, Object item);
}