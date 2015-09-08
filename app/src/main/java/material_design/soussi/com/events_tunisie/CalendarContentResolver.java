package material_design.soussi.com.events_tunisie;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Soussi on 28/04/2015.
 */
public class CalendarContentResolver {

    private ArrayList<Const_calendar> calendarList=new ArrayList<Const_calendar>();
    public static final String[] FIELDS = { CalendarContract.Events.TITLE,
            CalendarContract.Events.CALENDAR_DISPLAY_NAME,
            CalendarContract.Events.CALENDAR_COLOR,
            CalendarContract.Events.VISIBLE,
           // CalendarContract.Calendars.CALENDAR_LOCATION,
            CalendarContract.Events._ID,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.EVENT_LOCATION,
           CalendarContract.Events.LAST_DATE
           // CalendarContract.EXTRA_EVENT_END_TIME
           };
    private String[] FIELDS2={"title", "description", "dtstart","calendar_id", "dtend","eventTimezone", "eventLocation",CalendarContract.Events.CALENDAR_DISPLAY_NAME };

    public static final Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/events");//content://com.android.calendar/events

    ContentResolver contentResolver;
    Set<String> calendars = new HashSet<String>();

    public  CalendarContentResolver(Context ctx) {
        contentResolver = ctx.getContentResolver();
    }

    public ArrayList<Const_calendar> getCalendars() {
        // Fetch a list of all calendars sync'd with the device and their display names
        Cursor cursor = contentResolver.query(CALENDAR_URI, FIELDS2, null, null, "dtstart ASC");

        try {
            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
//                    String name = cursor.getString(0);
//                    String displayName = cursor.getString(1);
//                    // This is actually a better pattern:
//                    String color = cursor.getString(cursor.getColumnIndex(
//                            CalendarContract.Calendars.CALENDAR_COLOR));
//                    Boolean selected = !cursor.getString(3).equals("0");
//
//                    String cla_id = cursor.getString(4);
//                    String cla_discription = cursor.getString(5);
//                    String location = cursor.getString(6);
//                  //  String events_begin_time = cursor.getString(7);
//                  //  String events_end_time = cursor.getString(8);
                    Const_calendar item =new Const_calendar();

//                    item.setCal_id(cursor.getString(4));//
//                    item.setDisplayName(cursor.getString(1));//
//                    item.setName(cursor.getString(0));//
//                    item.setColor(cursor.getString(cursor.getColumnIndex(
//                            CalendarContract.Calendars.CALENDAR_COLOR)));
//                  item.setLocation(cursor.getString(6));//
//                    item.setDiscription(cursor.getString(5));//
//                   item.setEVENT_BEGIN_TIME(cursor.getString(7));
//
                    ///////////////////////////////////////////////////////////////////////////
                    Const_calendar item2 =new Const_calendar();

                    item2.setCal_id(cursor.getString(3));//
                    item2.setDisplayName(cursor.getString(7));//
                    item2.setName(cursor.getString(0));//
                   // item2.setColor(cursor.getString(cursor.getColumnIndex(
                    //        CalendarContract.Calendars.CALENDAR_COLOR)));
                    item2.setLocation(cursor.getString(5));//
                    item2.setDiscription(cursor.getString(1));//
                    item2.setEVENT_BEGIN_TIME(cursor.getString(2));
                    item2.setEVENT_END_TIME(cursor.getString(4));
                    calendarList.add(item2);
                }
            }
        } catch (AssertionError ex) {
            // TODO: log exception and bail
        }
        return calendarList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////
}