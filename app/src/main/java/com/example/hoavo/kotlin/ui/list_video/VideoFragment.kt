package com.example.hoavo.kotlin.ui.list_video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hoavo.karaoke.R
import com.example.hoavo.kotlin.models.Item
import com.example.hoavo.kotlin.ui.drawer.MainActivity
import com.example.hoavo.kotlin.ui.other.SearchVideo
import com.example.hoavo.kotlin.ui.play_video.PlayerVideoActivity
import kotlinx.android.synthetic.main.fragment_video.view.*


/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 31/07/2017.
 */

class VideoFragment : Fragment(), OnItemVideoClickListener {

    private var mContext: MainActivity? = null
    companion object {
        private val mVideoFragment: VideoFragment by lazy { VideoFragment() }
        fun newInstance(): VideoFragment = mVideoFragment
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View? = inflater?.inflate(R.layout.fragment_video, container, false)
        val searchVideo = SearchVideo()
        searchVideo.initSearch(v, this, context)
        v?.btnSearchVideo?.setOnClickListener {
            searchVideo.search(v)
        }
        return v
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onItemClick(type: Int, item: Item) {
        when (type) {
            VideoAdapter.TYPE_VIDEO -> {
                val intent = Intent(mContext, PlayerVideoActivity::class.java)
                intent.putExtra(PlayerVideoActivity.TYPE_ITEM, item)
                startActivity(intent)
            }
            VideoAdapter.TYPE_PLAY_LIST -> {

            }
            VideoAdapter.TYPE_CHANNEL -> {

            }
        }
    }
}
