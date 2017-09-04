package com.example.hoavo.karaoke.ui.main;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.hoavo.karaoke.R;
import com.example.hoavo.karaoke.api.HttpHandler;
import com.example.hoavo.karaoke.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 21/07/2017.
 */
class VideoDataFromApi extends AsyncTask<Void, Void, ArrayList<Video>> {
    private static final String TAG = VideoDataFromApi.class.getName();
    private static final String API_URI = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=snsd&maxResults=20";
    private static final String KEY_BROWSE = "AIzaSyCuGOJBJlAwk_Nq9JkvCGCz8dtXo9MnsLU";
    private MainActivity mMainActivity;

    VideoDataFromApi(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    protected ArrayList<Video> doInBackground(Void... params) {
        ArrayList<Video> videos = new ArrayList<>();
        String url = API_URI + "&key=" + KEY_BROWSE;
        HttpHandler httpHandler = new HttpHandler();
        String resultApi = httpHandler.makeServiceCall(url);
        if (resultApi != null) {
            try {
                String title = " ", description = " ", videoId = " ", thumbnailurl = " ";
                JSONObject jsonObj = new JSONObject(resultApi);

                // Getting JSON Array node
                if (jsonObj.has("items") && jsonObj.optJSONArray("items") != null) {
                    JSONArray contacts = jsonObj.getJSONArray("items");

                    // Looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        if (c.has("snippet") && c.optJSONObject("snippets") != null) {
                            JSONObject snippet = c.getJSONObject("snippet");
                            if (snippet.has("title") && snippet.optString("title") != null) {
                                title = snippet.getString("title");
                            }
                            if (snippet.has("description") && snippet.optString("description") != null) {
                                description = snippet.getString("description");
                            }
                            if (snippet.has("thumbnails") && snippet.optJSONObject("thumbnails") != null) {
                                JSONObject thumbnails = snippet.getJSONObject("thumbnails");    //Get Url Thumnail
                                if(thumbnails.has("medium")&&thumbnails.optString("medium")!=null) {
                                    JSONObject thumbnailsIMG = thumbnails.getJSONObject("medium");
                                    if(thumbnailsIMG.has("url")&&thumbnailsIMG.optString("url")!=null){
                                        thumbnailurl=thumbnailsIMG.getString("url");
                                    }
                                }
                            }
                        }
                        if (c.has("video") && c.optString("video") != null) {
                            JSONObject resourceId = c.getJSONObject("video");    //Get ID Video
                            if(resourceId.has("playlistId")&&resourceId.optString("playlistId")!=null) {
                                videoId = resourceId.getString("playlistId");
                            }
                        }

                        Video video = new Video();
                        video.setTitle(title);
                        video.setThumnail(thumbnailurl);
                        video.setDecription(description);
                        video.setUrlVideo(videoId);
                        videos.add(video);

                    }
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mMainActivity.getApplicationContext(),
                                mMainActivity.getString(R.string.json_passing_error, e.getMessage()),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            mMainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mMainActivity.getApplicationContext(),
                            mMainActivity.getString(R.string.error_http_json),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
        return videos;
    }

    @Override
    protected void onPostExecute(ArrayList<Video> videos) {
        super.onPostExecute(videos);

    }
}
