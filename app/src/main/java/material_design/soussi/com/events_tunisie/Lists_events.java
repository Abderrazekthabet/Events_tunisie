package material_design.soussi.com.events_tunisie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;

import material_design.soussi.com.events_tunisie.imageweb.ImageLoader;
import material_design.soussi.com.events_tunisie.imageweb.Utils;
import material_design.soussi.com.events_tunisie.recycler.EvantsAdapter;
import material_design.soussi.com.events_tunisie.recycler.HidingScrollListener;
import material_design.soussi.com.events_tunisie.recycler.HorizontalListView;
import material_design.soussi.com.events_tunisie.recycler.Important_adaptateur_events;
import material_design.soussi.com.events_tunisie.recycler.ListViewAdapter_important;
import material_design.soussi.com.events_tunisie.recycly_layout_empty.CustomItemAnimator;
import material_design.soussi.com.events_tunisie.recyclye_animation.FadeInLeftAnimator;
import material_design.soussi.com.events_tunisie.recyclye_animation.FlipInTopXAnimator;
import material_design.soussi.com.events_tunisie.recyclye_animation.SlideInRightAnimator;
import material_design.soussi.com.events_tunisie.recyclye_animation.SlideInUpAnimator;
import material_design.soussi.com.events_tunisie.scrollview.PoppyViewHelper;
import material_design.soussi.com.events_tunisie.scrollview.recycler_scrolling.ObservableScrollView;
import material_design.soussi.com.events_tunisie.scrollview.recycler_scrolling.ObservableScrollViewCallbacks;
import material_design.soussi.com.events_tunisie.scrollview.recycler_scrolling.ScrollState;
import material_design.soussi.com.events_tunisie.scrollview.recycler_scrolling.ScrollUtils;


public class Lists_events extends Fragment  {
    private RecyclerView recyclerView,recyclerView_important;
  //  private ArrayList<Cons_events> countries;
    private StaggeredGridLayoutManager mLayoutManager;
    private EvantsAdapter evantAdapter;
    private Important_adaptateur_events ad_important_events;
  // private Bitmap bitmap;
   // int pos_cat=0;
    private ProgressDialog pDialog;
    /*public static final String URL =
            "http://theopentutorials.com/totwp331/wp-content/uploads/totlogo.png";*/

