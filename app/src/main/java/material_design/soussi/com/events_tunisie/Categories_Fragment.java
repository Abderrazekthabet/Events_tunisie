package material_design.soussi.com.events_tunisie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import material_design.soussi.com.events_tunisie.menu_choix.FeatureCoverFlow;
import material_design.soussi.com.events_tunisie.model.FragmentDrawer;

public class Categories_Fragment extends Fragment {
  private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;

    private String title = "";



    public Categories_Fragment() {
    }

   /* @SuppressLint("ValidFragment")
    public Categories_Fragment(String title) {
        this.title = title;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample, container, false);




      mData.add(new GameEntity(R.drawable.cinema3, R.string.cat_Cinema));
        mData.add(new GameEntity(R.drawable.clubs3, R.string.cat_Clubbing));
        mData.add(new GameEntity(R.drawable.foire3, R.string.cat_Foire));
        mData.add(new GameEntity(R.drawable.politique3, R.string.cat_Politique));
        mData.add(new GameEntity(R.drawable.soiree3, R.string.cat_Soiree));
        mData.add(new GameEntity(R.drawable.sport, R.string.cat_Sport));
        mData.add(new GameEntity(R.drawable.theatre3, R.string.cat_Theatre));
        mData.add(new GameEntity(R.drawable.animation3, R.string.cat_animations));
        mData.add(new GameEntity(R.drawable.competition3, R.string.cat_competitions));
        mData.add(new GameEntity(R.drawable.concert3, R.string.cat_concerts));
        mData.add(new GameEntity(R.drawable.exposition3, R.string.cat_exposition));
        mData.add(new GameEntity(R.drawable.festival3, R.string.cat_festivals));
        mTitle = (TextSwitcher) rootView.findViewById(R.id.title111);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        mAdapter = new CoverFlowAdapter(getActivity());
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) rootView.findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(getActivity(),
               //         getResources().getString(mData.get(position).titleResId),
                //        Toast.LENGTH_SHORT).show();
                Intent filtre=new Intent(getActivity(),Choix_cat.class);
                System.out.println("item type ==>"+getResources().getString(mData.get(position).titleResId));
                filtre.putExtra("type11", getResources().getString(mData.get(position).titleResId));
                startActivity(filtre);




            }
        });



        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(getResources().getString(mData.get(position).titleResId));
                System.out.println("pos text :"+mTitle);
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
                System.out.println("scrolling ...:"+mTitle);
            }
        });




        return rootView;
    }
}
