package material_design.soussi.com.events_tunisie.toast.utils;
import android.os.Parcelable;
import android.view.View;

import material_design.soussi.com.events_tunisie.toast.SuperToast;

/**
 * Created by Soussi on 24/04/2015.
 */
@SuppressWarnings("UnusedParameters")
public class OnClickWrapper implements SuperToast.OnClickListener {

    private final String mTag;
    private final SuperToast.OnClickListener mOnClickListener;
    private Parcelable mToken;

    /**
     *  Creates an OnClickWrapper.
     *
     *  @param tag {@link CharSequence} Must be unique to this listener
     *  @param onClickListener {@link //com.github.johnpersano.supertoasts.SuperToast.OnClickListener}
     */
    public OnClickWrapper(String tag, SuperToast.OnClickListener onClickListener) {

        this.mTag = tag;
        this.mOnClickListener = onClickListener;

    }

    /**
     *  Returns the tag associated with this OnClickWrapper. This is used to
     *  reattach {@link //com.github.johnpersano.supertoasts.SuperToast.OnClickListener}.
     *
     *  @return {@link String}
     */
    public String getTag() {

        return mTag;

    }

    /**
     *  This is used during SuperActivityToast/SuperCardToast recreation and should
     *  never be called by the developer.
     */
    public void setToken(Parcelable token) {

        this.mToken = token;

    }

    @Override
    public void onClick(View view, Parcelable token) {

        mOnClickListener.onClick(view, mToken);

    }

}