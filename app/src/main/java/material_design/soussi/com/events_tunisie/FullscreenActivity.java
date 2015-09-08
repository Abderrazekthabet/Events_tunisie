package material_design.soussi.com.events_tunisie;

import material_design.soussi.com.events_tunisie.toast.SuperActivityToast;
import material_design.soussi.com.events_tunisie.toast.SuperToast;
import material_design.soussi.com.events_tunisie.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    static private int SPLASH_TIME = 6000;
    private SuperActivityToast toastVERSION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getActionBar().hide();
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        if (Build.VERSION.SDK_INT >= 16) {


            ConnectivityManager cm = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                //Message = "connecter a Internet 3G ";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        Intent mainIntent1 = new Intent(FullscreenActivity.this, MainActivity.class);

                        startActivity(mainIntent1);
                        finish();
                    }

                }, SPLASH_TIME);


            } else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                //Message = "connecter a Internet WIFI ";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        Intent mainIntent1 = new Intent(FullscreenActivity.this, MainActivity.class);

                        startActivity(mainIntent1);
                        finish();
                    }

                }, SPLASH_TIME);


            } else {


                Intent no_internet = new Intent(FullscreenActivity.this, No_connexion.class);
                startActivity(no_internet);
                finish();
            }


       /* new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {


                Intent mainIntent1 = new Intent(FullscreenActivity.this, MainActivity.class);

                startActivity(mainIntent1);
                finish();
            }

        }, SPLASH_TIME);*/

        }
        else
        {

            toastVERSION.setAnimations(SuperToast.Animations.FLYIN);
            toastVERSION.setDuration(SuperToast.Duration.MEDIUM);
            toastVERSION.setBackground(SuperToast.Background.RED);
            toastVERSION.setTextSize(SuperToast.TextSize.MEDIUM);
            toastVERSION.setText("SDK MIN 17 ");
            toastVERSION.setButtonIcon(SuperToast.Icon.Dark.INFO,"Annuler");
            toastVERSION.show();
        }
    }






}
