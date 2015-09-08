package material_design.soussi.com.events_tunisie;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import material_design.soussi.com.events_tunisie.recycler.Events_note_adaptateurs;
import material_design.soussi.com.events_tunisie.recycler.Important_adaptateur_events;
import material_design.soussi.com.events_tunisie.recycly_layout_empty.CustomItemAnimator;


public class Events_mieux_noter extends ActionBarActivity {
    private RecyclerView recyclerView_note;
    private StaggeredGridLayoutManager mLayoutManager;
    private Events_note_adaptateurs ad_events_note;
    private ArrayList<Cons_events> list_item_des_important=new ArrayList<Cons_events>();
    private static String url = lienhebergement.lien+"get_events_par_notes.php";
    private JSONArray all_events_par_note = null;
    private static String TAG = Events_mieux_noter.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_mieux_noter);

        new GetEvants_mieux_noter().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_event_par_note);

        toolbar.setNavigationIcon(R.drawable.retour2);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Events_mieux_noter.this.finish();
            }
        });

        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView_note = (RecyclerView)findViewById(R.id.recycler_view_event_par_note);
        recyclerView_note.setItemAnimator(new CustomItemAnimator());
        recyclerView_note.setLayoutManager(mLayoutManager);
        recyclerView_note.setHasFixedSize(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events_mieux_noter, menu);
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

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);

            return true;
        }

        if (id == R.id.action_aide) {
            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);

            Log.i("btn_action :>", "actualisation");

            return true;
        }

        if (id == R.id.action_parametre) {
            Intent parametre=new Intent(Events_mieux_noter.this,Parametre.class);
            startActivity(parametre);

            Log.i("btn_action :>", "parametre");

            return true;
        }

        if (id == R.id.action_gps) {

            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            Log.i("btn_action :>", "gps");

            return true;
        }

        if (id == R.id.action_facebook) {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lienhebergement.page_facebook));
            startActivity(browserIntent);
            Log.i("btn_action :>", "facebook");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                    Log.e(TAG, "onMenuOpened", e);
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private class GetEvants_mieux_noter extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response imp: ", "> " + jsonStr);


            if (jsonStr != null) {
                //  list_item_des.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_events_par_note = jsonObj.getJSONArray("events_notes");
                    Log.d("all events par note: ", "> " + all_events_par_note);

                    for (int i = 0; i < all_events_par_note.length(); i++) {
                        JSONObject c = all_events_par_note.getJSONObject(i);

                        int id_event = c.getInt("id_event");
                        String nom_event = c.getString("nom_event");
                        String lieu_event = c.getString("lieu_event");
                        String date_event = c.getString("date_event");
                        String description_event = c.getString("description_event");
                        String lien_image = c.getString("lien_image");
                        String categories = c.getString("categories");
                        String temps = c.getString("temps");
                        double latitude=c.getDouble("latitude");
                        double longitude=c.getDouble("longitude");
                        double num_start=c.getDouble("num_start");
                        String youtube_id = c.getString("youtube_id");


                        Log.d("all object mieux notes: ", "> " + c);



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
                        item.setNum_start(num_start);
                        //item.setDistance(CalculationB_Distance_en_km(latitude_d, longitude_d, latitude, longitude));

                        list_item_des_important.add(item);


                    }



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
             * Updating parsed JSON data into recyclerView
             * */
            ad_events_note=new Events_note_adaptateurs(Events_mieux_noter.this,list_item_des_important);
            recyclerView_note.setAdapter(ad_events_note);

        }
    }
}
