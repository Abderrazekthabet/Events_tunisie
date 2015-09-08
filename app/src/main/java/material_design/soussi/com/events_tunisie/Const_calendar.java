package material_design.soussi.com.events_tunisie;

import java.util.ArrayList;

/**
 * Created by Soussi on 28/04/2015.
 */
public class Const_calendar {

    String name,displayName,color,location,cal_id,discription;
    String EVENT_BEGIN_TIME,EVENT_END_TIME;
    public static ArrayList<Const_calendar> date_collection_arr;
    public Const_calendar(){}

    public String getCal_id() {
        return cal_id;
    }

    public String getDiscription() {
        return discription;
    }

    public String getEVENT_BEGIN_TIME() {
        return EVENT_BEGIN_TIME;
    }

    public void setEVENT_BEGIN_TIME(String EVENT_BEGIN_TIME) {
        this.EVENT_BEGIN_TIME = EVENT_BEGIN_TIME;
    }

    public String getEVENT_END_TIME() {
        return EVENT_END_TIME;
    }

    public void setEVENT_END_TIME(String EVENT_END_TIME) {
        this.EVENT_END_TIME = EVENT_END_TIME;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setCal_id(String cal_id) {
        this.cal_id = cal_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Const_calendar(String cal_id, String name, String displayName, String color, String location, String discription, String EVENT_BEGIN_TIME, String EVENT_END_TIME) {
        this.cal_id = cal_id;
        this.name = name;
        this.displayName = displayName;
        this.color = color;
        this.location = location;
        this.discription = discription;
        this.EVENT_BEGIN_TIME = EVENT_BEGIN_TIME;
        this.EVENT_END_TIME = EVENT_END_TIME;
    }

    public Const_calendar(String name, String displayName, String color, String location, String discription, String EVENT_BEGIN_TIME, String EVENT_END_TIME) {
        this.name = name;
        this.displayName = displayName;
        this.color = color;
        this.location = location;
        this.discription = discription;
        this.EVENT_BEGIN_TIME = EVENT_BEGIN_TIME;
        this.EVENT_END_TIME = EVENT_END_TIME;
    }
}
