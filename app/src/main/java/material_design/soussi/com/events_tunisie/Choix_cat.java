package material_design.soussi.com.events_tunisie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import material_design.soussi.com.events_tunisie.model.FragmentDrawer;
import material_design.soussi.com.events_tunisie.recycler.Cathegorie_adaptateur;
import material_design.soussi.com.events_tunisie.recycler.EmptyAdaptateur;
import material_design.soussi.com.events_tunisie.recycler.EvantsAdapter;
import material_design.soussi.com.events_tunisie.recycly_layout_empty.CustomItemAnimator;
import material_design.soussi.com.events_tunisie.recycly_layout_empty.EmptyLayout;


public class Choix_cat extends ActionBarActivity {

    private static String url = lienhebergement.lien+"categorie_events.php";
    private ArrayList<Cons_events> list_item_des=new ArrayList<Cons_events>();
    private JSONArray all_events = null;
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private String type_item;
    private TextView empty;
    private static String TAG = Choix_cat.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_cat);


        StrictMode.enableDefaults();
        Intent receivedIntent=getIntent();
        type_item=receivedIntent.getStringExtra("type11");
        System.out.println("type=" + type_item);

        new GetEvants().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_choix_cat);
        setSupportActionBar(toolbar);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(type_item);
        // toolbar.setLogo(R.drawable.retour);
        toolbar.setNavigationIcon(R.drawable.retour2);
       // toolbar.setSubtitle(type_item);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Choix_cat.this.finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(Choix_cat.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_viewcat);
        Cathegorie_adaptateur  chategorieadaptateur = new Cathegorie_adaptateur(Choix_cat.this, list_item_des);
        recyclerView.setAdapter(chategorieadaptateur);
        recyclerView.setItemAnimator(new CustomItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(Choix_cat.this));//
        // recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        empty=(TextView)findViewById(R.id.textempty);

    }


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetEvants extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Choix_cat.this);
            pDialog.setMessage(lienhebergement.dailog_load);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("type", type_item));

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET,params);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                //  list_item_des.clear();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_events = jsonObj.getJSONArray(lienhebergement.JSON_ARRAY_EVENTS);
                    Log.d("all categories: ", "> " + all_events);

                    for (int i = 0; i < all_events.length(); i++) {
                        JSONObject c = all_events.getJSONObject(i);

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

                        Log.d("all object: ", "> " + c);


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

            Cathegorie_adaptateur  chategorieadaptateur = new Cathegorie_adaptateur(Choix_cat.this, list_item_des);
            EmptyAdaptateur ad_vide=new EmptyAdaptateur(Choix_cat.this);
           // recyclerView.setAdapter(chategorieadaptateur);

           if(list_item_des.isEmpty())
           {
               empty.setVisibility(View.VISIBLE);
               empty.setText("List vide !");
 //              recyclerView.setAdapter(ad_vide);
//                mEmptyLayout.showError();
//                mEmptyLayout.setEmptyMessage("Aucun événement disponible !");
          }
           else
           {
               empty.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(chategorieadaptateur);
                //lv_streaming.setAdapter( adapter);//adapter
          }

           /* ImageLoader imgLoader = new ImageLoader(getActivity());
            imgLoader.clearCache();*/


        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_choix_cat, menu);
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
            Intent parametre=new Intent(Choix_cat.this,Parametre.class);
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
}
