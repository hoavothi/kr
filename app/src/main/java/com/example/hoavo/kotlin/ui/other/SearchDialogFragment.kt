package com.example.hoavo.kotlin.ui.other

import android.app.Dialog
import android.app.DialogFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hoavo.karaoke.R
import com.example.hoavo.kotlin.models.Item
import com.example.hoavo.kotlin.ui.list_video.OnItemVideoClickListener
import com.example.hoavo.kotlin.ui.list_video.VideoAdapter
import com.example.hoavo.kotlin.ui.play_video.PlayerVideoActivity
import kotlinx.android.synthetic.main.fragment_video.view.*

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 30/08/2017.
 */
class SearchDialogFragment : DialogFragment(), OnItemVideoClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity)
        dialog.setTitle("Your Search")
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,resources.getDimensionPixelSize(R.dimen.height_dialog))
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View? = inflater?.inflate(R.layout.fragment_video, container, false)
        val searchVideo = SearchVideo()
        searchVideo.initSearch(v, this, this@SearchDialogFragment.activity)
        v?.btnSearchVideo?.setOnClickListener {
            searchVideo.search(v)
        }
        return v
    }

    override fun onItemClick(type: Int, item: Item) {
        when (type) {
            VideoAdapter.TYPE_VIDEO -> {
                val intent = Intent(this@SearchDialogFragment.activity, PlayerVideoActivity::class.java)
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