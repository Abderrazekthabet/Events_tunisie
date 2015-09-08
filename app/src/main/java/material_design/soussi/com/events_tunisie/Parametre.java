package material_design.soussi.com.events_tunisie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import material_design.soussi.com.events_tunisie.load_toast.SnackBar;
import android.preference.Preference;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import material_design.soussi.com.events_tunisie.material_button.Dialog;
import material_design.soussi.com.events_tunisie.preferance.PreferenceCategory;


public class Parametre extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences preferences = null;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(Parametre.this);
        String theme = preferences.getString("themes", "default");
        System.out.println(theme);
        if (theme.equals("default")) setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        else if (theme.equals("light")) setTheme(R.style.Theme_AppCompat_Light_DialogWhenLarge);
        else if (theme.equals("dark")) setTheme(R.style.Base_ThemeOverlay_AppCompat_Dark);

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_parametre);
        addPreferencesFromResource(R.xml.pref);

        PreferenceManager
                .getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);


        for(int x = 0; x < getPreferenceScreen().getPreferenceCount(); x++) {

            PreferenceCategory category = (PreferenceCategory) getPreferenceScreen().getPreference(x);

            for (int y = 0; y < category.getPreferenceCount(); y++) {
                Preference preference = category.getPreference(y);

preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
    @Override
    public boolean onPreferenceClick(Preference preference) {

        if (preference.getKey().equals("p_propos")) {

            StringBuilder msg = new StringBuilder();

            msg.append("Karja");
            msg.append(" ");
            msg.append(":");
            msg.append("\n");
            msg.append("Cette application a ete developpe par : ");
            msg.append("\n");
            msg.append("SOUSSI MOHAMED ");
            msg.append("\n");
            msg.append("E-mail : soussi.mohamed747@gmail.com ");
            msg.append("\n");
            msg.append("Tel : +216 55 071 768");
            msg.append("\n");
            msg.append("Adresse : Akouda 4022 sousse tunisie");
            msg.append("\n");
            msg.append("Version : 1.0");
            msg.append("\n");
            msg.append("Copyright 2015 ");

//            try {
//                // Returns a charset object for the named charset.
//                Charset charset = Charset.forName("ISO-8859-1");
//                // Constructs a new decoder for this charset.
//                CharsetDecoder decoder = charset.newDecoder();
//                // Constructs a new encoder for this charset.
//                CharsetEncoder encoder = charset.newEncoder();
//                // Wrap the character sequence into a buffer.
//                CharBuffer uCharBuffer = CharBuffer.wrap(msg);
//                // Encode the remaining content of a single input character buffer to a new byte buffer.
//                // Converts to ISO-8859-1 bytes and stores them to the byte buffer
//                ByteBuffer bbuf = encoder.encode(uCharBuffer);
//                // Decode the remaining content of a single input byte buffer to a new character buffer.
//                // Converts from ISO-8859-1 bytes and stores them to the character buffer
//                CharBuffer cbuf = decoder.decode(bbuf);
//                 s = cbuf.toString();
//                System.out.println("Original String is: " + s);

                Dialog dialog = new Dialog(Parametre.this, "Karja",msg.toString() );
                dialog.show();

                dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

//            } catch (CharacterCodingException e) {
//
//                System.out.println("Character Coding Error: " + e.getMessage());
//
//            }









           /* try {
                String dd = URLDecoder.decode(msg.toString(), "UTF-8");*/


            /*} catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/


        }

        if (preference.getKey().equals("p_contact")) {

            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, lienhebergement.subj_email);
            intent.putExtra(Intent.EXTRA_TEXT, "");
            intent.setData(Uri.parse("mailto:"+lienhebergement.email_to)); // or just "mailto:" for blank
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent);

        }

        if (preference.getKey().equals("p_facebook")) {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lienhebergement.page_facebook));
            startActivity(browserIntent);

        }

        if (preference.getKey().equals("p_cache")) {

            String data="Confirmation";
            new SnackBar(Parametre.this,
                    data,
                    "OUI", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preferences.edit().clear().commit();
                    recreate();

                }
            }).show();
        }

        return false;
    }
});




                // Initializing the default values
                if (preference.getKey().equals("p_version")) {

                    try {
                        PackageManager packageManager=getPackageManager();
                        PackageInfo packageInfo=packageManager.getPackageInfo(getPackageName(),0);

                        preference.setTitle(packageInfo.versionName);
                        // preference.setSummary(packageInfo.versionCode);

                    } catch (PackageManager.NameNotFoundException e) {
                        // Couldn't get package information
                        //
                        // Won't do anything, since variables are
                        // already started with default values.
                    }


                }


            }
        }
    }





    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent home=new Intent(Parametre.this,MainActivity.class);
        startActivity(home);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parametre, menu);
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

   if (key.equals("themes")) {
            recreate();
            return;
        }

    }
}
