package material_design.soussi.com.events_tunisie;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.view.View.OnClickListener;

import material_design.soussi.com.events_tunisie.cal_fav.Cal_recy_adap;
import material_design.soussi.com.events_tunisie.cal_fav.CalendarAdapter;
import material_design.soussi.com.events_tunisie.cal_fav.CalendarCollection;
import material_design.soussi.com.events_tunisie.recycler.Cathegorie_adaptateur;


public class Favoris_fragment extends Fragment {
   private CalendarView calendar;
   private CalendarContentResolver cursor_cal;
   private ArrayList<Const_calendar> calendarList=new ArrayList<Const_calendar>();

    public GregorianCalendar cal_month, cal_month_copy;
    private CalendarAdapter cal_adapter;
    private TextView tv_month;
    private String dateString;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mLayoutManager;

    public Favoris_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_favoris_fragment, container, false);
       // calendar = (CalendarView) rootView.findViewById(R.id.calendar);

        cursor_cal=new CalendarContentResolver(getActivity());
        calendarList=cursor_cal.getCalendars();

        List<Const_calendar> contacts = calendarList;
        for (Const_calendar cn : contacts) {
           // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
           /* try {
                 dateString = String.valueOf(convert_utc_to_Date(cn.EVENT_BEGIN_TIME));
                Log.i("lists date", dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            String log = "Id: "+cn.getCal_id()+" ,displayName: " + cn.getDisplayName()+" name :"+cn.getName()+" location :"+cn.getLocation()+
                    " discription :"+cn.getDiscription()+" time 1 :"+date_and_time(cn.EVENT_BEGIN_TIME)+" time 2 :"+date_and_time(cn.getEVENT_END_TIME())
                    ;
            Log.i("lists events", log);
          //  CalendarCollection.date_collection_arr=new ArrayList<CalendarCollection>();
          //  CalendarCollection.date_collection_arr.add(new CalendarCollection(date_and_time(cn.EVENT_BEGIN_TIME),cn.getName()));

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_viewcalandar);
        Cal_recy_adap chategorieadaptateur = new Cal_recy_adap(getActivity(), calendarList);
        recyclerView.setAdapter(chategorieadaptateur);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//
        // recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);


       // CalendarAdapter adap=new CalendarAdapter();
       /* new Handler().post(new Runnable() {
            @Override
            public void run() {
                List<Const_calendar> contacts = calendarList;
                for (Const_calendar cn : contacts) {
                    // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
           *//* try {
                 dateString = String.valueOf(convert_utc_to_Date(cn.EVENT_BEGIN_TIME));
                Log.i("lists date", dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }*//*
                 //   String log = "Id: "+cn.getCal_id()+" ,displayName: " + cn.getDisplayName()+" name :"+cn.getName()+" location :"+cn.getLocation()+
                  //          " discription :"+cn.getDiscription()+" time 1 :"+date_and_time(cn.EVENT_BEGIN_TIME)+" time 2 :"+cn.getDisplayName()
                  //          ;
                  //  Log.i("lists events", log);
                    CalendarCollection.date_collection_arr=new ArrayList<CalendarCollection>();
                    CalendarCollection.date_collection_arr.add(new CalendarCollection(date_and_time(cn.EVENT_BEGIN_TIME),cn.getName()));

                }

            }
        });*/

        /*CalendarCollection.date_collection_arr=new ArrayList<CalendarCollection>();
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2015-04-01","00000"));
        CalendarCollection.date_collection_arr.add(new CalendarCollection("2015-04-04","events"));*/

     /*   cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        cal_adapter = new CalendarAdapter(getActivity(), cal_month, calendarList);


        tv_month = (TextView) rootView.findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));

        ImageButton previous = (ImageButton) rootView.findViewById(R.id.ib_prev);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageButton next = (ImageButton) rootView.findViewById(R.id.Ib_next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });


        GridView gridview = (GridView) rootView.findViewById(R.id.gv_calendar);
        gridview.setAdapter(cal_adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);
                String selectedGridDate = CalendarAdapter.day_string
                        .get(position);

                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*","");
                int gridvalue = Integer.parseInt(gridvalueString);

                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v,position);


                ((CalendarAdapter) parent.getAdapter()).getPositionList(selectedGridDate, getActivity());
            }

        });*/

        ////////////////////////////////////////////////////////////////////////





        return rootView;
    }

    public static String convertDate(String dateInMilliseconds,String dateFormat) {

        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();

    }

    public static Date convert_utc_to_Date(String dateInMilliseconds) throws ParseException {
        if(dateInMilliseconds!=null) {
            Calendar cal = Calendar.getInstance();
// Via this, you're setting the timezone for the time you're planning to do the conversion
            cal.setTimeZone(TimeZone.getTimeZone("Europe/London"));
            cal.setTimeInMillis(Long.parseLong(dateInMilliseconds));
// The date is in your home timezone (London, in this case)
            Date date1 = cal.getTime();


           // TimeZone destTz = TimeZone.getTimeZone("GMT");
// Best practice is to set Locale in case of messing up the date display
            SimpleDateFormat destFormat = new SimpleDateFormat("HH:mm MM/dd/yyyy", Locale.UK);
          //  destFormat.setTimeZone(destTz);
// Then we do the conversion to convert the date you provided in milliseconds to the GMT timezone


            return destFormat.parse(String.valueOf(date1));
        }
        else
        {
            return null;
        }

    }

    public String date_and_time(String millis)
    {
        if(millis !=null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");//"hh:ss MM/dd/yyyy"
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            String result = df.format(Long.parseLong( millis));
            return result;
        }
        else
        {
            return "";
        }
    }


    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1),
                    cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1),
                    cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }





   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris_fragment);
    }*/


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favoris_fragment, menu);
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
