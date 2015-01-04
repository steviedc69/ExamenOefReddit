package com.example.hannes.fragmentlayouts;

/**
 * Created by hannes on 03/01/15.
 */
public class Post {
    private String titel;
    private String auteur;
    private String thumbnail;
    private String url;

    public Post(String titel, String auteur, String thumbnail, String url) {
        this.titel = titel;
        this.auteur = auteur;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public String getTitel() {
        return titel;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }
}
