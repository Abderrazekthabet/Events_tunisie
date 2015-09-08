package material_design.soussi.com.events_tunisie.week_view;

import java.util.Calendar;

/**
 * Created by Soussi on 07/05/2015.
 */
public interface DateTimeInterpreter {
    String interpretDate(Calendar date);
    String interpretTime(int hour);
}
