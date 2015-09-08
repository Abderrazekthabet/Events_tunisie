package material_design.soussi.com.events_tunisie.recyclye_animation;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
/**
 * Created by Soussi on 07/07/2015.
 */
public class FlipInTopXAnimator extends BaseItemAnimator {

    @Override
    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .rotationX(90)
                .setDuration(getRemoveDuration())
                .setListener(new DefaultRemoveVpaListener(holder))
                .start();
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void preAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setRotationX(holder.itemView, 90);
    }

    @Override
    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        ViewCompat.animate(holder.itemView)
                .rotationX(0)
                .setDuration(getAddDuration())
                .setListener(new DefaultAddVpaListener(holder)).start();
        mAddAnimations.add(holder);
    }
}
