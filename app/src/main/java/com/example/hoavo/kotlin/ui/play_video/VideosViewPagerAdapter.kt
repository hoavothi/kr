package com.example.hoavo.kotlin.ui.play_video

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hoavo.karaoke.R
import com.example.hoavo.kotlin.models.Item
import kotlinx.android.synthetic.main.item_list_pager_video.view.*

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 14/08/2017.
 */
class VideosViewPagerAdapter(val mItems: List<Item>, val mContext: Context) : PagerAdapter() {

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view?.equals(`object`)!!
    }

    override fun getCount(): Int = mItems.size

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val itemView: View = LayoutInflater.from(mContext).inflate(R.layout.item_list_pager_video, container, false)
        Glide.with(mContext)
                .load(mItems[position].snippet.thumbnails.medium.url)
                .centerCrop()
                .into(itemView.imgThumbnailPager)
        container?.addView(itemView)
//        itemView.imgThumbnailPager.setOnClickListener {
//            if(mContext is PlayerVideoActivity){
//                mContext.initPlayer(mItems[position].video.id.videoId)
//                mContext.loadDataForPlayVideo(mItems[position])
//            }
//        }
        return itemView
    }



    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View?)
    }

}
