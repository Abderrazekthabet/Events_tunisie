package material_design.soussi.com.events_tunisie.menu_toolbar.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import material_design.soussi.com.events_tunisie.menu_toolbar.App;

/**
 * Created by Soussi on 12/07/2015.
 */
public class CanaroTextView extends TextView {
    public CanaroTextView(Context context) {
        this(context, null);
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.canaroExtraBold);
    }

}