    private static String url = lienhebergement.lien+"events.php";
    private static String url_imp = lienhebergement.lien+"events_important.php";
    private ArrayList<Cons_events> list_item_des=new ArrayList<Cons_events>();
    private ArrayList<Cons_events> list_item_des_important=new ArrayList<Cons_events>();
    JSONArray all_events = null;
    private Distance_calcul_gps dis;
    private  GPSTracker gps;
    public  double latitude_d;
    public  double longitude_d;
    private PoppyViewHelper mPoppyViewHelper;
    private HorizontalListView mHlvSimpleList;
    private FrameLayout afficher_bar_import_event;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private Bundle bundle;
    private ListViewAdapter_important adap;
    public Lists_events() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_lists_events, container, false);
         //bundle=savedInstanceState;
      // afficher_bar_import_event=(FrameLayout)rootView.findViewById(R.id.import_events);
        //mHlvSimpleList=(HorizontalListView)rootView.findViewById(R.id.hlvSimpleList1);
      //  adap =new ListViewAdapter_important(getActivity(),list_item_des_important);
        gps = new GPSTracker(getActivity());
        if(gps.canGetLocation()){


            latitude_d = gps.getLatitude();
            longitude_d = gps.getLongitude();

            // \n is for new line
            //Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            Log.d("Response location en cours.... : ", "> " + "Your Location is - \nLat: " + latitude_d + "\nLong: " + longitude_d);

            new GetEvants_gps().execute();
            new GetEvants_important().execute();



        }else{
            new GetEvants().execute();
            new GetEvants_important().execute();
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            // gps.showSettingsAlert();
        }


       /* mPoppyViewHelper = new PoppyViewHelper(getActivity(), PoppyViewHelper.PoppyViewPosition.TOP);
        View poppyView = mPoppyViewHelper.createPoppyViewOnScrollView(R.id.scrollView_import, R.layout.horizontal_view);*/
       // mHlvSimpleList = (HorizontalListView) poppyView.findViewById(R.id.hlvSimpleList1);
       // dis.startService(getActivity(),Distance_calcul_gps.class);

       LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);

        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

      //  createAndPopulateCountriesArray();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                for (Cons_events cn : list_item_des) {

                    Log.i("list distance par event", String.valueOf(cn.getDistance()));

                }

               /* dis = new Distance_calcul_gps();
                double p = dis.getdistance(getActivity(), 35.868933, 10.582576);
                System.out.println("votre distance est :" + p);
*/
          }

        }, 5000);



       /* if(!countries.isEmpty())
        {
           //set on post execute

        }*/

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView_important = (RecyclerView) rootView.findViewById(R.id.recycler_view_import);
//        evantAdapter = new EvantsAdapter(getActivity(), list_item_des);
//        recyclerView.setAdapter(evantAdapter);
       // recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setItemAnimator(new CustomItemAnimator());
     //  recyclerView.getItemAnimator().setAddDuration(300);
      // recyclerView.getItemAnimator().setRemoveDuration(300);

        recyclerView_important.setItemAnimator(new CustomItemAnimator());
       // recyclerView_important.getItemAnimator().setAddDuration(300);
       // recyclerView_important.getItemAnimator().setRemoveDuration(300);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//,LinearLayoutManager.HORIZONTAL, false
       recyclerView_important.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView_important.setHasFixedSize(true);

        //  get_all_data();
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent desc = new Intent(getActivity(), Descriptions.class);


                // desc.putExtra("item_loisir",list_item_des.get(position));
        startActivity(desc);
    }
});
recyclerView.setOnScrollListener(new HidingScrollListener() {
    @Override
    public void onHide() {
       // hideViews();
    }

    @Override
    public void onShow() {
      //  showViews();
    }
});



      /* new Handler().post(new Runnable() {
            @Override
            public void run() {


                ////////////////////////////////////////////////////////////////////////

                //////////////////////////////////////////////////////////////////////////
              //  ListViewAdapterStreaming adapter = new ListViewAdapterStreaming(getActivity(), list_item_streaming);//db.getAllContacts_adress()
                // lv_streaming.setAdapter( adapter);//adapter
                *//*if(list_item_des.isEmpty())
                {

                }
                else
                {

                }*//*
            }
        });*/






        /*for (Cons_events cn : list_item_des) {
            countries.add(new Cons_events(cn.getNom_events(),cn.getDate(),cn.getLieu(),cn.getEvent_type(),cn.getImage_url()));
            Log.i("list streaming video", cn.getNom_events());

        }*/


        return rootView;
    }


    /*private void hideViews() {
       // mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) afficher_bar_import_event.getLayoutParams();
        int fabBottomMargin = lp.topMargin;
        afficher_bar_import_event.animate().translationY(afficher_bar_import_event.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
      //  mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        afficher_bar_import_event.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }*/


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

    private void createAndPopulateCountriesArray() {
      //  ImageLoader imgLoader = new ImageLoader(getActivity());

    //    bitmap=downloadImage("http://test-dashboard1.seeloz.com/system/images/products_images/86/5454544114_1401886223?1401886223");
      //  new LoadImage().execute("http://www.learn2crack.com/wp-content/uploads/2014/04/node-cover-720x340.png");
     //  countries = new ArrayList<Cons_events>();

     //   Cons_events item = new Cons_events();
     //   countries.add(new Cons_events("events 1","2015","tunis","sport","http://test-dashboard1.seeloz.com/system/images/products_images/86/5454544114_1401886223?1401886223"));
      //  countries.add(new Cons_events("events 2","2015","sousse","loisir","http://test-dashboard1.seeloz.com/system/images/products_images/86/5454544114_1401886223?1401886223"));
      //  countries.add(new Cons_events("events 3","2015","sousse","loisir",URL));
      //  countries.add(new Cons_events("events 4","2015","sousse","loisir","http://test-dashboard1.seeloz.com/system/images/products_images/86/5454544114_1401886223?1401886223"));


    }

    private void get_all_data()
    {
       /* new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {



            }
        }, 1000);*/

       /* for (Cons_events cn : list_item_des) {
            countries.add(new Cons_events(cn.getNom_events(),cn.getDate(),cn.getLieu(),cn.getEvent_type(),cn.getImage_url()));
            // Log.i("list streaming video", log);

        }*/


    }





    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetEvants extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(lienhebergement.dailog_load);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);
           // list_item_des.clear();
            if (jsonStr != null) {
              //  list_item_des.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_events = jsonObj.getJSONArray(lienhebergement.JSON_ARRAY_EVENTS);
                    Log.d("all events: ", "> " + all_events);

                    for (int i = 0; i < all_events.length(); i++) {
                        JSONObject c = all_events.getJSONObject(i);

                       int id_event = c.getInt(lienhebergement.ID_EVENTS);
                        String nom_event = c.getString(lienhebergement.NOM_EVENTS);
                        String lieu_event = c.getString(lienhebergement.LIEU_EVENTS);
                        String date_event = c.getString(lienhebergement.DATE_EVENTS);
                        String description_event = c.getString(lienhebergement.DESCRIPTION_EVENTS);
                        String lien_image = c.getString(lienhebergement.LIEN_IMAGE);
                        String categories = c.getString("categories");
                        String temps = c.getString("temps");
                        double latitude=c.getDouble("latitude");
                        double longitude=c.getDouble("longitude");
                        String youtube_id = c.getString("youtube_id");
                      //  dis = new Distance_calcul_gps();
                      //  double p = dis.getdistance(getActivity(),latitude, longitude);

                        Log.d("all object: ", "> " + c);
                       // Log.d("all distance: ", "> " + p);


                      Cons_events item = new Cons_events();

                        item.setId_events(id_event);
                        item.setNom_events(nom_event);
                        item.setLieu(lieu_event);
                        item.setDate(date_event);
                        item.setDescription_events(description_event);
                        item.setImage_url(lien_image);
                        item.setEvent_type(categories);
                        item.setTemps_event(temps);
                        item.setLatutude(latitude);
                        item.setLongitude(longitude);
                        item.setVideo_id(youtube_id);
                      //  item.setDistance(CalculationB_Distance_en_km(35.868933, 10.582576,latitude,longitude));

                        list_item_des.add(item);

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
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            evantAdapter = new EvantsAdapter(getActivity(), list_item_des);
            recyclerView.setAdapter(evantAdapter);

           /* ImageLoader imgLoader = new ImageLoader(getActivity());
            imgLoader.clearCache();*/


        }

        @Override
        protected void onCancelled() {

            super.onCancelled();
            pDialog.dismiss();
            Toast toast = Toast.makeText(getActivity(),
                    "Error is occured due to some probelm", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();

        }



    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetEvants_gps extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(lienhebergement.dailog_load);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);
          //  list_item_des.clear();
            if (jsonStr != null) {
                //  list_item_des.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_events = jsonObj.getJSONArray(lienhebergement.JSON_ARRAY_EVENTS);
                    Log.d("all events: ", "> " + all_events);

                    for (int i = 0; i < all_events.length(); i++) {
                        JSONObject c = all_events.getJSONObject(i);

                        int id_event = c.getInt(lienhebergement.ID_EVENTS);
                        String nom_event = c.getString(lienhebergement.NOM_EVENTS);
                        String lieu_event = c.getString(lienhebergement.LIEU_EVENTS);
                        String date_event = c.getString(lienhebergement.DATE_EVENTS);
                        String description_event = c.getString(lienhebergement.DESCRIPTION_EVENTS);
                        String lien_image = c.getString(lienhebergement.LIEN_IMAGE);
                        String categories = c.getString(lienhebergement.CATEGORIE);
                        String temps = c.getString("temps");
                        double latitude=c.getDouble("latitude");
                        double longitude=c.getDouble("longitude");
                        String youtube_id = c.getString("youtube_id");
                        //  dis = new Distance_calcul_gps();
                        //  double p = dis.getdistance(getActivity(),latitude, longitude);

                        Log.d("all object: ", "> " + c);
                        // Log.d("all distance: ", "> " + p);


                        Cons_events item = new Cons_events();

                        item.setId_events(id_event);
                        item.setNom_events(nom_event);
                        item.setLieu(lieu_event);
                        item.setDate(date_event);
                        item.setDescription_events(description_event);
                        item.setImage_url(lien_image);
                        item.setEvent_type(categories);
                        item.setTemps_event(temps);
                        item.setLatutude(latitude);
                        item.setLongitude(longitude);
                        item.setVideo_id(youtube_id);
                        item.setDistance(CalculationB_Distance_en_km(latitude_d, longitude_d,latitude,longitude));

                        list_item_des.add(item);

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
           // if (pDialog.isShowing())
                pDialog.dismiss();
                pDialog = null;
            /**
             * Updating parsed JSON data into ListView
             * */

            evantAdapter = new EvantsAdapter(getActivity(), list_item_des);
            recyclerView.setAdapter(evantAdapter);

           /* ImageLoader imgLoader = new ImageLoader(getActivity());
            imgLoader.clearCache();*/


        }
        @Override
        protected void onCancelled() {

            super.onCancelled();
            pDialog.dismiss();
            Toast toast = Toast.makeText(getActivity(),
                    "Error is occured due to some probelm", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();

        }



    }

    @Override
    public void onStop() {

        super.onStop();

        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }



    private class GetEvants_important extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url_imp, ServiceHandler.GET);

            Log.d("Response imp: ", "> " + jsonStr);


            if (jsonStr != null) {
                //  list_item_des.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_events = jsonObj.getJSONArray("events_imp");
                    Log.d("all events imp: ", "> " + all_events);

                    for (int i = 0; i < all_events.length(); i++) {
                        JSONObject c = all_events.getJSONObject(i);

                        int id_event = c.getInt("id_event_imp");
                        String nom_event = c.getString("nom_event_imp");
                        String lieu_event = c.getString("lieu_event_imp");
                        String date_event = c.getString("date_event_imp");
                        String description_event = c.getString("description_event_imp");
                        String lien_image = c.getString("lien_image_imp");
                        String categories = c.getString("categories_imp");
                        String temps = c.getString("temps_imp");
                        double latitude = c.getDouble("latitude_imp");
                        double longitude = c.getDouble("longitude_imp");
                        String id_youtube = c.getString("youtube_id_imp");
                        //  dis = new Distance_calcul_gps();
                        //  double p = dis.getdistance(getActivity(),latitude, longitude);

                        Log.d("all object imp: ", "> " + c);
                        // Log.d("all distance: ", "> " + p);


                        Cons_events item = new Cons_events();

                        item.setId_events(id_event);
                        item.setNom_events(nom_event);
                        item.setLieu(lieu_event);
                        item.setDate(date_event);
                        item.setDescription_events(description_event);
                        item.setImage_url(lien_image);
                        item.setEvent_type(categories);
                        item.setTemps_event(temps);
                        item.setLatutude(latitude);
                        item.setLongitude(longitude);
                        item.setVideo_id(id_youtube);
                        //item.setDistance(CalculationB_Distance_en_km(latitude_d, longitude_d, latitude, longitude));

                        list_item_des_important.add(item);

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

            /**
             * Updating parsed JSON data into ListView
             * */
          ad_important_events=new Important_adaptateur_events(getActivity(),list_item_des_important);
            recyclerView_important.setAdapter(ad_important_events);

        }
    }
   /* public Bitmap GetBitmapfromUrl(String scr) {
        URL imageURL = null;

        try {
           *//* URL url=new URL(scr);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input=connection.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(input);*//*

            imageURL = new URL(scr);
            HttpURLConnection connection = (HttpURLConnection) imageURL
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            return BitmapFactory.decodeStream(inputStream);



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }

    }*/

    /*    // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

*/



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lists_events, menu);
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
    }*/
}
