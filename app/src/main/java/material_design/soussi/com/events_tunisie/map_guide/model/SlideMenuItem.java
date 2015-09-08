package material_design.soussi.com.events_tunisie.map_guide.model;

import material_design.soussi.com.events_tunisie.map_guide.interface_menu.Resourceble;

/**
 * Created by Soussi on 29/04/2015.
 */
public class SlideMenuItem implements Resourceble {
    private String name;
    private int imageRes;

    public SlideMenuItem(String name, int imageRes) {
        this.name = name;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}

