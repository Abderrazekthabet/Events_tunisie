package material_design.soussi.com.events_tunisie.map_menu_button.labellist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.List;

import material_design.soussi.com.events_tunisie.R;
import material_design.soussi.com.events_tunisie.map_menu_button.ABImageProcess;
import material_design.soussi.com.events_tunisie.map_menu_button.ABShape;
import material_design.soussi.com.events_tunisie.map_menu_button.ABTextUtil;
import material_design.soussi.com.events_tunisie.map_menu_button.ABViewUtil;
import material_design.soussi.com.events_tunisie.map_menu_button.RapidFloatingActionContent;
import material_design.soussi.com.events_tunisie.map_menu_button.constants.RFABConstants;
import material_design.soussi.com.events_tunisie.map_menu_button.constants.RFABSize;
import material_design.soussi.com.events_tunisie.map_menu_button.widget.CircleButtonDrawable;
import material_design.soussi.com.events_tunisie.map_menu_button.widget.CircleButtonProperties;

/**
 * Created by Soussi on 10/05/2015.
 */
public class RapidFloatingActionContentLabelList extends RapidFloatingActionContent implements View.OnClickListener {
    public interface OnRapidFloatingActionContentListener<T> {
        void onRFACItemLabelClick(int position, RFACLabelItem<T> item);

        void onRFACItemIconClick(int position, RFACLabelItem<T> item);
    }

    private OnRapidFloatingActionContentListener onRapidFloatingActionContentListener;

    public void setOnRapidFloatingActionContentListener(OnRapidFloatingActionContentListener onRapidFloatingActionContentListener) {
        this.onRapidFloatingActionContentListener = onRapidFloatingActionContentListener;
    }

    public RapidFloatingActionContentLabelList(Context context) {
        super(context);
    }

    public RapidFloatingActionContentLabelList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RapidFloatingActionContentLabelList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RapidFloatingActionContentLabelList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int rfacItemDrawableSizePx;

    private LinearLayout contentView;
    private List<RFACLabelItem> items;
    private int iconShadowRadius;
    private int iconShadowColor;
    private int iconShadowDx;
    private int iconShadowDy;

    @Override
    protected void initAfterConstructor() {
        rfacItemDrawableSizePx = ABTextUtil.dip2px(getContext(), RFABConstants.SIZE.RFAC_ITEM_DRAWABLE_SIZE_DP);

        contentView = new LinearLayout(getContext());
        contentView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setOrientation(LinearLayout.VERTICAL);
        setRootView(contentView);
    }

    @Override
    protected void initAfterRFABHelperBuild() {
        refreshItems();
    }

    public List<RFACLabelItem> getItems() {
        return items;
    }

    public RapidFloatingActionContentLabelList setItems(List<RFACLabelItem> items) {
        if (!ABTextUtil.isEmpty(items)) {
            this.items = items;
        }
        return this;
    }

    private void refreshItems() {
        if (ABTextUtil.isEmpty(items)) {
            throw new RuntimeException(this.getClass().getSimpleName() + "[items] can not be empty!");
        }
        contentView.removeAllViews();
        for (int i = 0, size = items.size(); i < size; i++) {
            RFACLabelItem item = items.get(i);
            //
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.rfab__content_label_list_item, null);
            View rootView = ABViewUtil.obtainView(itemView, R.id.rfab__content_label_list_root_view);

            TextView labelTv = ABViewUtil.obtainView(itemView, R.id.rfab__content_label_list_label_tv);
            ImageView iconIv = ABViewUtil.obtainView(itemView, R.id.rfab__content_label_list_icon_iv);
            rootView.setOnClickListener(this);
            labelTv.setOnClickListener(this);
            iconIv.setOnClickListener(this);
            rootView.setTag(R.id.rfab__id_content_label_list_item_position, i);
            labelTv.setTag(R.id.rfab__id_content_label_list_item_position, i);
            iconIv.setTag(R.id.rfab__id_content_label_list_item_position, i);

            //
            CircleButtonProperties circleButtonProperties = new CircleButtonProperties()
                    .setStandardSize(RFABSize.MINI)
                    .setShadowColor(iconShadowColor)
                    .setShadowRadius(iconShadowRadius)
                    .setShadowDx(iconShadowDx)
                    .setShadowDy(iconShadowDy);

            //
            int shadowOffsetHalf = circleButtonProperties.getShadowOffsetHalf();
            int minPadding = ABTextUtil.dip2px(getContext(), 8);
            if (shadowOffsetHalf < minPadding) {
                int deltaPadding = minPadding - shadowOffsetHalf;
                rootView.setPadding(0, deltaPadding, 0, deltaPadding);
            }

            //
            int realItemSize = circleButtonProperties.getRealSizePx(getContext());
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) iconIv.getLayoutParams();
            if (null == lp) {
                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            int rfabRealSize = onRapidFloatingActionListener.obtainRFAButton().getRfabProperties().getRealSizePx(getContext());
            lp.rightMargin = (rfabRealSize - realItemSize) / 2;
            lp.width = realItemSize;
            lp.height = realItemSize;
            iconIv.setLayoutParams(lp);

            Integer normalColor = item.getIconNormalColor();
            Integer pressedColor = item.getIconPressedColor();

            CircleButtonDrawable rfacNormalDrawable = new CircleButtonDrawable(getContext(), circleButtonProperties,
                    null == normalColor ? getResources().getColor(R.color.rfab__color_background_normal) : normalColor);
            CircleButtonDrawable rfacPressedDrawable = new CircleButtonDrawable(getContext(), circleButtonProperties,
                    null == pressedColor ? getResources().getColor(R.color.rfab__color_background_pressed) : pressedColor);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                iconIv.setLayerType(LAYER_TYPE_SOFTWARE, rfacNormalDrawable.getPaint());
            }

