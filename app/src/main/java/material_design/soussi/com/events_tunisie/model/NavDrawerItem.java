package material_design.soussi.com.events_tunisie.model;

/**
 * Created by Soussi on 21/04/2015.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int image;//new


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {//new
        return image;
    }

    public void setImage(int image) {//new
        this.image = image;
    }

    public NavDrawerItem(boolean showNotify, String title, int image) {//new
        this.showNotify = showNotify;
        this.title = title;
        this.image = image;
    }
}
