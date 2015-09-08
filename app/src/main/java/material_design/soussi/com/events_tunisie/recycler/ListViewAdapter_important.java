package material_design.soussi.com.events_tunisie.recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import material_design.soussi.com.events_tunisie.Cons_events;
import material_design.soussi.com.events_tunisie.R;
import material_design.soussi.com.events_tunisie.imageweb.ImageLoader;

/**
 * Created by Soussi on 05/05/2015.
 */
public class ListViewAdapter_important extends BaseAdapter {

    private Activity activity;
    private ArrayList<Cons_events> data;
    private static LayoutInflater inflater=null;
    private ImageLoader imageLoader;

    public ListViewAdapter_important(Activity a, ArrayList<Cons_events> data) {
        activity = a;
        this.data=data;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_import_events, null);

        TextView title = (TextView)vi.findViewById(R.id.nom_events_important); // title
       // TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
       // TextView ouverture = (TextView)vi.findViewById(R.id.tv_ouverture); // duration
       // TextView fermeture = (TextView)vi.findViewById(R.id.tv_fermeture);

        ImageView thumb_image=(ImageView)vi.findViewById(R.id.imageaffiche_important); // thumb image

        Cons_events loisirItem = new Cons_events();
        loisirItem = data.get(position);

        // Setting all values in listview
        title.setText(loisirItem.getNom_events());

      //  artist.setText(loisirItem.getAdress());
       // ouverture.setText(loisirItem.getClint());
       // fermeture.setText(loisirItem.getClint());

        int loader = R.drawable.map_pin_;

        imageLoader.DisplayImage(loisirItem.getImage_url(),loader, thumb_image);
        return vi;
    }

}
