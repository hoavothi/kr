package com.example.hoavo.karaoke.ui.main;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hoavo.karaoke.models.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 28/07/2017.
 */

public class DataFromApi extends AsyncTask<String, Void, ArrayList<Video>> {
    private static final String API_URI = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=taylor+swift&maxResults=";
    private static final String PLAYLIST_ID = "25";
    private static final String KEY_BROWSE = "AIzaSyDDH313GMgdJYGDkZ3S7xlZXcoR962Q2gE";
    private VideoFragment mVideoFragment;

    DataFromApi(VideoFragment videoFragment) {
        mVideoFragment = videoFragment;
    }

    @Override
    protected ArrayList<Video> doInBackground(String... params) {
        ArrayList<Video> listVideo = new ArrayList<>();
        String videoId = "", playlistId = "", channelId = "";
        URL jSonUrl;
        URLConnection jSonConnect;
        try {
            jSonUrl = new URL(API_URI + PLAYLIST_ID + "&key=" + KEY_BROWSE);
            jSonConnect = jSonUrl.openConnection();
            InputStream inputstream = jSonConnect.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputstream.close();
            String jSontxt = stringBuilder.toString();
            Log.d("nnnnnnnnnnnnnn", "doInBackground: " + jSontxt);

            JSONObject jsonobject = new JSONObject(jSontxt);
            JSONArray allItem = jsonobject.getJSONArray("items");
            Log.d("nnnnnnnnnnnnn", "alll: " + allItem.length());
            for (int i = 0; i < allItem.length(); i++) {
                videoId = "";
                playlistId = "";
                channelId = "";
                JSONObject item = allItem.getJSONObject(i);
                JSONObject snippet = item.getJSONObject("snippet");
                String title = snippet.getString("title");              // Get Title Video
                Log.d("nnnnnnnnnn", "  " + title);
                String decription = snippet.getString("description");   // Get Description
                JSONObject thumbnails = snippet.getJSONObject("thumbnails");    //Get Url Thumnail
                JSONObject thumnailsIMG = thumbnails.getJSONObject("medium");
                String thumnailurl = thumnailsIMG.getString("url");

                JSONObject resourceId = item.getJSONObject("video");    //Get ID Video
                if (resourceId.has("playlistId") && resourceId.optString("playlistId") != null) {
                    playlistId = resourceId.getString("playlistId");
                }
                if (resourceId.has("videoId") && resourceId.optString("videoId") != null) {
                    videoId = resourceId.getString("videoId");
                }
                if (resourceId.has("channelId") && resourceId.optString("channelId") != null) {
                    channelId = resourceId.getString("channelId");
                }

                Video video = new Video();
                video.setTitle(title);
                video.setThumnail(thumnailurl);
                video.setDecription(decription);
                video.setUrlVideo(videoId);
                video.setUrlPlaylist(playlistId);
                video.setUrlChannel(channelId);

                //Add video to List
                listVideo.add(video);
                Log.d("nnnnnnnnnnnnn", "list: " + listVideo.size());
            }
            if (listVideo.size() != 0) {
                Log.d("nnnnnnnnnnnnnnnnn", "doInBackground: bbbbbbbbbbbbbbbb");
            }
        } catch (Exception e) {
            Log.d("nnnnnnnnnn", "exceptuon: " + e.toString());
        }
        if (listVideo.size() == 0) {
            Log.d("nnnnnnnnnnnnnnnnn", " aaaaaaaaaaa");
        }
        return listVideo;
    }

    @Override
    protected void onPostExecute(ArrayList<Video> videos) {
        super.onPostExecute(videos);
        mVideoFragment.setAdapter(videos);
    }
}
