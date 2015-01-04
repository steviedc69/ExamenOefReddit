package com.example.hannes.fragmentlayouts;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hannes on 03/01/15.
 */
public class TitlesFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 0;
    private String url1 = "http://www.reddit.com/r/funny.json";
    private HandleJSON obj;
    ArrayList<Post> posts = new ArrayList<Post>();
    CustomAdapter aa;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,SuperHeroInfo.NAMES);
        this.open();
        aa = new CustomAdapter(posts);

        setListAdapter(aa);
        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if(savedInstanceState != null){
            mCurCheckPosition = savedInstanceState.getInt("curChoice",0);
        }
        if(mDualPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mCurCheckPosition);
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice",mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       showDetails(position);
    }

    void showDetails(int index){
        mCurCheckPosition = index;
        if(mDualPane){
            getListView().setItemChecked(index,true);
            DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);

            if(details == null || details.getShownIndex() != index){
                //Ook de urlString meegeven naar de details
                //details = DetailsFragment.newInstance(index);
                details = DetailsFragment.newInstance(index,posts.get(index).getUrl());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details,details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(),DetailsActivity.class);
            intent.putExtra("index",index);
            intent.putExtra("url",posts.get(index).getUrl());
            startActivity(intent);
        }



    }
    public void open(){
        obj = new HandleJSON(url1);
        obj.fetchJSON();
        while(obj.parsingComplete);
        this.posts = obj.getPosts();

    }
}
