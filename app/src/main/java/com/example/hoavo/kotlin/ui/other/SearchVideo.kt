package com.example.hoavo.kotlin.ui.other

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.hoavo.kotlin.api.OnAfterGetApi
import com.example.hoavo.kotlin.api.OnMyServiceGetApi
import com.example.hoavo.kotlin.api.RequestApi
import com.example.hoavo.kotlin.models.Item
import com.example.hoavo.kotlin.ui.list_video.OnItemVideoClickListener
import com.example.hoavo.kotlin.ui.list_video.VideoAdapter
import kotlinx.android.synthetic.main.fragment_video.view.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 01/09/2017.
 */
class SearchVideo : OnAfterGetApi {
    companion object {
        val URI = "https://www.googleapis.com"
        val KEY_BROWSER = "AIzaSyDDH313GMgdJYGDkZ3S7xlZXcoR962Q2gE"
    }
    private var mItemVideos: MutableList<Item> = mutableListOf()
    private lateinit var mRetrofit: Retrofit
    private var mRequestApi = RequestApi(this)
    private lateinit var mVideoAdapter: VideoAdapter

    fun initSearch(v: View?, onItemVideoClickListener: OnItemVideoClickListener, context: Context) {
        mVideoAdapter = VideoAdapter(mItemVideos, context, onItemVideoClickListener)
        val linearLayoutManager = LinearLayoutManager(context)
        v?.recyclerViewVideo?.layoutManager = linearLayoutManager
        v?.recyclerViewVideo?.adapter = mVideoAdapter
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        mRetrofit = Retrofit.Builder()
                .baseUrl(SearchVideo.URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun search(v: View?) {
        mItemVideos.removeAll(mItemVideos)
        val onMyServiceGetApi: OnMyServiceGetApi? = mRetrofit.create(OnMyServiceGetApi::class.java)
        val callSearchService = onMyServiceGetApi?.getVideoSearch("snippet", v?.edtSearchVideo?.text.toString(), 25, SearchVideo.KEY_BROWSER)
        mRequestApi.searchVideo(onMyServiceGetApi, callSearchService!!)
        val im = v?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun onPerform(items: List<Item>) {
        mItemVideos.add(items[items.size - 1])
        mVideoAdapter.notifyDataSetChanged()
    }
}