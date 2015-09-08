package material_design.soussi.com.events_tunisie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import material_design.soussi.com.events_tunisie.menu_choix.FeatureCoverFlow;

/**
 * Created by Soussi on 08/07/2015.
 */
public class Categories_villes extends Fragment {
    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;

    private String title = "";



    public Categories_villes() {
    }

   /* @SuppressLint("ValidFragment")
    public Categories_Fragment(String title) {
        this.title = title;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample2, container, false);




        mData.add(new GameEntity(R.drawable.ville_ariana, R.string.ville_Ariana));
        mData.add(new GameEntity(R.drawable.ville_beja, R.string.ville_Beja));
        mData.add(new GameEntity(R.drawable.ville_ben_arous, R.string.ville_BenArous));
        mData.add(new GameEntity(R.drawable.ville_bizerte, R.string.ville_Bizerte));
        mData.add(new GameEntity(R.drawable.ville_gabes, R.string.ville_Gabes));
        mData.add(new GameEntity(R.drawable.ville_gafsa, R.string.ville_Gafsa));
        mData.add(new GameEntity(R.drawable.ville_jendouba, R.string.ville_Jendouba));
        mData.add(new GameEntity(R.drawable.ville_kairouan, R.string.ville_Kairouan));
        mData.add(new GameEntity(R.drawable.ville_kasserine, R.string.ville_Kasserine));
        mData.add(new GameEntity(R.drawable.ville_kebili, R.string.ville_Kebili));
        mData.add(new GameEntity(R.drawable.ville_kef, R.string.ville_Kef));
        mData.add(new GameEntity(R.drawable.ville_mahdia, R.string.ville_Mahdia));
        mData.add(new GameEntity(R.drawable.ville_manouba, R.string.ville_Manouba));
        mData.add(new GameEntity(R.drawable.ville_medenine, R.string.ville_Medenine));
        mData.add(new GameEntity(R.drawable.ville_monastir, R.string.ville_Monastir));
        mData.add(new GameEntity(R.drawable.ville_nabeul, R.string.ville_Nabeul));
        mData.add(new GameEntity(R.drawable.ville_sfax, R.string.ville_Sfax));
        mData.add(new GameEntity(R.drawable.ville_sidibouzid, R.string.ville_SidiBouzid));
        mData.add(new GameEntity(R.drawable.ville_siliana, R.string.ville_Siliana));
        mData.add(new GameEntity(R.drawable.ville_sousse, R.string.ville_Sousse));
        mData.add(new GameEntity(R.drawable.ville_tataouine, R.string.ville_Tataouine));
        mData.add(new GameEntity(R.drawable.ville_tozeur, R.string.ville_Tozeur));
        mData.add(new GameEntity(R.drawable.ville_tunis, R.string.ville_Tunis));
        mData.add(new GameEntity(R.drawable.ville_zaghouan, R.string.ville_Zaghouan));
       // mData.add(new GameEntity(R.drawable.img_ferstivals, R.string.ville_touts));
        mTitle = (TextSwitcher) rootView.findViewById(R.id.title1111);
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
        mCoverFlow = (FeatureCoverFlow) rootView.findViewById(R.id.coverflow1);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getActivity(),
                //         getResources().getString(mData.get(position).titleResId),
                //        Toast.LENGTH_SHORT).show();
                Intent filtre=new Intent(getActivity(),Choix_ville.class);
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
