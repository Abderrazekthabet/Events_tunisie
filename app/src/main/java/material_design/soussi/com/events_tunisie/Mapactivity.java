package material_design.soussi.com.events_tunisie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import material_design.soussi.com.events_tunisie.load_toast.SnackBar;
import material_design.soussi.com.events_tunisie.map_guide.ContentFragment;
import material_design.soussi.com.events_tunisie.map_guide.animation.SupportAnimator;
import material_design.soussi.com.events_tunisie.map_guide.animation.ViewAnimationUtils;
import material_design.soussi.com.events_tunisie.map_guide.interface_menu.Resourceble;
import material_design.soussi.com.events_tunisie.map_guide.interface_menu.ScreenShotable;
import material_design.soussi.com.events_tunisie.map_guide.model.SlideMenuItem;
import material_design.soussi.com.events_tunisie.map_guide.util.ViewAnimator;
import material_design.soussi.com.events_tunisie.map_menu_button.ABShape;
import material_design.soussi.com.events_tunisie.map_menu_button.ABTextUtil;
import material_design.soussi.com.events_tunisie.map_menu_button.RapidFloatingActionButton;
import material_design.soussi.com.events_tunisie.map_menu_button.RapidFloatingActionHelper;
import material_design.soussi.com.events_tunisie.map_menu_button.RapidFloatingActionLayout;
import material_design.soussi.com.events_tunisie.map_menu_button.labellist.RFACLabelItem;
import material_design.soussi.com.events_tunisie.map_menu_button.labellist.RapidFloatingActionContentLabelList;
import material_design.soussi.com.events_tunisie.recycler.EvantsAdapter;
import material_design.soussi.com.events_tunisie.toast.SuperActivityToast;
import material_design.soussi.com.events_tunisie.toast.SuperToast;


public class Mapactivity extends ActionBarActivity implements LocationListener,RapidFloatingActionContentLabelList.OnRapidFloatingActionContentListener {
    private GoogleMap map;
    private boolean gps_enabled,wifi_enabled;
    private Location current_locLocation;
    MapFragment mapfragment;
    double latutude,longitude;
    private  MarkerOptions marker_item = new MarkerOptions();
    private static String url = lienhebergement.lien+"events.php";
    private ArrayList<Cons_events> list_item_des_map=new ArrayList<Cons_events>();
    JSONArray all_events = null;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
   // private ContentFragment contentFragment;
   // private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;
   // private int res = R.drawable.cat_sport;
    private SuperActivityToast toastmap,toastgps,toastmarker;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfabHelper;
    private RapidFloatingActionLayout rfaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity);

        ///////////////////////////////////////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmap);
        //toolbar.setLogo(R.drawable.retour);
        toolbar.setNavigationIcon(R.drawable.retour2);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mapactivity.this.finish();
            }
        });

        rfaButton=(RapidFloatingActionButton)findViewById(R.id.label_list_sample_rfab);
        rfaLayout=(RapidFloatingActionLayout)findViewById(R.id.label_list_sample_rfal);

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(Mapactivity.this);
        rfaContent.setOnRapidFloatingActionContentListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                        .setLabel("Map normal")
                        .setResId(R.drawable.map_icon_m)
                        .setIconNormalColor(0xff4e342e)
                        .setIconPressedColor(0xff3e2723)
                        .setLabelColor(Color.WHITE)
                        .setLabelSizeSp(14)
                        .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(Mapactivity.this, 4)))
                        .setWrapper(1)
        );

        items.add(new RFACLabelItem<Integer>()
                        .setLabel("Map satelite")
                        .setResId(R.drawable.map_icon_m)
                        .setIconNormalColor(0xff4e342e)
                        .setIconPressedColor(0xff3e2723)
                        .setLabelColor(Color.WHITE)
                        .setLabelSizeSp(14)
                        .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(Mapactivity.this, 4)))
                        .setWrapper(1)
        );

