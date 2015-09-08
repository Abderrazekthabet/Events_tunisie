package material_design.soussi.com.events_tunisie.toast.utils;
import android.view.View;

import material_design.soussi.com.events_tunisie.toast.SuperToast;

/**
 * Created by Soussi on 24/04/2015.
 */
public class OnDismissWrapper implements SuperToast.OnDismissListener {

    private final String mTag;
    private final SuperToast.OnDismissListener mOnDismissListener;

    /**
     *  Creates an OnClickWrapper.
     *
     *  @param tag {@link CharSequence} Must be unique to this listener
     *  @param onDismissListener {@link //com.github.johnpersano.supertoasts.SuperToast.OnDismissListener}
     */
    public OnDismissWrapper(String tag, SuperToast.OnDismissListener onDismissListener) {

        this.mTag = tag;
        this.mOnDismissListener = onDismissListener;

    }

    /**
     *  Returns the tag associated with this OnDismissWrapper. This is used to
     *  reattach {@link //com.github.johnpersano.supertoasts.SuperToast.OnDismissListener}s.
     *
     *  @return {@link String}
     */
    public String getTag() {

        return mTag;

    }

    @Override
    public void onDismiss(View view) {

        mOnDismissListener.onDismiss(view);

    }

}