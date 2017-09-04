package com.example.hoavo.kotlin.ui.record

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hoavo.karaoke.R

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 11/08/2017.
 */
class RecordFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View? = inflater?.inflate(R.layout.fragment_video1, container, false)

        return v
    }
}