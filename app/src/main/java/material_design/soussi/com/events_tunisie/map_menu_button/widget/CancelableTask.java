package material_design.soussi.com.events_tunisie.map_menu_button.widget;

import java.util.Collection;

/**
 * Created by Soussi on 10/05/2015.
 */
public interface CancelableTask {

    public void addListener(Collection<CancelableTask> cancelableTaskCollection);

    public boolean cancel(boolean mayInterruptIfRunning);

    public void remove();
}