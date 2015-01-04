package com.example.hannes.fragmentlayouts;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by hannes on 03/01/15.
 */
public class DetailsFragment extends Fragment {
    ImageView img;
    ProgressDialog progressDialog;
    public static DetailsFragment newInstance(int index,String url){

        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        //in plaats van de index te geven. Geef object of url mee
        args.putInt("index",index);
        args.putString("url",url);
        f.setArguments(args);
        return f;

    }

    public int getShownIndex(){
        return getArguments().getInt("index",0);
    }
    public String getUrl() {
        return getArguments().getString("url","test");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        img = new ImageView(getActivity());
        //In de on create moet er nu een afbeelding komen
        TextView textViewUrl = new TextView(getActivity());

        ScrollView scroller = new ScrollView(getActivity());
        //TextView textView = new TextView(getActivity());

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4,getActivity().getResources().getDisplayMetrics());
        scroller.setPadding(padding,padding,padding,padding);
        //scroller.addView(textView);
        //textView.setText(SuperHeroInfo.HISTORY[getShownIndex()]);
        //Toegevoegd
        new LoadImage().execute(getUrl());
        scroller.addView(img);
        //textViewUrl.setText(getUrl());

        //return scroller;
        return scroller;
    }

    private class LoadImage extends AsyncTask<String,String,Bitmap> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading image...");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... args){
            Bitmap bitmap = null;
         try {
             bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

         }catch (Exception e){
             e.printStackTrace();
         }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image){
            if(image != null){
                img.setImageBitmap(image);
                progressDialog.dismiss();
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Image does not exist or no network",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
