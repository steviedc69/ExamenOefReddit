package com.example.hannes.fragmentlayouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hannes on 03/01/15.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<Post> posts;
    public CustomAdapter(ArrayList<Post> posts){
        this.posts = posts;
    }
    @Override
    public int getCount(){
        return posts.size();

    }
    @Override
    public Post getItem(int i){
        return posts.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup){
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            v = inflater.inflate(R.layout.listitem,viewGroup,false);
        }

        TextView titel = (TextView) v.findViewById(R.id.title);
        TextView auteur = (TextView) v.findViewById(R.id.auteur);

        Post post = posts.get(i);
        String titelPost = post.getTitel();
        String auteurPost = post.getAuteur();

        titel.setText(titelPost);
        auteur.setText(auteurPost);

        return v;


    }
}
