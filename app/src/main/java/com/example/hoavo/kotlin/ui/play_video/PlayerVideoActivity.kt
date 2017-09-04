package com.example.hoavo.kotlin.ui.play_video

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.example.hoavo.karaoke.R
import com.example.hoavo.kotlin.models.Item
import com.example.hoavo.kotlin.ui.list_video.getViewCount
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.android.synthetic.main.activity_player_video.*
import org.jetbrains.anko.longToast

class PlayerVideoActivity : AppCompatActivity() {

    companion object {
        val API_KEY = "AIzaSyBs2LP4sEpjRZ1fueTOJwZWxfFUr6wXFAA"
        val TYPE_ITEM = "Item"
        val TYPE_VIDEO_ID = "VideoId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_video)
        val bundle: Bundle = intent.extras
        val item: Item = bundle.getParcelable<Item>(TYPE_ITEM)
        val videoId = item.video.id.videoId
        initPlayer(videoId)
        loadDataForPlayVideo(item)
    }

    fun loadDataForPlayVideo(item: Item) {
        tvDetailNameVideo.text = item.snippet.title
        tvDetailChannelVideo.text = item.snippet.channelTitle
        Glide.with(this)
                .load(item.channel?.snippet?.thumbnails?.medium?.url)
                .centerCrop()
                .into(imgDetailThumbnailVideo)
        tvDetailLikeView.text = item.statistics?.likeCount?.getViewCount()
        tvDetailDisLikeView.text = item.statistics?.dislikeCount?.getViewCount()
        tvDetailDayPublish.text = item.snippet.publishedAt.getDayPublish()
        tvDetailViewCountVideo.text = getString(R.string.view_count, item.statistics?.viewCount?.getViewCount())
        supportFragmentManager.beginTransaction().replace(R.id.flShowVideo, ShowManyVideoFragment.newInstance(item)).commit()
    }

    fun initPlayer(videoId: String) {
        val youtubeFragmentPlayer = YouTubePlayerSupportFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.flShowPlayVideo, youtubeFragmentPlayer).commit()
        youtubeFragmentPlayer.initialize(PlayerVideoActivity.API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(a0: YouTubePlayer.Provider?, a1: YouTubePlayer?, a2: Boolean) {
                Log.d("hhhhhhhhhhhhhh", "nnnnnnnnnnn  " + a2)
                if (!a2) {
                    a1?.setShowFullscreenButton(true)
                    a1?.loadVideo(videoId)
                }
            }

            override fun onInitializationFailure(a0: YouTubePlayer.Provider?, a1: YouTubeInitializationResult?) {
                longToast("Can't load video, please check your internet!")
            }
        })
    }
}

fun String.getDayPublish(): String {
    return this.substring(0, 10)
}

