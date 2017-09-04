package com.example.hoavo.kotlin.ui.list_video

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hoavo.karaoke.R
import com.example.hoavo.kotlin.models.Item
import kotlinx.android.synthetic.main.item_channel.view.*
import kotlinx.android.synthetic.main.item_video.view.*
import java.util.regex.Pattern

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 31/07/2017.
 */
class VideoAdapter(var mVideos: List<Item>, val mContext: Context, val onItemVideoClickListener: OnItemVideoClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TYPE_VIDEO = 0
        val TYPE_CHANNEL = 1
        val TYPE_PLAY_LIST = 2
    }

    override fun getItemCount(): Int = mVideos.size

    override fun getItemViewType(position: Int): Int {

        if (!TextUtils.isEmpty(mVideos[position].video.id.channelId)) {
            return TYPE_CHANNEL
        } else if (!TextUtils.isEmpty(mVideos[position].video.id.playlistId)) {
            return TYPE_PLAY_LIST
        } else {
            return TYPE_VIDEO
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is ChannelHolder -> {
                holder.itemView.tvTitleChannel.text = mVideos[position].snippet.title
                Glide.with(mContext)
                        .load(mVideos[position].snippet.thumbnails.medium.url)
                        .centerCrop()
                        .into(holder.itemView.imgThumbnailChannel)
            }
            is VideoHolder -> {
                holder.itemView?.tvTitleVideo?.text = mVideos[position].snippet.title
                holder.itemView?.tvChannelVideo?.text = mVideos[position].snippet.channelTitle
                holder.itemView.tvQuantityVideo.text = mVideos[position].statistics?.viewCount?.getViewCount()
                holder.itemView.tvDurationVideo.text = getVideoDuration(mVideos[position].contentDetails?.duration ?: "")
                Glide.with(mContext)
                        .load(mVideos[position].snippet.thumbnails.medium.url)
                        .centerCrop()
                        .into(holder.itemView?.imgThumbnail)
            }
            is PlaylistHolder -> {
                holder.itemView?.tvTitleVideo?.text = mVideos[position].snippet.title
                holder.itemView?.tvChannelVideo?.text = mContext.getString(R.string.youtube)
                holder.itemView?.tvPlaylistQuantity?.visibility = View.VISIBLE
                holder.itemView?.tvPlaylistQuantity?.text = mContext.getString(R.string.video_count,mVideos[position].snippet.totalPlaylist.toString())
                holder.itemView?.tvDurationVideo?.visibility = View.GONE
                Glide.with(mContext)
                        .load(mVideos[position].snippet.thumbnails.medium.url)
                        .centerCrop()
                        .into(holder.itemView?.imgThumbnail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_CHANNEL -> return ChannelHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_channel, parent, false))
            TYPE_VIDEO -> return VideoHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_video, parent, false))
            else -> return PlaylistHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_video, parent, false))
        }
    }

    private fun getVideoDuration(duration: String): String {
        val pattern = Pattern.compile("[A-Z]+")
        val list: Array<String> = pattern.split(duration.trim())
        when (list.size) {
            4 -> return list[1] + ":" + if (list[2].length > 1) list[2] else {
                "0" + list[2]
            } + ":" + if (list[3].length > 1) list[3] else {
                "0" + list[3]
            }
            3 -> return list[1] + ":" + if (list[2].length > 1) list[2] else {
                "0" + list[2]
            }
            2 -> return "0:" + list[1]
            else -> return ""
        }
    }

    private inner class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemVideoClickListener.onItemClick(TYPE_VIDEO, mVideos[layoutPosition])
            }
        }
    }

    private inner class ChannelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemVideoClickListener.onItemClick(TYPE_CHANNEL, mVideos[layoutPosition])
            }
        }
    }

    private inner class PlaylistHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemVideoClickListener.onItemClick(TYPE_PLAY_LIST, mVideos[layoutPosition])
            }
        }
    }
}

 fun String.getViewCount(): String {
    var vC = this
    if (this.length.compareTo(3) == 1) {
        vC = ""
        var view = this.length.rem(3)
        when (view) {
            1 -> vC = this[0] + ""
            2 -> vC = this.substring(0, 1) + ""
            0 -> vC = this.substring(0, 2) + ""
        }
        if (view == 0) {
            view = 3
        }
        for (i in view..this.length - 3 step 3) {
            vC += "." + this.substring(i, i + 3)
        }
    }
    return vC
}
