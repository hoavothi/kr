package com.example.hoavo.kotlin.ui.play_video

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hoavo.karaoke.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import org.jetbrains.anko.support.v4.longToast

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 14/08/2017.
 */
class PlayerVideoFragment : Fragment() {
    private lateinit var mVideoId: String
    private val playerVideoFragment: PlayerVideoFragment by lazy { PlayerVideoFragment() }

    fun newInstance(videoId: String): PlayerVideoFragment {
        val bundle: Bundle = Bundle()
        bundle.putString(PlayerVideoActivity.TYPE_VIDEO_ID, videoId)
        Log.d("hhhhhhhhhhhhhh", "m  " + videoId)
        playerVideoFragment.arguments = bundle
        return playerVideoFragment
    }

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        mVideoId = arguments.getString(PlayerVideoActivity.TYPE_VIDEO_ID) ?: ""
    }

    override fun onCreateView(p0: LayoutInflater?, p1: ViewGroup?, p2: Bundle?): View? {
        val v: View? = p0?.inflate(R.layout.fragment_play_video, p1, false)
        initPlayer()
        Log.d("hhhhhhhhhhhhhh", "jjjjjjjjj  ")
        return v
    }

    fun initPlayer(){
        val youtubePlayerFragment: YouTubePlayerSupportFragment = YouTubePlayerSupportFragment()
        youtubePlayerFragment.initialize(PlayerVideoActivity.API_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(a0: YouTubePlayer.Provider?, a1: YouTubePlayer?, a2: Boolean) {
                Log.d("hhhhhhhhhhhhhh", "nnnnnnnnnnn  " + a2)
                if (!a2) {
                    a1?.setShowFullscreenButton(true)
                    a1?.loadVideo("cMPEd8m79Hw")
                }
            }

            override fun onInitializationFailure(a0: YouTubePlayer.Provider?, a1: YouTubeInitializationResult?) {
                longToast("Can't load video, please check your internet!")
            }
        })
    }
}