//        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("Map hybrid")
//                        .setResId(R.drawable.map_icon_m)
//                        .setIconNormalColor(0xff4e342e)
//                        .setIconPressedColor(0xff3e2723)
//                        .setLabelColor(Color.WHITE)
//                        .setLabelSizeSp(14)
//                        .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(Mapactivity.this, 4)))
//                        .setWrapper(1)
//        );

        items.add(new RFACLabelItem<Integer>()
                        .setLabel("Map terrain")
                        .setResId(R.drawable.map_icon_m)
                        .setIconNormalColor(0xff4e342e)
                        .setIconPressedColor(0xff3e2723)
                        .setLabelColor(Color.WHITE)
                        .setLabelSizeSp(14)
                        .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(Mapactivity.this, 4)))
                        .setWrapper(1)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(Mapactivity.this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(Mapactivity.this, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                Mapactivity.this,
                rfaLayout,
                rfaButton,
                rfaContent
        ).build();
        ///////////////////////////////////////////////////////////////////////////////////////////

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mapfragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map1);
        map=mapfragment.getMap();
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);



        gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        wifi_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
       // System.out.println("gps is enabled="+gps_enabled);
       // System.out.println("wifi is enabled="+wifi_enabled);

       /* lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);
        if(wifi_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, this);
        else{
            if(gps_enabled)
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);
            else
                Toast.makeText(Mapactivity.this,
                        "Error detecting location....", Toast.LENGTH_LONG).show();
        }
*/


        new GetEvants_position_in_map().execute();



        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        if(wifi_enabled)
        {

            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        }


        if(gps_enabled)
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);

            //-------------------------------------------------------------------
            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected())
            {
               // new Createposition().execute();
            }
            else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
            {
             //   new Createposition().execute();
            }
            else
            {
               // Toast.makeText(Mapactivity.this, "Pas de connection a internet", Toast.LENGTH_LONG).show();
                toastmap= new SuperActivityToast(Mapactivity.this,
                        SuperToast.Type.BUTTON);
                toastmap.setAnimations(SuperToast.Animations.FLYIN);
                toastmap.setDuration(SuperToast.Duration.LONG);
                toastmap.setBackground(SuperToast.Background.BLACK);
                toastmap.setTextSize(SuperToast.TextSize.MEDIUM);
                toastmap.setText(lienhebergement.problem_internet);
                toastmap.setButtonIcon(SuperToast.Icon.Dark.EXIT, "Annuler");
                toastmap.show();
            }
            //-------------------------------------------------------------------

        }

        else

        {
          //  Toast.makeText(Mapactivity.this,
           //         "Impossible de détecter vos emplacement courant veuillez activer GPS....", Toast.LENGTH_LONG).show();

            toastgps= new SuperActivityToast(Mapactivity.this,
                    SuperToast.Type.BUTTON);
            toastgps.setAnimations(SuperToast.Animations.POPUP);
            toastgps.setDuration(SuperToast.Duration.SHORT);
            toastgps.setBackground(SuperToast.Background.RED);
            toastgps.setTextSize(SuperToast.TextSize.SMALL);
            toastgps.setText("Impossible de détecter vos emplacement courant veuillez activer GPS....");
            toastgps.setButtonIcon(SuperToast.Icon.Dark.UNDO,"Annuler");
            toastgps.show();

        }


