package com.example.hannes.fragmentlayouts;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by hannes on 03/01/15.
 */
public class DetailsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
        if (savedInstanceState == null){
            //Nieuw detailfragment wordt aangemaakt
            DetailsFragment details = new DetailsFragment();
            //Extra argumenten worden meegegeven met het detail venster: Dit gaat het "Post-object" moeten zijn
            //Kan ook de url-String
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content,details).commit();

        }
    }
}
