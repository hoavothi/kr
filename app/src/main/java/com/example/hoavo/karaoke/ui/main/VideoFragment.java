package com.example.hoavo.karaoke.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoavo.karaoke.R;
import com.example.hoavo.karaoke.models.Video;

import java.util.ArrayList;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 21/07/2017.
 */
public class VideoFragment extends Fragment implements ItemVideoListener {
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video1, container,false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewVideo);
        new DataFromApi(this).execute("dog");
        return v;
    }

    public void setAdapter(ArrayList<Video> videos) {
        VideoAdapter videoAdapter = new VideoAdapter(videos, this, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(videoAdapter);
    }

    @Override
    public void onItemClick(int position) {

    }
}