map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
    @Override
    public boolean onMarkerClick(final Marker marker) {
        if(gps_enabled) {
            final double p_latitude = marker.getPosition().latitude;
            final double p_longitude = marker.getPosition().longitude;

            new SnackBar(Mapactivity.this, "Navigation", "Go", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    launchGoogleMaps(Mapactivity.this, p_latitude, p_longitude, marker.getTitle());
                }
            }).show();

        }
        else
        {
          //  Toast.makeText(Mapactivity.this,
           //         "Impossible de détecter vos emplacement courant veuillez activer GPS....", Toast.LENGTH_LONG).show();

            toastmarker= new SuperActivityToast(Mapactivity.this,
                    SuperToast.Type.BUTTON);
            toastmarker.setAnimations(SuperToast.Animations.FLYIN);
            toastmarker.setDuration(SuperToast.Duration.LONG);
            toastmarker.setBackground(SuperToast.Background.PURPLE);
            toastmarker.setTextSize(SuperToast.TextSize.SMALL);
            toastmarker.setText("Impossible de détecter vos emplacement courant veuillez activer GPS....");
            toastmarker.setButtonIcon(SuperToast.Icon.Dark.INFO,"Annuler");
            toastmarker.show();
        }
        return true;
    }
});




    }

    public static void launchGoogleMaps(Context context, double latitude, double longitude, String label) {
        String format = "geo:0,0?q=" + Double.toString(latitude) + "," + Double.toString(longitude) + "(" + label + ")";
        Uri uri = Uri.parse(format);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }




    public double CalculationByDistance(double lat1,double lon1, double lat2 ,double lon2) {
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

        return Radius * c;
    }

    public double CalculationB_Distance_en_km(double lat1,double lon1, double lat2 ,double lon2) {
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

   /* @Override
    protected boolean isRouteDisplayed() {
        return false;
    }*/


    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if(current_locLocation==null){
            current_locLocation=location;
            double dis_km=  CalculationB_Distance_en_km(current_locLocation.getLatitude(),current_locLocation.getLongitude(),35.868933,10.582576);
            System.out.println("distance en km :"+dis_km);


            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

            MarkerOptions mp = new MarkerOptions();
            mp.visible(true);
           // System.out.println("latitude="+location.getLatitude());
           // System.out.println("longitude="+location.getLongitude());
            mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
            mp.title("Ma position")
                  //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            .icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.map_marqueur111));
            map.addMarker(mp);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(current_locLocation.getLatitude(), current_locLocation.getLongitude()), 12));
        }
        else
        {
            //////////////////////////////////////////////////////////////////
            List<Cons_events> contacts = list_item_des_map;
            for (Cons_events cn : contacts) {
                /////////////////////////////////////////////////////////////////
                current_locLocation = location;
                float[] last_new_location = new float[3];
                Location.distanceBetween(current_locLocation.getLatitude(), current_locLocation.getLongitude(),
                        current_locLocation.getLatitude(), current_locLocation.getLongitude(), last_new_location);
                double latnewdifference = (last_new_location[0] * 0.000621);

                if (latnewdifference > 0.1) {

                    map.clear();

                    //  getTraçage();
                    getWindow().getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    // new Createposition().execute();
                    //***************************************************************************************************

                    //***************************************************************************************************
                    //--------------------------------------------------------------------
                    List<Cons_events> contacts2 = list_item_des_map;
                    for (Cons_events cn2 : contacts2) {


                        marker_item.position(new LatLng(cn.getLatutude(), cn.getLongitude()));
                        marker_item.title(cn.getNom_events())
                                .snippet(cn.getDate())
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.kharja11));
                        // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        map.addMarker(marker_item);
                    }

               /* List<Cons_events> contacts2 = list_item_des_map;
                for (Cons_events cn : contacts2) {

               double dis= CalculationByDistance_en_km(current_locLocation.getLatitude(),current_locLocation.getLongitude(),
                            cn.getLatutude(),cn.getLongitude());
                     System.out.println("distance plus proche"+dis);
                }*/


                    //----------------------------------------------------------------------

                    MarkerOptions mp = new MarkerOptions();
                    // System.out.println("latitude="+location.getLatitude());
                    // System.out.println("longitude="+location.getLongitude());

                    latutude = location.getLatitude();
                    longitude = location.getLongitude();


                    System.out.println("================================");
                /*System.out.println("latitude="+latutude);
                System.out.println("longitude="+longitude);*/
                    System.out.println("================================");

                    mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

                    mp.title("Ma position")
                            // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.map_marqueur111));
                    map.addMarker(mp);
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(current_locLocation.getLatitude(), current_locLocation.getLongitude()), 16));

                    //------------------------------------
                }
                //-----------------------------------
                ///////////////////////////////////////////////////////
            }
            ///////////////////////////////////////////////////////
        }


    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Mapactivity.this.finish();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {

        switch (position) {
            case 0:
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            /*case 2:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;*/

            case 2:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:
                break;
        }

        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {

        switch (position) {
            case 0:
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            /*case 2:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;*/

            case 2:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:
                break;
        }
        rfabHelper.toggleContent();
    }


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetEvants_position_in_map extends AsyncTask<Void, Void, Void> {

        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();

        }*/

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                //  list_item_des.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_events = jsonObj.getJSONArray(lienhebergement.JSON_ARRAY_EVENTS);
                    Log.d("all forcast: ", "> " + all_events);

                    for (int i = 0; i < all_events.length(); i++) {
                        JSONObject c = all_events.getJSONObject(i);

                        int id_event1 = c.getInt(lienhebergement.ID_EVENTS);
                        String nom_event1 = c.getString(lienhebergement.NOM_EVENTS);
                        String lieu_event1 = c.getString(lienhebergement.LIEU_EVENTS);
                        String date_event1 = c.getString(lienhebergement.DATE_EVENTS);
                        String description_event1 = c.getString(lienhebergement.DESCRIPTION_EVENTS);
                        String lien_image1 = c.getString(lienhebergement.LIEN_IMAGE);
                        String categories1 = c.getString("categories");
                        String temps1 = c.getString("temps");
                        double latitude1=c.getDouble("latitude");
                        double longitude1=c.getDouble("longitude");

                        Log.d("all object: ", "> " + c);


                        Cons_events item = new Cons_events();

                        item.setId_events(id_event1);
                        item.setNom_events(nom_event1);
                        item.setLieu(lieu_event1);
                        item.setDate(date_event1);
                        item.setDescription_events(description_event1);
                        item.setImage_url(lien_image1);
                        item.setEvent_type(categories1);
                        item.setTemps_event(temps1);
                        item.setLatutude(latitude1);
                        item.setLongitude(longitude1);

                        list_item_des_map.add(item);

                        //}	//new
                    }
                    //**********************************************************************************************


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            /*if (pDialog.isShowing())
                pDialog.dismiss();*/
            /**
             * Updating parsed JSON data into ListView
             * */

            List<Cons_events> contacts = list_item_des_map;
            for (Cons_events cn : contacts) {

              /*  CalculationByDistance(current_locLocation.getLatitude(),current_locLocation.getLongitude(),
                        cn.getLatutude(),cn.getLongitude());
*/
                marker_item.position(new LatLng(cn.getLatutude(), cn.getLongitude()));
              /*  double dis= CalculationByDistance_en_km(current_locLocation.getLatitude(),current_locLocation.getLongitude(),
                        cn.getLatutude(),cn.getLongitude());*/
                marker_item.title(cn.getNom_events())
                        .snippet(cn.getDate())

                        .icon(BitmapDescriptorFactory
                                //.fromBitmap());
                                .fromResource(R.drawable.kharja11));

                // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                map.addMarker(marker_item);
            }


           /* ImageLoader imgLoader = new ImageLoader(getActivity());
            imgLoader.clearCache();*/


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        if("gps".equals(provider)) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.removeUpdates(this);

        }

    }
}
