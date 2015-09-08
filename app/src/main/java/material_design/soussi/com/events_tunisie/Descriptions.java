package material_design.soussi.com.events_tunisie;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import material_design.soussi.com.events_tunisie.Shimmer_animation.Shimmer;
import material_design.soussi.com.events_tunisie.Shimmer_animation.ShimmerButton;
import material_design.soussi.com.events_tunisie.Shimmer_animation.ShimmerTextView;
import material_design.soussi.com.events_tunisie.imageweb.ImageLoader;
import material_design.soussi.com.events_tunisie.load_toast.LoadToast;
import material_design.soussi.com.events_tunisie.load_toast.SnackBar;
import material_design.soussi.com.events_tunisie.material_button.ButtonFloat;
import material_design.soussi.com.events_tunisie.material_button.Dialog;
import material_design.soussi.com.events_tunisie.recycler.Events_note_adaptateurs;
import material_design.soussi.com.events_tunisie.scrollview.PoppyViewHelper;
import material_design.soussi.com.events_tunisie.toast.SuperActivityToast;
import material_design.soussi.com.events_tunisie.toast.SuperToast;


public class Descriptions extends ActionBarActivity  {
    private ImageView aff;
    private TextView categorie_event,lieu_event,date_events,temps_events,description_events;
    private Intent intent_recu;
    private Cons_events const1;
    private ButtonFloat btn_cal_syn,btn_gotomap;
    private SnackBar snackbar_stream;
    private PoppyViewHelper mPoppyViewHelper;
    private ColoredRatingBar ratingBar;
    private ShimmerButton play_video,btn_classer;
    private ShimmerTextView text_categorie_animation,text_youtube_anim,text_description_anim,non_event;
    private Shimmer shimmer;
    private static String url_fav_events=lienhebergement.lien+"fav_events.php";
    private static String url_statistique_events=lienhebergement.lien+"get_event_statistique_moy.php";
    private ServiceHandler servicehandler =new ServiceHandler();
    private Float rating_num;
    private SuperActivityToast toast_material_youtube;
    private JSONArray all_events_par_moy = null;
    private float NUMSTART;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptions);


        shimmer = new Shimmer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                new GetEvants_moy().execute();

            }

        }, 1000);


        text_categorie_animation=(ShimmerTextView)findViewById(R.id.text_categorie_animation);
        text_description_anim=(ShimmerTextView)findViewById(R.id.text_discription_anim);
        text_youtube_anim=(ShimmerTextView)findViewById(R.id.text_youtube_anim);
      //  shimmer.start(text_categorie_animation);
       // shimmer.start(text_description_anim);
      //  shimmer.start(text_youtube_anim);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_desc);
        //toolbar.setLogo(R.drawable.retour);
        toolbar.setNavigationIcon(R.drawable.retour2);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Descriptions.this.finish();
            }
        });

        mPoppyViewHelper = new PoppyViewHelper(this, PoppyViewHelper.PoppyViewPosition.BOTTOM);
        View poppyView = mPoppyViewHelper.createPoppyViewOnScrollView(R.id.scrollView, R.layout.poppyview);

        btn_cal_syn=(ButtonFloat)poppyView.findViewById(R.id.calendar_syn1);
        btn_gotomap=(ButtonFloat)poppyView.findViewById(R.id.gotomap);

        aff=(ImageView)findViewById(R.id.imageaffiche);
      //  non_event=(TextView)findViewById(R.id.nom_events_d);
        non_event=(ShimmerTextView)findViewById(R.id.nom_events_d);
        shimmer.start(non_event);
        categorie_event=(TextView)findViewById(R.id.categorie_events_d);
        lieu_event=(TextView)findViewById(R.id.lieu_events_d);
        date_events=(TextView)findViewById(R.id.date_events_d);
        description_events=(TextView)findViewById(R.id.description_events_d);
       // temps_events=(TextView)findViewById(R.id.temps_events_d);
      //  btn_cal_syn=(ButtonFloat)findViewById(R.id.calendar_syn);
        play_video=(ShimmerButton)findViewById(R.id.play_video);
      //  shimmer.start(play_video);

        btn_classer=(ShimmerButton)findViewById(R.id.classer);
        shimmer.start(btn_classer);

        btn_classer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder msg = new StringBuilder();
                msg.append( "Vous avez notez cet événement");
                msg.append( ":");
                msg.append("\n");
                msg.append(const1.getNom_events());
                msg.append("\n");
                msg.append( "À "+rating_num+" étoile(s)");
                msg.append("\n");
                msg.append("Confirmée !");

                Dialog dialog = new Dialog(Descriptions.this, const1.getNom_events(),msg.toString());

                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Create_fav_events().execute();
                    }
                });

                dialog.setOnCancelButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.show();
            }
        });


        intent_recu=getIntent();
        const1= (Cons_events) intent_recu.getSerializableExtra("item_loisir");
        System.out.println("data recu :" + const1.getNom_events());

        play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! const1.getVideo_id().isEmpty()) {
                    Intent gotovideo = new Intent(Descriptions.this, Youtube_activity_view.class);
                    gotovideo.putExtra("id_video", const1.getVideo_id());
                    startActivity(gotovideo);
                }
                else
                {
                    toast_material_youtube = new SuperActivityToast(Descriptions.this,
                            SuperToast.Type.BUTTON);
                    toast_material_youtube.setAnimations(SuperToast.Animations.FLYIN);
                    toast_material_youtube.setDuration(SuperToast.Duration.SHORT);
                    toast_material_youtube.setBackground(SuperToast.Background.RED);
                    toast_material_youtube.setTextSize(SuperToast.TextSize.MEDIUM);
                    toast_material_youtube.setText("Video non disponible !");
                    toast_material_youtube.setButtonIcon(SuperToast.Icon.Dark.UNDO,"Annuler");
                    toast_material_youtube.show();
                }
            }
        });



        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(Descriptions.this);
        // Loader image - will be shown before loading image
        int loader = R.drawable.kharja11;

        imgLoader.DisplayImage(const1.getImage_url(), loader,  aff);
        non_event.setText(const1.getNom_events());
        categorie_event.setText(const1.getEvent_type());
        lieu_event.setText(const1.getLieu());
        date_events.setText(const1.getDate()+"  @ "+const1.getTemps_event());
        description_events.setText(const1.getDescription_events());
        makeTextViewResizable(description_events, 2, "Afficher Plus", true);

       // temps_events.setText(const1.getTemps_event());
        System.out.println("distance discription: " + const1.getDistance());

        btn_cal_syn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // final LoadToast event_done = new LoadToast(Descriptions.this).setText("").show();
                new SnackBar(Descriptions.this,
                        "Voulez vous ajouter cet événement au calendrier ?",
                        "OUI", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  event_done.success();


                        String year = const1.getDate().substring(0, 4);
                        int year1 = Integer.valueOf(year);
                        String day = const1.getDate().substring(8, 10);
                        int day1 = Integer.valueOf(day);
                        String moins = const1.getDate().substring(5, 7);
                        int moins1 = Integer.valueOf(moins);
                        System.out.println("year: " + year1);
                        System.out.println("mon: " + moins1);
                        System.out.println("day: " + (day1 - 1));

                        String time_h = const1.getTemps_event().substring(0, 2);
                        String time_m = const1.getTemps_event().substring(3, 5);
                        int time_h1 = Integer.valueOf(time_h);
                        int time_m1 = Integer.valueOf(time_m);
                        System.out.println("time h: " + time_h1);
                        System.out.println("time m: " + time_m1);

                        //date and time
                        //-----------------------------------------------------------------
                        Calendar cal = Calendar.getInstance();
                        // cal.setTimeZone(TimeZone.getTimeZone("GMT-1"));
                        Calendar ca2 = Calendar.getInstance();
                        // ca2.setTimeZone(TimeZone.getTimeZone("GMT-1"));


                        //-----------------------------------------------------------------
                        Intent calIntent = new Intent(Intent.ACTION_INSERT);
                        calIntent.setType("vnd.android.cursor.item/event");
                        calIntent.putExtra(CalendarContract.Events.TITLE, const1.getNom_events());
                        calIntent.putExtra(CalendarContract.Events._ID, const1.getId_events());
                        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, const1.getLieu());
                        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, const1.getDescription_events());
                        calIntent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                        GregorianCalendar calDate = new GregorianCalendar(year1, moins1, (day1 - 1), time_h1, time_m1);
                        GregorianCalendar calDate_end = new GregorianCalendar(year1, moins1, day1, time_h1, time_m1);

                        cal.set(year1, moins1, (day1 - 1), time_h1, time_m1);
                        ca2.set(year1, moins1, day1, time_h1, time_m1);
                        // String date_e=const1.getDate()+" "+const1.getTemps_event();
                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                        //calIntent.putExtra(CalendarContract.Colors.COLOR, Color.parseColor("#FF4081"));
                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                ca2.getTimeInMillis());
                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                ca2.getTimeInMillis());

                        startActivity(calIntent);

