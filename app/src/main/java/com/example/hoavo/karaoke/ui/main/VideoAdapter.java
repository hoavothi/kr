package com.example.hoavo.karaoke.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hoavo.karaoke.R;
import com.example.hoavo.karaoke.models.Video;

import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 21/07/2017.
 */

class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    private ItemVideoListener mItemVideoListener;
    private List<Video> mVideos;
    private Context mContext;

    VideoAdapter(List<Video> videos, ItemVideoListener itemVideoListener, Context context) {
        mItemVideoListener = itemVideoListener;
        mVideos = videos;
        mContext = context;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        holder.mTvTitle.setText(mVideos.get(position).getTitle());
        holder.mTvDescription.setText(mVideos.get(position).getDecription());
        Glide.with(mContext)
                .load(mVideos.get(position).getThumnail())
                .centerCrop()
                .into(holder.mImgThumbnail);
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView mImgThumbnail;
        private TextView mTvTitle;
        private TextView mTvDescription;

        VideoHolder(View itemView) {
            super(itemView);
            mImgThumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitleVideo);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvChannelVideo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemVideoListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    class ChannelHolder extends RecyclerView.ViewHolder{
        private ImageView mImgThumbnail;
        private TextView mTvTitleChannel;
        private TextView mTvQuantity;
        private Button mBtnRegister;

         ChannelHolder(View itemView) {
            super(itemView);

        }
    }
}
