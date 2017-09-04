package com.example.hoavo.kotlin.api

import android.text.TextUtils
import android.util.Log
import com.example.hoavo.kotlin.models.*
import com.example.hoavo.kotlin.ui.other.SearchVideo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 15/08/2017.
 * Create class contain method to request api
 */
class RequestApi(val mOnAfterGetApi: OnAfterGetApi) {
    private var mItemVideos: MutableList<Item> = mutableListOf()

    companion object {
        val TAG = RequestApi::class.java.name
    }

    fun searchVideo(onMyServiceGetApi: OnMyServiceGetApi?, callSearchService: Call<VideoSearchFromApi>) {
        var videos: List<Video> = listOf()
        callSearchService.enqueue(object : Callback<VideoSearchFromApi> {
            override fun onResponse(call: Call<VideoSearchFromApi>?, response: Response<VideoSearchFromApi>?) {
                videos = response?.body()?.items ?: videos
                for (video in videos) {
                    if (!TextUtils.isEmpty(video.id.videoId)) {
                        searchDetailVideo(video, onMyServiceGetApi!!)
                    } else if (!TextUtils.isEmpty(video.id.channelId)) {
                        searchDetailChannel(null, video, false, onMyServiceGetApi!!)
                    } else if (!TextUtils.isEmpty(video.id.playlistId)) {
                        searchPlaylist(video, onMyServiceGetApi!!)
                    }
                }
            }

            override fun onFailure(call: Call<VideoSearchFromApi>?, t: Throwable?) {
                Log.d(TAG, "fail on retrofit when search list video " + t.toString())
            }
        })
    }

    private fun searchDetailVideo(video: Video, onMyServiceGetApi: OnMyServiceGetApi) {
        var items: List<Item> = arrayListOf()
        val callVideoService = onMyServiceGetApi.getVideoDetail("snippet,contentDetails,statistics", video.id.videoId, 20, SearchVideo.KEY_BROWSER)
        callVideoService.enqueue(object : Callback<VideoDetailFromApi> {
            override fun onResponse(call: Call<VideoDetailFromApi>?, response: Response<VideoDetailFromApi>?) {
                items = response?.body()?.items ?: items
                items[0].video = video
                searchDetailChannel(items[0], null, true, onMyServiceGetApi)
            }

            override fun onFailure(call1: Call<VideoDetailFromApi>?, t: Throwable?) {
                Log.d(TAG, "fail on retrofit when search detail video" + t.toString())
            }
        })
    }

    private fun searchDetailChannel(item: Item?, video: Video?, checkItem: Boolean, onMyServiceGetApi: OnMyServiceGetApi) {
        var items: List<Channel> = arrayListOf()
        val callVideoService: Call<ChannelSearchFromApi>
        if (checkItem) {
            callVideoService = onMyServiceGetApi.getChannelDetail("snippet", item?.snippet?.channelId!!, SearchVideo.KEY_BROWSER)
        } else {
            callVideoService = onMyServiceGetApi.getChannelDetail("snippet", video?.id?.channelId!!, SearchVideo.KEY_BROWSER)
        }
        callVideoService.enqueue(object : Callback<ChannelSearchFromApi> {
            override fun onResponse(call1: Call<ChannelSearchFromApi>?, response1: Response<ChannelSearchFromApi>?) {
                items = response1?.body()?.items ?: items
                if (checkItem) {
                    item?.channel = items[0]
                    mItemVideos.add(item!!)
                } else {
                    mItemVideos.add(Item(video!!, items[0].snippet, null, null, null))
                }
                mOnAfterGetApi.onPerform(mItemVideos)
            }

            override fun onFailure(call1: Call<ChannelSearchFromApi>?, t: Throwable?) {
                Log.d(TAG, "fail on retrofit when search detail channel" + t.toString())
            }
        })
    }

    private fun searchPlaylist(video: Video, serviceOn: OnMyServiceGetApi) {
        var items: List<Playlist> = arrayListOf()
        val callVideoService = serviceOn.getPlaylistSearch("snippet", video.id.playlistId, SearchVideo.KEY_BROWSER)
        callVideoService.enqueue(object : Callback<PlaylistSearchFromApi> {
            override fun onResponse(call1: Call<PlaylistSearchFromApi>?, response1: Response<PlaylistSearchFromApi>?) {
                items = response1?.body()?.items ?: items
                searchPlaylistDetail(video, serviceOn, items)
            }

            override fun onFailure(call1: Call<PlaylistSearchFromApi>?, t: Throwable?) {
                Log.d(TAG, "fail on retrofit when search playlist" + t.toString())
            }
        })
    }

    private fun searchPlaylistDetail(video: Video, serviceOn: OnMyServiceGetApi, items: List<Playlist>) {
        var detailItems: List<PlaylistDetails> = arrayListOf()
        val callVideoService = serviceOn.getPlaylistDetails("snippet,contentDetails", video.id.playlistId, SearchVideo.KEY_BROWSER)
        callVideoService.enqueue(object : Callback<PlaylistDetailFromApi> {
            override fun onResponse(call: Call<PlaylistDetailFromApi>?, response: Response<PlaylistDetailFromApi>?) {
                val playlistDetailFromApi: PlaylistDetailFromApi = response?.body()!!
                detailItems = response.body()?.items ?: detailItems
                items[0].snippet.totalPlaylist = playlistDetailFromApi.pageInfo.totalResults
                mItemVideos.add(Item(video, items[0].snippet, null, null, null))
                mOnAfterGetApi.onPerform(mItemVideos)
            }

            override fun onFailure(call: Call<PlaylistDetailFromApi>?, t: Throwable?) {
                Log.d(TAG, "fail on retrofit when search detail plalylist" + t.toString())
            }
        })
    }
}