////////////////////////////////////////////////////////////////////////////////////////////////////
/*
                         Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-1"));
                Date dt = null;
                Date dt1 = null;
                String date_e=const1.getDate()+" "+const1.getTemps_event();
                try {
                    dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_e);
                    dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_e);

                    Calendar beginTime = Calendar.getInstance();
                    cal.setTime(dt);

                    // beginTime.set(2013, 7, 25, 7, 30);
                    beginTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                            cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE));

                    Calendar endTime = Calendar.getInstance();
                    cal.setTime(dt1);

                    // endTime.set(2013, 7, 25, 14, 30);
                    // endTime.set(year, month, day, hourOfDay, minute);
                    endTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                            cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE));

                    ContentResolver cr = Descriptions.this.getContentResolver();
                    ContentValues values = new ContentValues();

                    values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                    values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
                    values.put(CalendarContract.Events.TITLE, const1.getNom_events());
                    values.put(CalendarContract.Events.DESCRIPTION, const1.getDescription_events());
                    values.put(CalendarContract.Events.CALENDAR_ID, const1.getId_events());
                    //-----------------------------------------------------------------------------
                    values.put(CalendarContract.CalendarAlerts.DTSTART, beginTime.getTimeInMillis());
                    values.put(CalendarContract.CalendarAlerts.DTEND, endTime.getTimeInMillis());
                    values.put(CalendarContract.CalendarAlerts.TITLE, const1.getNom_events());
                    values.put(CalendarContract.CalendarAlerts.DESCRIPTION, const1.getDescription_events());
                    values.put(CalendarContract.CalendarAlerts.CALENDAR_ID, const1.getId_events());
                    values.put(CalendarContract.CalendarAlerts.EVENT_TIMEZONE, "Tunisia");
                    values.put(CalendarContract.CalendarAlerts.EVENT_LOCATION, const1.getLieu());

                    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                    long eventID = Long.parseLong(uri.getLastPathSegment());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
/////////////////////////////////////////////////////////////////////////////////////////////////////


                    }
                }).show();

                //   event_done.error();






               /* Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-1"));
                Date dt = null;
                Date dt1 = null;
                String date_e=const1.getDate()+" "+const1.getTemps_event();
                try {
                    dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_e);
                    dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_e);

                    Calendar beginTime = Calendar.getInstance();
                    cal.setTime(dt);

                    // beginTime.set(2013, 7, 25, 7, 30);
                    beginTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                            cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE));

                    Calendar endTime = Calendar.getInstance();
                    cal.setTime(dt1);

                    // endTime.set(2013, 7, 25, 14, 30);
                    // endTime.set(year, month, day, hourOfDay, minute);
                    endTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                            cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE));

                    ContentResolver cr = Descriptions.this.getContentResolver();
                    ContentValues values = new ContentValues();

                    values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                    values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
                    values.put(CalendarContract.Events.TITLE, const1.getNom_events());
                    values.put(CalendarContract.Events.DESCRIPTION, const1.getDescription_events());
                    values.put(CalendarContract.Events.CALENDAR_ID, const1.getId_events());
                    //-----------------------------------------------------------------------------
                    values.put(CalendarContract.CalendarAlerts.DTSTART, beginTime.getTimeInMillis());
                    values.put(CalendarContract.CalendarAlerts.DTEND, endTime.getTimeInMillis());
                    values.put(CalendarContract.CalendarAlerts.TITLE, const1.getNom_events());
                    values.put(CalendarContract.CalendarAlerts.DESCRIPTION, const1.getDescription_events());
                    values.put(CalendarContract.CalendarAlerts.CALENDAR_ID, const1.getId_events());
                    values.put(CalendarContract.CalendarAlerts.EVENT_TIMEZONE, "Tunisia");
                    values.put(CalendarContract.CalendarAlerts.EVENT_LOCATION, const1.getLieu());

                    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                    long eventID = Long.parseLong(uri.getLastPathSegment());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
*/



              /*  char day1 = const1.getDate().charAt(0);
                char day2 = const1.getDate().charAt(1);
                String temp = Character.toString((char) (day1+day2));
                System.out.println("day 1: " +temp);

               Calendar beginTime = Calendar.getInstance();
                Time t = new Time(Time.getCurrentTimezone());
                t.setToNow();
                String date = t.format("%Y/%m/%d");
                System.out.println("date: " +date);
               // beginTime.set(yearInt, monthInt - 1, dayInt, 7, 30);



                ContentValues l_event = new ContentValues();
                l_event.put("calendar_id", const1.getId_events());
                l_event.put("title", const1.getNom_events());
                l_event.put("description",  const1.getDescription_events());
                l_event.put("eventLocation", const1.getLieu());
                l_event.put("dtstart", beginTime.getTimeInMillis());
                l_event.put("dtend", beginTime.getTimeInMillis());
                l_event.put("allDay", 0);
                l_event.put("rrule", "FREQ=YEARLY");
                // status: 0~ tentative; 1~ confirmed; 2~ canceled
                // l_event.put("eventStatus", 1);

                l_event.put("eventTimezone", "Tunisia");
                Uri l_eventUri;
                if (Build.VERSION.SDK_INT >= 8) {
                    l_eventUri = Uri.parse("content://com.android.calendar/events");
                } else {
                    l_eventUri = Uri.parse("content://calendar/events");
                }
                Uri l_uri = Descriptions.this.getContentResolver()
                        .insert(l_eventUri, l_event);
*/

            }
        });

        btn_gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGoogleMaps(Descriptions.this, const1.getLatutude(), const1.getLongitude(), const1.getNom_events());
            }
        });


        ratingBar = (ColoredRatingBar)findViewById(R.id.coloredRatingBar1);
        ratingBar.setOnRatingBarChangeListener(new ColoredRatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(ColoredRatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rating_num=rating;
                if (rating <= 1.6) {
                btn_classer.setText("Null");
                } else if (rating <= 3.2) {
                    btn_classer.setText("Passable");
                } else {
                    btn_classer.setText("Excellent");
                }
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



    //textview show more

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }
    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "Retour", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, "Afficher Plus", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_descriptions, menu);
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


    /**
     * Background Async Task to Create new product
     * */
    class Create_fav_events extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Setuser.this);
            pDialog.setMessage("User Ajouter..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }*/
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String nom_event = const1.getNom_events();
            String num_start = String.valueOf(rating_num);
            String id_events = String.valueOf(const1.getId_events());



            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nom_event", nom_event));
            params.add(new BasicNameValuePair("num_start", num_start));
            params.add(new BasicNameValuePair("id_event", id_events));


            // getting JSON Object
            // Note that create user url accepts POST method
            String jsonStr = servicehandler.makeServiceCall(url_fav_events, ServiceHandler.POST,params);

            // check log cat fro response
            Log.d("Create Response", jsonStr);

            // check for success tag
//            try {
//                int success = json.getInt(TAG_SUCCESS);
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done


        }

    }

    private class GetEvants_moy extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String id1= String.valueOf(const1.getId_events());
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("id_events", id1));
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url_statistique_events, ServiceHandler.GET,params1);

            Log.d("Response imp: ", "> " + jsonStr);


            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    all_events_par_moy = jsonObj.getJSONArray("events_moy");
                    Log.d("all events par moy: ", "> " + all_events_par_moy);

                    for (int i = 0; i < all_events_par_moy.length(); i++) {
                        JSONObject c = all_events_par_moy.getJSONObject(i);

                        Log.d("all object moy: ", "> " + c);

                        double starts=c.getDouble("AVG(num_start)");
                        Log.d("num moy: ", "> " + starts);
                        NUMSTART=Float.valueOf(String.valueOf(starts));
                        Log.d("num moyFILNAL: ", "> " + NUMSTART);

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
         ratingBar.setRating(NUMSTART);

        }
    }
}
