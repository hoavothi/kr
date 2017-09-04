package com.example.hoavo.karaoke.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 17/07/2017.
 */

public class Video implements Parcelable {
    private String urlVideo;
    private String thumnail;
    private String title;
    private String decription;
    private String urlChannel;
    private String urlPlaylist;

    public Video() {
    }

    public Video(String urlVideo, String thumnail, String title, String decription, String urlChannel, String urlPlaylist) {
        this.urlVideo = urlVideo;
        this.thumnail = thumnail;
        this.title = title;
        this.decription = decription;
        this.urlChannel = urlChannel;
        this.urlPlaylist = urlPlaylist;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getUrlChannel() {
        return urlChannel;
    }

    public void setUrlChannel(String urlChannel) {
        this.urlChannel = urlChannel;
    }

    public String getUrlPlaylist() {
        return urlPlaylist;
    }

    public void setUrlPlaylist(String urlPlaylist) {
        this.urlPlaylist = urlPlaylist;
    }

    public static Creator<Video> getCREATOR() {
        return CREATOR;
    }

    protected Video(Parcel in) {
        urlVideo = in.readString();
        thumnail = in.readString();
        title = in.readString();
        decription = in.readString();
        urlChannel = in.readString();
        urlPlaylist = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(urlVideo);
        dest.writeString(thumnail);
        dest.writeString(title);
        dest.writeString(decription);
        dest.writeString(urlChannel);
        dest.writeString(urlPlaylist);
    }
}
