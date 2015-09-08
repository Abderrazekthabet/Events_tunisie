package material_design.soussi.com.events_tunisie.cal_fav;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import material_design.soussi.com.events_tunisie.Const_calendar;
import material_design.soussi.com.events_tunisie.R;
import material_design.soussi.com.events_tunisie.imageweb.ImageLoader;

/**
 * Created by Soussi on 30/04/2015.
 */
public class Cal_recy_adap extends RecyclerView.Adapter<Cal_recy_adap.EventsViewHolder> {

    private Context context;
    private ArrayList<Const_calendar> items;


    public Cal_recy_adap(Context context, ArrayList<Const_calendar> items) {
        this.context = context;
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        EventsViewHolder viewHolder = new EventsViewHolder(context, view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventsViewHolder viewHolder, int position) {
        Const_calendar country = items.get(position);
        viewHolder.event_name.setText(country.getName());
        viewHolder.event_date.setText(country.getLocation());
        viewHolder.event_lieu.setText(date_and_time(country.getEVENT_BEGIN_TIME()));
       // viewHolder.event_type.setText(country.getDisplayName());
        viewHolder.event_temps.setText("@ "+country.getDisplayName());
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(context);
        // Loader image - will be shown before loading image
        int loader = R.drawable.map_pin_;

       // imgLoader.DisplayImage(country.getImage_url(), loader, viewHolder.ivFlag);

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

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{//

        private Context context;
        public TextView event_name,event_lieu,event_date,event_type,event_temps;
        public ImageView ivFlag;



        public EventsViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            event_name = (TextView) itemView.findViewById( R.id.event_name);
            event_date = (TextView) itemView.findViewById( R.id.event_date);
            event_lieu = (TextView) itemView.findViewById( R.id.event_lieu);
            event_type = (TextView) itemView.findViewById( R.id.event_type);
            ivFlag = (ImageView) itemView.findViewById(R.id.list_image1);
            event_temps = (TextView) itemView.findViewById( R.id.event_temps1);
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(context, event_name.getText().toString(), Toast.LENGTH_SHORT).show();
          //  Intent desc =new Intent(context, Descriptions.class);
            int position = getPosition();

          //  desc.putExtra("item_loisir", items.get(position));
          //  context.startActivity(desc);

        }


    }



}
