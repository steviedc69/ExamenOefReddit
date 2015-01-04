package com.example.hannes.fragmentlayouts;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hannes on 03/01/15.
 */
public class HandleJSON {
    private String urlString = null;
    private ArrayList<Post> posts = new ArrayList <Post>();
    public volatile boolean parsingComplete = true;

    public HandleJSON(String url){
        this.urlString = url;
    }



    @SuppressLint("newApi")
    public void readAndParseJSON(String in){

        //Moet in een loop zitten waarbij er meerdere Post objecten worden aangemaakt
        //Waarna er een lijst moet zijn van allemaal Posts. Waarbij je per post de titel, auteur en thumbnail toont.
        //Bij het klikken moet je de url te zien krijgen als afbeelding

        try {
            JSONObject reader = new JSONObject(in);
            JSONObject post = reader.getJSONObject("data");
            JSONArray post2 = post.getJSONArray("children");

            for(int i = 1;i<=post2.length();i++)
            {
                JSONObject postPerUnit = (JSONObject) post2.get(i);
                JSONObject inhoudPost = postPerUnit.getJSONObject("data");

                String titel = inhoudPost.getString("title");
                String auteur = inhoudPost.getString("author");
                String thumbnail = inhoudPost.getString("thumbnail");
                String urlFoto = inhoudPost.getString("url");

                Post p = new Post(titel,auteur,thumbnail,urlFoto);
                posts.add(p);

                parsingComplete = false;
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Post> getPosts(){
        return this.posts;
    }

    public void fetchJSON(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data);
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
