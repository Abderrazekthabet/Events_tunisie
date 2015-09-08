package material_design.soussi.com.events_tunisie.recyclye_animation.adaptateur;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
/**
 * Created by Soussi on 07/07/2015.
 */
public class SlideInLeftAnimationAdapter extends AnimationAdapter {

    public SlideInLeftAnimationAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    protected Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
        };
    }
}