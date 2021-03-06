package material_design.soussi.com.events_tunisie.map_menu_button;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import material_design.soussi.com.events_tunisie.R;
import material_design.soussi.com.events_tunisie.map_menu_button.constants.RFABConstants;
import material_design.soussi.com.events_tunisie.map_menu_button.constants.RFABSize;
import material_design.soussi.com.events_tunisie.map_menu_button.listener.OnRapidFloatingActionListener;
import material_design.soussi.com.events_tunisie.map_menu_button.listener.OnRapidFloatingButtonSeparateListener;
import material_design.soussi.com.events_tunisie.map_menu_button.widget.CircleButtonDrawable;
import material_design.soussi.com.events_tunisie.map_menu_button.widget.CircleButtonProperties;

/**
 * Created by Soussi on 10/05/2015.
 */
public class RapidFloatingActionButton extends FrameLayout implements View.OnClickListener {
    /**
     * identificationCode
     */
    public static final String IDENTIFICATION_CODE_NONE = "";
    /**
     * identificationCode
     */
    private String identificationCode = IDENTIFICATION_CODE_NONE;

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(@NonNull String identificationCode) {
        this.identificationCode = identificationCode;
    }

    /**
     *
     */
    private Drawable buttonDrawable;
    /**
     * drawable
     */
    private static final int DEFAULT_BUTTON_DRAWABLE_RES_ID = R.drawable.rfab__drawable_rfab_default;
    /**
     *
     */
    private int buttonDrawableSize;
    /**
     * ImageView
     */
    private ImageView centerDrawableIv;

    public ImageView getCenterDrawableIv() {
        return centerDrawableIv;
    }

    /**
     *
     */
    private CircleButtonProperties rfabProperties = new CircleButtonProperties();

    /**
     *
     */
    private int normalColor;
    /**
     *
     */
    private int pressedColor;
    /**
     *
     */
    private ObjectAnimator mDrawableAnimator;
    /**
     *
     */
    private OvershootInterpolator mOvershootInterpolator;
    /**
     *
     */
    private OnRapidFloatingActionListener onRapidFloatingActionListener;

    public void setOnRapidFloatingActionListener(OnRapidFloatingActionListener onRapidFloatingActionListener) {
        this.onRapidFloatingActionListener = onRapidFloatingActionListener;
    }

    /**
     *
     */
    private OnRapidFloatingButtonSeparateListener onRapidFloatingButtonSeparateListener;

    public void setOnRapidFloatingButtonSeparateListener(OnRapidFloatingButtonSeparateListener onRapidFloatingButtonSeparateListener) {
        this.onRapidFloatingButtonSeparateListener = onRapidFloatingButtonSeparateListener;
    }

    public RapidFloatingActionButton(Context context) {
        super(context);
        initAfterConstructor();
    }

