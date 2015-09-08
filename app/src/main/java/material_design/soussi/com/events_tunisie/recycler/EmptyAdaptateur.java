package material_design.soussi.com.events_tunisie.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import material_design.soussi.com.events_tunisie.Cons_events;
import material_design.soussi.com.events_tunisie.Descriptions;
import material_design.soussi.com.events_tunisie.R;
import material_design.soussi.com.events_tunisie.imageweb.ImageLoader;

/**
 * Created by Soussi on 08/07/2015.
 */
public class EmptyAdaptateur extends RecyclerView.Adapter<EmptyAdaptateur.EventsViewHolder> {

    private Context context;
   // private ArrayList<Cons_events> items;


    public EmptyAdaptateur(Context context) {
        this.context = context;
     //   this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_empty, parent, false);
        EventsViewHolder viewHolder = new EventsViewHolder(context, view);
        return viewHolder;


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventsViewHolder viewHolder, int position) {
     //   Cons_events country = items.get(position);
     /*   viewHolder.event_name.setText(country.getNom_events());
        viewHolder.event_date.setText(country.getDate());
        viewHolder.event_lieu.setText(country.getLieu());
        viewHolder.event_type.setText(country.getEvent_type());
        viewHolder.event_temps.setText("@ "+country.getTemps_event());
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(context);
        // Loader image - will be shown before loading image
        int loader = R.drawable.map_pin_;

        imgLoader.DisplayImage(country.getImage_url(), loader, viewHolder.ivFlag);*/

    }

    @Override
    public int getItemCount() {
        return 0;
    }

//    @Override
//   public int getItemCount() {
//        return items.size();
 //   }


    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{//

        private Context context;
 //       public TextView event_name,event_lieu,event_date,event_type,event_temps;
 //       public ImageView ivFlag;



        public EventsViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
//            event_name = (TextView) itemView.findViewById( R.id.event_name);
//            event_date = (TextView) itemView.findViewById( R.id.event_date);
//            event_lieu = (TextView) itemView.findViewById( R.id.event_lieu);
//            event_type = (TextView) itemView.findViewById( R.id.event_type);
//            ivFlag = (ImageView) itemView.findViewById(R.id.list_image1);
//            event_temps = (TextView) itemView.findViewById( R.id.event_temps1);
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(context, event_name.getText().toString(), Toast.LENGTH_SHORT).show();
//            Intent desc =new Intent(context, Descriptions.class);
//            int position = getPosition();
//
//            desc.putExtra("item_loisir", items.get(position));
//            context.startActivity(desc);

        }


    }



}