            ABViewUtil.setBackgroundDrawable(iconIv, ABShape.selectorClickSimple(rfacNormalDrawable, rfacPressedDrawable));
            //
            int padding = ABTextUtil.dip2px(getContext(), 8) + shadowOffsetHalf;
            iconIv.setPadding(padding, padding, padding, padding);

            //
            String label = item.getLabel();
            if (ABTextUtil.isEmpty(label)) {
                labelTv.setVisibility(GONE);
            } else {
                if (item.isLabelTextBold()) {
                    labelTv.getPaint().setFakeBoldText(true);
                }
                labelTv.setVisibility(VISIBLE);
                labelTv.setText(label);
                Drawable bgDrawable = item.getLabelBackgroundDrawable();
                if (null != bgDrawable) {
                    ABViewUtil.setBackgroundDrawable(labelTv, bgDrawable);
                }
                Integer labelColor = item.getLabelColor();
                if (null != labelColor) {
                    labelTv.setTextColor(labelColor);
                }
                Integer labelSize = item.getLabelSizeSp();
                if (null != labelSize) {
                    labelTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelSize);
                }

            }
            int resId = item.getResId();
            if (resId > 0) {
                iconIv.setVisibility(VISIBLE);
                iconIv.setImageResource(resId);
                item.setDrawable(ABImageProcess.getResourceDrawableBounded(getContext(), resId, rfacItemDrawableSizePx));

                Drawable drawable = item.getDrawable();
                if (null == drawable) {
                    drawable = ABImageProcess.getResourceDrawableBounded(getContext(), resId, rfacItemDrawableSizePx);
                    item.setDrawable(drawable);
                }
                iconIv.setImageDrawable(drawable);

            } else {
                iconIv.setVisibility(GONE);
            }

            contentView.addView(itemView);
        }

    }

    @Override
    protected void initialContentViews(View rootView) {
    }


    @Override
    public void onClick(View v) {
        Integer position;
        if (null == onRapidFloatingActionContentListener
                ||
                null == (position = (Integer) v.getTag(R.id.rfab__id_content_label_list_item_position))) {
            return;
        }
        int i = v.getId();
        if (i == R.id.rfab__content_label_list_label_tv) {
            onRapidFloatingActionContentListener.onRFACItemLabelClick(position, items.get(position));
        } else if (i == R.id.rfab__content_label_list_icon_iv) {
            onRapidFloatingActionContentListener.onRFACItemIconClick(position, items.get(position));
        } else if (i == R.id.rfab__content_label_list_root_view) {
            onRapidFloatingActionListener.collapseContent();
        }
    }

    public RapidFloatingActionContentLabelList setIconShadowRadius(int iconShadowRadius) {
        this.iconShadowRadius = iconShadowRadius;
        return this;
    }

    public RapidFloatingActionContentLabelList setIconShadowColor(int iconShadowColor) {
        this.iconShadowColor = iconShadowColor;
        return this;
    }

    public RapidFloatingActionContentLabelList setIconShadowDx(int iconShadowDx) {
        this.iconShadowDx = iconShadowDx;
        return this;
    }

    public RapidFloatingActionContentLabelList setIconShadowDy(int iconShadowDy) {
        this.iconShadowDy = iconShadowDy;
        return this;
    }

    /**
     * **********************  ************************
     */
    private OvershootInterpolator mOvershootInterpolator = new OvershootInterpolator();

    @Override
    public void onExpandAnimator(AnimatorSet animatorSet) {
        int count = contentView.getChildCount();
        for (int i = 0; i < count; i++) {
            View rootView = contentView.getChildAt(i);
            ImageView iconIv = ABViewUtil.obtainView(rootView, R.id.rfab__content_label_list_icon_iv);
            if (null == iconIv) {
                return;
            }
            ObjectAnimator animator = new ObjectAnimator();
            animator.setTarget(iconIv);
            animator.setFloatValues(45f, 0);
            animator.setPropertyName("rotation");
            animator.setInterpolator(mOvershootInterpolator);
            animator.setStartDelay((count * i) * 20);
            animatorSet.playTogether(animator);
        }
    }

    @Override
    public void onCollapseAnimator(AnimatorSet animatorSet) {
        int count = contentView.getChildCount();
        for (int i = 0; i < count; i++) {
            View rootView = contentView.getChildAt(i);
            ImageView iconIv = ABViewUtil.obtainView(rootView, R.id.rfab__content_label_list_icon_iv);
            if (null == iconIv) {
                return;
            }
            ObjectAnimator animator = new ObjectAnimator();
            animator.setTarget(iconIv);
            animator.setFloatValues(0, 45f);
            animator.setPropertyName("rotation");
            animator.setInterpolator(mOvershootInterpolator);
            animator.setStartDelay((count * i) * 20);
            animatorSet.playTogether(animator);
        }
    }

}