    public RapidFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parserAttrs(context, attrs, 0, 0);
        initAfterConstructor();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RapidFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parserAttrs(context, attrs, defStyleAttr, 0);
        initAfterConstructor();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RapidFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parserAttrs(context, attrs, defStyleAttr, defStyleRes);
        initAfterConstructor();
    }

    private void parserAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.RapidFloatingActionButton, defStyleAttr, defStyleRes);
        try {
            identificationCode = a.getString(R.styleable.RapidFloatingActionButton_rfab_identification_code);
            if (null == identificationCode) {
                identificationCode = IDENTIFICATION_CODE_NONE;
            }
            buttonDrawable = a.getDrawable(R.styleable.RapidFloatingActionButton_rfab_drawable);
            normalColor = a.getColor(R.styleable.RapidFloatingActionButton_rfab_color_normal, getContext().getResources().getColor(R.color.rfab__color_background_normal));
            pressedColor = a.getColor(R.styleable.RapidFloatingActionButton_rfab_color_pressed, getContext().getResources().getColor(R.color.rfab__color_background_pressed));
            int sizeCode = a.getInt(R.styleable.RapidFloatingActionButton_rfab_size, RFABSize.NORMAL.getCode());
            rfabProperties.setStandardSize(RFABSize.getRFABSizeByCode(sizeCode));
            rfabProperties.setShadowColor(a.getInt(R.styleable.RapidFloatingActionButton_rfab_shadow_color, Color.TRANSPARENT));
            rfabProperties.setShadowDx(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_dx, 0));
            rfabProperties.setShadowDy(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_dy, 0));
            rfabProperties.setShadowRadius(a.getDimensionPixelSize(R.styleable.RapidFloatingActionButton_rfab_shadow_radius, 0));
        } finally {
            a.recycle();
        }

    }

    private void initAfterConstructor() {
        this.setOnClickListener(this);
        //
        buttonDrawableSize = ABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAB_DRAWABLE_SIZE_DP);

        refreshRFABDisplay();
    }

    /**
     *
     */
    private void refreshRFABDisplay() {
        if (null == buttonDrawable) {
            buttonDrawable = ABImageProcess.getResourceDrawableBounded(getContext(), DEFAULT_BUTTON_DRAWABLE_RES_ID, buttonDrawableSize);
        }

        //
        CircleButtonDrawable normalDrawable = new CircleButtonDrawable(getContext(), rfabProperties, normalColor);
        ABViewUtil.setBackgroundDrawable(
                this,
                ABShape.selectorClickSimple(
                        normalDrawable,
                        new CircleButtonDrawable(getContext(), rfabProperties, pressedColor)
                )
        );

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            this.setLayerType(LAYER_TYPE_SOFTWARE, normalDrawable.getPaint());
        }

        if (null == centerDrawableIv) {
            this.removeAllViews();
            centerDrawableIv = new ImageView(getContext());
            this.addView(centerDrawableIv);
            LayoutParams lp = new LayoutParams(buttonDrawableSize, buttonDrawableSize);
            lp.gravity = Gravity.CENTER;
            centerDrawableIv.setLayoutParams(lp);
        }
        resetCenterImageView();
    }

    /**
     *
     */
    private void resetCenterImageView() {
        buttonDrawable.setBounds(0, 0, buttonDrawableSize, buttonDrawableSize);
        centerDrawableIv.setImageDrawable(buttonDrawable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int realSize = rfabProperties.getRealSizePx(getContext());
        setMeasuredDimension(realSize, realSize);
    }

    @Override
    public void onClick(View v) {
        if (null != onRapidFloatingActionListener) {
            onRapidFloatingActionListener.onRFABClick();
        }
        if (null != onRapidFloatingButtonSeparateListener) {
            onRapidFloatingButtonSeparateListener.onRFABClick();
        }
    }

    /**
     *
     *
     * @return
     */
    public CircleButtonProperties getRfabProperties() {
        return rfabProperties;
    }

    /**
     *
     *
     * @param buttonDrawable
     */
    public void setButtonDrawable(Drawable buttonDrawable) {
        this.buttonDrawable = buttonDrawable;
    }

    /**
     *
     *
     * @param normalColor
     */
    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    /**
     *
     *
     * @param pressedColor
     */
    public void setPressedColor(int pressedColor) {
        this.pressedColor = pressedColor;
    }

    /**
     *
     */
    public void build() {
        refreshRFABDisplay();
    }

    /**
     *
     *
     * @param animatorSet
     */
    public void onExpandAnimator(AnimatorSet animatorSet) {
        ensureDrawableAnimator();
        ensureDrawableInterpolator();
        mDrawableAnimator.cancel();
        mDrawableAnimator.setTarget(centerDrawableIv);
        mDrawableAnimator.setFloatValues(0, -45f);
        mDrawableAnimator.setPropertyName("rotation");
        mDrawableAnimator.setInterpolator(mOvershootInterpolator);
        animatorSet.playTogether(mDrawableAnimator);
    }

    public void onCollapseAnimator(AnimatorSet animatorSet) {
        ensureDrawableAnimator();
        ensureDrawableInterpolator();
        mDrawableAnimator.cancel();
        mDrawableAnimator.setTarget(centerDrawableIv);
        mDrawableAnimator.setFloatValues(-45f, 0);
        mDrawableAnimator.setPropertyName("rotation");
        mDrawableAnimator.setInterpolator(mOvershootInterpolator);
        animatorSet.playTogether(mDrawableAnimator);
    }

    /**

     */
    private void ensureDrawableAnimator() {
        if (null == mDrawableAnimator) {
            mDrawableAnimator = new ObjectAnimator();
        }
    }

    /**
     *
     */
    private void ensureDrawableInterpolator() {
        if (null == mOvershootInterpolator) {
            mOvershootInterpolator = new OvershootInterpolator();
        }
    }

}

