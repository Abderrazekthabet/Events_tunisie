package material_design.soussi.com.events_tunisie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.youtube.player.internal.f;

import material_design.soussi.com.events_tunisie.material_button.ButtonFlat;
import material_design.soussi.com.events_tunisie.material_progressbar.ProgressWheel;
import material_design.soussi.com.events_tunisie.swip_to_refrech.SwipyRefreshLayout;
import material_design.soussi.com.events_tunisie.swip_to_refrech.SwipyRefreshLayoutDirection;
import material_design.soussi.com.events_tunisie.toast.SuperActivityToast;
import material_design.soussi.com.events_tunisie.toast.SuperToast;


public class No_connexion extends ActionBarActivity implements SwipyRefreshLayout.OnRefreshListener {
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private SuperActivityToast toastconnexion;
    private ButtonFlat btn_ref;
    private ProgressWheel connect;
    private RelativeLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connexion);




        content=(RelativeLayout)findViewById(R.id.contentDialog);
        connect=(ProgressWheel)findViewById(R.id.progressBar_connect);
        connect.setVisibility(View.INVISIBLE);
        btn_ref=(ButtonFlat)findViewById(R.id.btn_ref);

        btn_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connect.setVisibility(View.VISIBLE);
                content.setVisibility(View.INVISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Hide the refresh after 2sec
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                             //   mSwipyRefreshLayout.setRefreshing(false);

                                ConnectivityManager cm = (ConnectivityManager)
                                        getSystemService(Context.CONNECTIVITY_SERVICE);

                                if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected())
                                {
                                    Intent start=new Intent(No_connexion.this,FullscreenActivity.class);
                                    startActivity(start);
                                }
                                else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
                                {
                                    Intent start=new Intent(No_connexion.this,FullscreenActivity.class);
                                    startActivity(start);
                                }
                                else
                                {
                                    content.setVisibility(View.VISIBLE);
                                    connect.setVisibility(View.INVISIBLE);
                                    toastconnexion= new SuperActivityToast(No_connexion.this,
                                            SuperToast.Type.BUTTON);
                                    toastconnexion.setAnimations(SuperToast.Animations.FLYIN);
                                    toastconnexion.setDuration(SuperToast.Duration.LONG);
                                    toastconnexion.setBackground(SuperToast.Background.RED);
                                    toastconnexion.setTextSize(SuperToast.TextSize.MEDIUM);
                                    toastconnexion.setText(lienhebergement.problem_internet);
                                    toastconnexion.setButtonIcon(SuperToast.Icon.Dark.INFO, "Annuler");
                                    toastconnexion.show();
                                }


                            }
                        });
                    }
                }, 5000);

            }
        });

        mSwipyRefreshLayout = (SwipyRefreshLayout)findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
        //mSwipyRefreshLayout.setProgressBackgroundColor(R.color.color_primary2);
        mSwipyRefreshLayout.setColorScheme(R.color.material_drawer_accent, R.color.material_drawer_primary, R.color.color_success, R.color.color_primary);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getActionBar().hide();
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_no_connexion, menu);
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
    public void onRefresh(SwipyRefreshLayoutDirection direction) {

        Log.d("no connexion", "Refresh triggered at "
                + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Hide the refresh after 2sec
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipyRefreshLayout.setRefreshing(false);

                        ConnectivityManager cm = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);

                        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected())
                        {
                            Intent start=new Intent(No_connexion.this,FullscreenActivity.class);
                            startActivity(start);
                        }
                        else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
                        {
                            Intent start=new Intent(No_connexion.this,FullscreenActivity.class);
                            startActivity(start);
                        }
                        else
                        {
                            toastconnexion= new SuperActivityToast(No_connexion.this,
                                    SuperToast.Type.BUTTON);
                            toastconnexion.setAnimations(SuperToast.Animations.FLYIN);
                            toastconnexion.setDuration(SuperToast.Duration.LONG);
                            toastconnexion.setBackground(SuperToast.Background.RED);
                            toastconnexion.setTextSize(SuperToast.TextSize.MEDIUM);
                            toastconnexion.setText(lienhebergement.problem_internet);
                            toastconnexion.setButtonIcon(SuperToast.Icon.Dark.INFO, "Annuler");
                            toastconnexion.show();
                        }


                    }
                });
            }
        }, 5000);

    }
}
