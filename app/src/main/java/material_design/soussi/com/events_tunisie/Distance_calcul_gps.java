package material_design.soussi.com.events_tunisie;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.app.Service;

import java.text.DecimalFormat;

/**
 * Created by Soussi on 01/05/2015.
 */
public class Distance_calcul_gps extends Service{

    private  GPSTracker gps;
    public   double latitude_d;
    public   double longitude_d;




    public double getdistance(Context cx,double lat,double lon)
    {
        double dist_event = 0;
        gps = new GPSTracker(cx);

        if(gps.canGetLocation()){


                    latitude_d = gps.getLatitude();
                    longitude_d = gps.getLongitude();
                     dist_event= CalculationB_Distance_en_km1(latitude_d,longitude_d,lat,lon);
                    // \n is for new line
                    //Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    Log.d("Response location : ", "> " + "Your Location is - \nLat: " + latitude_d + "\nLong: " + longitude_d);





        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            // gps.showSettingsAlert();
        }



        return dist_event;
    }

    public  double CalculationB_Distance_en_km1(double lat1, double lon1, double lat2, double lon2) {
        int Radius=6371;//radius of earth in Km
        // double lat1 = lat1;
        // double lat2 = EndP.latitude;
        // double lon1 = StartP.longitude;
        //  double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return kmInDec;
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override

    public void onCreate() {

      //  Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();



    }



    @Override

    public void onStart(Intent intent, int startId) {

        // For time consuming an long tasks you can launch a new thread here...

       // Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();



    }



    @Override

    public void onDestroy() {

     //   Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();



    }

    /*public void startService(FragmentActivity activity, Class<Distance_calcul_gps> distance_calcul_gpsClass) {
    }*/
}
