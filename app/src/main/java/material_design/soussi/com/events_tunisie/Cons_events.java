package material_design.soussi.com.events_tunisie;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Soussi on 22/04/2015.
 */
public class Cons_events implements Serializable {
   private int id_events;
    private String temps_event,video_id;
   private String nom_events,description_events,date,lieu,event_type,image_url;
   private double latutude,longitude,distance,num_start;
  // private Bitmap image;


    public Cons_events(){}

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getTemps_event() {
        return temps_event;
    }

    public void setTemps_event(String temps_event) {
        this.temps_event = temps_event;
    }

    public int getId_events() {
        return id_events;
    }

    public void setId_events(int id_events) {
        this.id_events = id_events;
    }

    public String getNom_events() {
        return nom_events;
    }

    public void setNom_events(String nom_events) {
        this.nom_events = nom_events;
    }

    public String getDescription_events() {
        return description_events;
    }

    public void setDescription_events(String description_events) {
        this.description_events = description_events;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public double getLatutude() {
        return latutude;
    }

    public void setLatutude(double latutude) {
        this.latutude = latutude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getNum_start() {
        return num_start;
    }

    public void setNum_start(double num_start) {
        this.num_start = num_start;
    }

    public Cons_events(int id_events, String nom_events, String description_events, String date, String lieu, String event_type, String image_url, double latutude, double longitude) {
        this.id_events = id_events;
        this.nom_events = nom_events;
        this.description_events = description_events;
        this.date = date;
        this.lieu = lieu;
        this.event_type = event_type;
        this.image_url = image_url;
        this.latutude = latutude;
        this.longitude = longitude;
    }

    public Cons_events(String nom_events, String description_events, String date, String lieu, String event_type, String image_url, double latutude, double longitude) {
        this.nom_events = nom_events;
        this.description_events = description_events;
        this.date = date;
        this.lieu = lieu;
        this.event_type = event_type;
        this.image_url = image_url;
        this.latutude = latutude;
        this.longitude = longitude;
    }

    public Cons_events(String nom_events, String date, String lieu, String event_type, String image_url) {
        this.nom_events = nom_events;
        this.date = date;
        this.lieu = lieu;
        this.event_type = event_type;
        this.image_url = image_url;
    }

    public Cons_events(String temps_event, String nom_events, String date, String lieu, String event_type, String image_url) {
        this.temps_event = temps_event;
        this.nom_events = nom_events;
        this.date = date;
        this.lieu = lieu;
        this.event_type = event_type;
        this.image_url = image_url;
    }
}
