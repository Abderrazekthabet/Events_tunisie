package material_design.soussi.com.events_tunisie.Shimmer_animation;

/**
 * Created by Soussi on 05/05/2015.
 */
public interface ShimmerViewBase {

    public float getGradientX();
    public void setGradientX(float gradientX);
    public boolean isShimmering();
    public void setShimmering(boolean isShimmering);
    public boolean isSetUp();
    public void setAnimationSetupCallback(ShimmerViewHelper.AnimationSetupCallback callback);
    public int getPrimaryColor();
    public void setPrimaryColor(int primaryColor);
    public int getReflectionColor();
    public void setReflectionColor(int reflectionColor);
}
