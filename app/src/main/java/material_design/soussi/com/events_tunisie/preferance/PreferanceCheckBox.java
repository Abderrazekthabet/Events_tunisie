package material_design.soussi.com.events_tunisie.preferance;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static material_design.soussi.com.events_tunisie.preferance.Typefaces.getRobotoRegular;

import material_design.soussi.com.events_tunisie.R;

/**
 * Created by Soussi on 05/05/2015.
 */
public class PreferanceCheckBox extends CheckBoxPreference{
    private int iconResId;
    private Drawable icon;
    //*************************************************************************************************
    private Context mContext;
    // private int mLayoutResId = R.layout.preference;
    private int mWidgetLayoutResId = R.layout.preference_widget_checkbox;

    private boolean mShouldDisableView = true;

    private CharSequence mSummaryOn;
    private CharSequence mSummaryOff;

    private boolean mSendAccessibilityEventViewClickedType;

    private AccessibilityManager mAccessibilityManager;
    //*************************************************************************************************
    public PreferanceCheckBox (Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public PreferanceCheckBox (Context context, AttributeSet attrs) {
        super(context, attrs);
        //this(context, attrs, android.R.attr.checkBoxPreferenceStyle);
        init(context, attrs, 0, 0);
    }

    public PreferanceCheckBox (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //************************************************************************************************
        mContext = context;
        mSummaryOn = getSummaryOn();
        mSummaryOff = getSummaryOff();
        mAccessibilityManager = (AccessibilityManager) mContext
                .getSystemService(Service.ACCESSIBILITY_SERVICE);
        //************************************************************************************************
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PreferanceCheckBox (Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, new int[] { android.R.attr.icon }, defStyleAttr,
                        defStyleRes);
        iconResId = typedArray.getResourceId(0, 0);
        typedArray.recycle();
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.mp_preferance_checkbox, parent, false);

        ViewGroup widgetFrame = (ViewGroup) layout.findViewById(R.id.widget_frame1);
        int widgetLayoutResId = getWidgetLayoutResource();
        if (widgetLayoutResId != 0) {
            layoutInflater.inflate(widgetLayoutResId, widgetFrame);
        }
        widgetFrame.setVisibility(widgetLayoutResId != 0 ? VISIBLE : GONE);

        return layout;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        TextView titleView = (TextView) view.findViewById(R.id.title1);
        CharSequence title = getTitle();
        titleView.setText(title);
        titleView.setVisibility(!isEmpty(title) ? VISIBLE : GONE);
        titleView.setTypeface(getRobotoRegular(getContext()));

        TextView summaryView = (TextView) view.findViewById(R.id.summary1);
        CharSequence summary = getSummary();
        summaryView.setText(summary);
        summaryView.setVisibility(!isEmpty(summary) ? VISIBLE : GONE);
        summaryView.setTypeface(getRobotoRegular(getContext()));

        ImageView imageView = (ImageView) view.findViewById(R.id.icon1);
        if (icon == null && iconResId > 0) {
            icon = getContext().getResources().getDrawable(iconResId);
        }
        imageView.setImageDrawable(icon);
        imageView.setVisibility(icon != null ? VISIBLE : GONE);

        View imageFrame = view.findViewById(R.id.icon_frame1);
        imageFrame.setVisibility(icon != null ? VISIBLE : GONE);

       /* CheckBox box =(CheckBox)view.findViewById(R.id.checkBox1);
        if (box != null && box instanceof Checkable) {
            ((Checkable) box).setChecked(mChecked);
        }*/
        if (mShouldDisableView) {
            setEnabledStateOnViews(view, isEnabled());
        }

        View checkboxView = view.findViewById(R.id.checkbox);
        if (checkboxView != null && checkboxView instanceof Checkable) {
            ((Checkable) checkboxView).setChecked(isChecked());
            material_design.soussi.com.events_tunisie.preferance.CheckBox switchButton = (material_design.soussi.com.events_tunisie.preferance.CheckBox) checkboxView;
            switchButton
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            // TODO Auto-generated method stub
                            mSendAccessibilityEventViewClickedType = true;
                            if (!callChangeListener(isChecked)) {
                                return;
                            }
                            setChecked(isChecked);
                        }
                    });

            if (mSendAccessibilityEventViewClickedType
                    && mAccessibilityManager.isEnabled()
                    && checkboxView.isEnabled()) {
                mSendAccessibilityEventViewClickedType = false;

                int eventType = AccessibilityEvent.TYPE_VIEW_CLICKED;
                checkboxView.sendAccessibilityEventUnchecked(AccessibilityEvent
                        .obtain(eventType));
            }

        }


//--------------------------------------------------------------------------------------------------

    }
    /**
     * Makes sure the view (and any children) get the enabled state changed.
     */
    private void setEnabledStateOnViews(View v, boolean enabled) {
        v.setEnabled(enabled);

        if (v instanceof ViewGroup) {
            final ViewGroup vg = (ViewGroup) v;
            for (int i = vg.getChildCount() - 1; i >= 0; i--) {
                setEnabledStateOnViews(vg.getChildAt(i), enabled);
            }
        }
    }

    @Override
    public void setIcon(int iconResId) {
        super.setIcon(iconResId);
        this.iconResId = iconResId;
    }

    @Override
    public void setIcon(Drawable icon) {
        super.setIcon(icon);
        this.icon = icon;
    }
}
