package material_design.soussi.com.events_tunisie;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import java.lang.reflect.Method;

import material_design.soussi.com.events_tunisie.menu_toolbar.GuillotineAnimation;
import material_design.soussi.com.events_tunisie.model.FragmentDrawer;
import material_design.soussi.com.events_tunisie.notification.ConnectionDetector;
import material_design.soussi.com.events_tunisie.notification.WakeLocker;
import material_design.soussi.com.events_tunisie.toast.SuperActivityToast;
import material_design.soussi.com.events_tunisie.toast.SuperToast;
import static material_design.soussi.com.events_tunisie.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static material_design.soussi.com.events_tunisie.CommonUtilities.EXTRA_MESSAGE;
import static material_design.soussi.com.events_tunisie.CommonUtilities.SENDER_ID;

public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener{

    private static String TAG = MainActivity.class.getSimpleName();

    private SuperActivityToast toast_material_exit;
    private FragmentDrawer drawerFragment;
    private long back_pressed=0;
  //  private GPSTracker gps;
  //  private boolean gps_enabled;
    private Toolbar toolbar;
  public static String name;
    public static String email;
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;
    // Connection detector
    ConnectionDetector cd;
    // Alert dialog manager
    private SharedPreferences preferences = null;
    private static final long RIPPLE_DURATION = 250;
    private RelativeLayout root;
    private ImageView contentHamburger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        root=(RelativeLayout)findViewById(R.id.root);
//        contentHamburger=(ImageView)findViewById(R.id.content_hamburger);
//
//        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
//        root.addView(guillotineMenu);
//
//        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
//                .setStartDelay(RIPPLE_DURATION)
//                .setActionBarViewForAnimation(toolbar)
//                .build();




        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);//copy

        Boolean actif_notification = preferences.getBoolean("p_notification", false);//copy

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
           /* alert.showAlertDialog(MainActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);*/
            // stop executing code by return
            return;
        }

        name="event_notifications";
        email=lienhebergement.email_to;

        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);
        if(actif_notification)
        {
            registerReceiver(mHandleMessageReceiver, new IntentFilter(
                    DISPLAY_MESSAGE_ACTION));
            Log.i("notification push :>", "oui");
        }
        else
        {
            Log.i("notification push :>", "non");
        }


        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(this);

        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
               // Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
                Log.i("GCMRegistrar.isRegisteredOnServer(this) :>", "Already registered with GCM");
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        ServerUtilities.register(context, name, email, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }

       // gps = new GPSTracker(MainActivity.this);
       // LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     //  gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
       /* if(gps.canGetLocation())
        {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.i("start_action location :>", "actualisation"+latitude+" "+longitude);
                   *//* Intent intent = getIntent();
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);

                    Log.i("btn_action :>", "actualisation");*//*



        }*/

        System.out.println("activity principal :"+TAG);
        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);





    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        }
    }

     private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

       // this.getSupportFragmentManager().popBackStack();
        switch (position) {
            case 0:
                fragment = new Lists_events();
                title = getString(R.string.nav_item_home);
                break;
            case 1:
               // fragment = new Maps_events();
                Intent go_map=new Intent(MainActivity.this,Mapactivity.class);
                startActivity(go_map);
                title = getString(R.string.nav_item_friends);
                break;
            case 2:
               fragment = new Categories_Fragment();
                title = getString(R.string.nav_item_notifications);
                break;
           case 3:
                fragment = new Categories_villes();
               title = getString(R.string.nav_item_date);
                break;
            /*case 3:

                Intent pardate=new Intent(MainActivity.this,Events_par_date.class);
                startActivity(pardate);
                title = getString(R.string.nav_item_date);
                break;*/
            case 4:

                Intent noter=new Intent(MainActivity.this,Events_mieux_noter.class);
                startActivity(noter);
                title = getString(R.string.nav_item_noter);
                break;
            case 5:

                Intent parametre=new Intent(MainActivity.this,Parametre.class);
                startActivity(parametre);
                title = getString(R.string.nav_item_parametre);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent parametre=new Intent(MainActivity.this,Parametre.class);
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

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else

            toast_material_exit= new SuperActivityToast(MainActivity.this,
                    SuperToast.Type.BUTTON);
        toast_material_exit.setAnimations(SuperToast.Animations.FLYIN);
        toast_material_exit.setDuration(SuperToast.Duration.LONG);
        toast_material_exit.setBackground(SuperToast.Background.BLUE);
        toast_material_exit.setTextSize(SuperToast.TextSize.MEDIUM);
        toast_material_exit.setText(lienhebergement.text_quitter);
        toast_material_exit.setButtonIcon(SuperToast.Icon.Dark.UNDO, "Annuler");
        toast_material_exit.show();

        back_pressed = System.currentTimeMillis();


    }

    /**
     * Receiving push messages
     * */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Showing received message
          //  lblMessage.append(newMessage + "\n");
           // Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
            Log.i("BroadcastReceiver :>", newMessage);
            // Releasing wake lock
            WakeLocker.release();
        }
    };

    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }
}
