package material_design.soussi.com.events_tunisie.material_button;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * Created by Soussi on 23/04/2015.
 */
public abstract class Button extends RippleView {

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitAttributes(attrs);
    }

    @Override
    protected void onInitDefaultValues() {
        backgroundColor = Color.parseColor("#2196f3");//
        ///beforeBackground = backgroundColor;// error
    }

    protected void onInitAttributes(AttributeSet attrs) {
        setAttributes(attrs);
    }

    // ### RIPPLE EFFECT ###

    /**
     * @return bitmap
     */
    public Bitmap makeCircle() {
        //
        Bitmap output = Bitmap.createBitmap(
                getWidth() - Utils.dpToPx(6, getResources()),
                getHeight() - Utils.dpToPx(7, getResources()), Bitmap.Config.ARGB_8888);
        return makeCircleFromBitmap(output);
    }

    // Set color of background
    @Override
    public void setBackgroundColor(int color) {
        backgroundColor = color;
        if (isEnabled()) {
            beforeBackground = backgroundColor;
        }
        try {
            LayerDrawable layer = (LayerDrawable) getBackground();
            // shape_bacground
            //   GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
            /**
             *
             *
             */
            //   shape.setColor(backgroundColor);
            /**
             *
             */
            if (!settedRippleColor) {
                rippleColor = makePressColor(255);
            }
        } catch (Exception ex) {
            // Without bacground
        }
    }

    abstract public TextView getTextView();

}



