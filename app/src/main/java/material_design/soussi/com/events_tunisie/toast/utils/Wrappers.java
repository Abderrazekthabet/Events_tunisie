package material_design.soussi.com.events_tunisie.toast.utils;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Soussi on 24/04/2015.
 */
public class Wrappers {

    private List<OnClickWrapper> onClickWrapperList = new ArrayList<OnClickWrapper>();

    private List<OnDismissWrapper> onDismissWrapperList = new ArrayList<OnDismissWrapper>();

    /**
     * Adds an onclickwrapper to a list that will be reattached on orientation change.
     *
     * @param onClickWrapper {@link OnClickWrapper}
     */
    public void add(OnClickWrapper onClickWrapper){

        onClickWrapperList.add(onClickWrapper);

    }

    /**
     * Adds an ondismisswrapper to a list that will be reattached on orientation change.
     *
     * @param onDismissWrapper {@link OnDismissWrapper}
     */
    public void add(OnDismissWrapper onDismissWrapper){

        onDismissWrapperList.add(onDismissWrapper);

    }

    /**
     * Used during recreation of SuperActivityToasts/SuperCardToasts.
     */
    public List<OnClickWrapper> getOnClickWrappers() {

        return onClickWrapperList;

    }

    /**
     * Used during recreation of SuperActivityToasts/SuperCardToasts.
     */
    public List<OnDismissWrapper> getOnDismissWrappers() {

        return onDismissWrapperList;

    }

}

