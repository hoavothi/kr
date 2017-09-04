package com.example.hoavo.kotlin.ui.play_video

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.hoavo.karaoke.R
import com.example.hoavo.kotlin.api.OnAfterGetApi
import com.example.hoavo.kotlin.api.OnMyServiceGetApi
import com.example.hoavo.kotlin.api.RequestApi
import com.example.hoavo.kotlin.models.Item
import com.example.hoavo.kotlin.ui.list_video.getViewCount
import com.example.hoavo.kotlin.ui.other.SearchDialogFragment
import com.example.hoavo.kotlin.ui.other.SearchVideo
import kotlinx.android.synthetic.main.fab_layout.*
import kotlinx.android.synthetic.main.fab_layout.view.*
import kotlinx.android.synthetic.main.fragment_show_many_video.*
import kotlinx.android.synthetic.main.fragment_show_many_video.view.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 13/08/2017.
 */
class ShowManyVideoFragment : Fragment(), OnAfterGetApi, View.OnClickListener {
    private var mPlayerVideoActivity: PlayerVideoActivity? = null
    private var mItems: MutableList<Item> = mutableListOf()
    private lateinit var mItem: Item
    private lateinit var mRetrofit: Retrofit
    private var mRequestApi = RequestApi(this)
    private lateinit var mVideoPagerAdapter: VideosViewPagerAdapter
    private var isFabOpen = false
    private val mFabOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_open) }
    private val mFabClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_close) }
    private val mFabRotateBackward: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_rotate_backward) }
    private val mFabRotateForward: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_rotate_forward) }
    private val mAnimTxtOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.txt_open) }

    companion object {
        fun newInstance(item: Item): ShowManyVideoFragment {
            val mShowManyVideoFragment = ShowManyVideoFragment()
            val bundle = Bundle()
            bundle.putParcelable(PlayerVideoActivity.TYPE_ITEM, item)
            mShowManyVideoFragment.arguments = bundle
            return mShowManyVideoFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItem = arguments.getParcelable(PlayerVideoActivity.TYPE_ITEM)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View? = inflater?.inflate(R.layout.fragment_show_many_video, container, false)
//        val gson = GsonBuilder()
//                .registerTypeAdapter(object : TypeToken<List<Item>>() {}.getType(), MyDeserializer())
//                .create()

//        mRetrofit = Retrofit.Builder()
//                .baseUrl(VideoFragment.URI)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        mRetrofit = Retrofit.Builder()
                .baseUrl(SearchVideo.URI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()
        val onMyServiceGetApi: OnMyServiceGetApi? = mRetrofit.create(OnMyServiceGetApi::class.java)
        val callService = onMyServiceGetApi?.getMoreVideos("snippet", "completed", "25", mItem.video.id.videoId, "video", SearchVideo.KEY_BROWSER)
        mRequestApi.searchVideo(onMyServiceGetApi, callService!!)
        mVideoPagerAdapter = VideosViewPagerAdapter(mItems, context)
        v!!.viewPagerShowVideos.adapter = mVideoPagerAdapter
        v.viewPagerShowVideos.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //No-op
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //No-op
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> imgArrowLeft.visibility = View.GONE
                    mItems.size - 1 -> imgArrowRight.visibility = View.GONE
                    else -> {
                        imgArrowLeft.visibility = View.VISIBLE
                        imgArrowRight.visibility = View.VISIBLE
                    }
                }
                setOnPageChange(position)
            }

        })
        v.imgArrowLeft.setOnClickListener { v.viewPagerShowVideos.currentItem = v.viewPagerShowVideos.currentItem - 1 }
        v.imgArrowRight.setOnClickListener { v.viewPagerShowVideos.currentItem = v.viewPagerShowVideos.currentItem + 1 }
        v.imgArrowLeft.visibility = View.GONE
        v.imgArrowRight.visibility = View.GONE
        mVideoPagerAdapter.notifyDataSetChanged()
        v.floatingButtonOption.setOnClickListener(this)
        v.imgBtnFile.setOnClickListener(this)
        v.imgBtnHome.setOnClickListener(this)
        v.imgBtnRecord.setOnClickListener(this)
        v.imgBtnSearch.setOnClickListener(this)
        return v
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("nnnnnnnnnnnnnnnnn","on attach")
        mPlayerVideoActivity = context as? PlayerVideoActivity
    }

    override fun onPerform(items: List<Item>) {
        mItems.add(items[items.size - 1])
        mVideoPagerAdapter.notifyDataSetChanged()

        if (mItems.size == 1) {
            setOnPageChange(0)
            imgArrowRight.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            v?.floatingButtonOption -> {
                animateFab()
            }
            v?.imgBtnFile -> {
            }
            v?.imgBtnHome -> {
            }
            v?.imgBtnRecord -> {
            }
            v?.imgBtnSearch -> {
                SearchDialogFragment().show(mPlayerVideoActivity?.fragmentManager, "dialog")
            }
        }
    }

    private fun setOnPageChange(position: Int) {
        tvTitleViewPager.text = mItems[position].snippet.title
        tvViewCountViewPager.text = getString(R.string.view_count, mItems[position].statistics?.viewCount?.getViewCount())
        tvTitleViewPager.setOnTouchListener({ _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    tvTitleViewPager.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                }

                MotionEvent.ACTION_UP -> {
                    tvTitleViewPager.paintFlags = Paint.ANTI_ALIAS_FLAG
                    mPlayerVideoActivity?.initPlayer(mItems[position].video.id.videoId)
                    mPlayerVideoActivity?.loadDataForPlayVideo(mItems[position])
                }
            }
            true
        })
    }

    private fun animateFab() {
        when (isFabOpen) {
            false -> {
                imgBtnFile.visibility = View.VISIBLE
                imgBtnHome.visibility = View.VISIBLE
                imgBtnRecord.visibility = View.VISIBLE
                imgBtnSearch.visibility = View.VISIBLE
                tvHomeFab.visibility = View.VISIBLE
                tvRecordFab.visibility = View.VISIBLE
                tvSearchFab.visibility = View.VISIBLE
                tvFileFab.visibility = View.VISIBLE
                floatingButtonOption.startAnimation(mFabRotateForward)
                imgBtnRecord.startAnimation(mFabOpen)
                imgBtnSearch.startAnimation(mFabOpen)
                imgBtnFile.startAnimation(mFabOpen)
                imgBtnHome.startAnimation(mFabOpen)
                tvHomeFab.startAnimation(mAnimTxtOpen)
                tvFileFab.startAnimation(mAnimTxtOpen)
                tvRecordFab.startAnimation(mAnimTxtOpen)
                tvSearchFab.startAnimation(mAnimTxtOpen)
                isFabOpen = true
            }
            true -> {
                floatingButtonOption.startAnimation(mFabRotateBackward)
                imgBtnFile.startAnimation(mFabClose)
                imgBtnHome.startAnimation(mFabClose)
                imgBtnRecord.startAnimation(mFabClose)
                imgBtnSearch.startAnimation(mFabClose)
                tvHomeFab.startAnimation(mFabClose)
                tvFileFab.startAnimation(mFabClose)
                tvRecordFab.startAnimation(mFabClose)
                tvSearchFab.startAnimation(mFabClose)
                isFabOpen = false
            }
        }
    }

//    private class MyDeserializer : JsonDeserializer<MutableList<Item>> {
//        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): MutableList<Item> {
//            val items: MutableList<Item> = mutableListOf()
//            val values = json?.asJsonObject?.getAsJsonObject("items")
//            for ((key, value) in values?.entrySet()!!) {
//                val item: Item? = context?.deserialize(value, Item::class.java)
//                items.add(item!!)
//            }
//            return items
//        }
//    }

}

