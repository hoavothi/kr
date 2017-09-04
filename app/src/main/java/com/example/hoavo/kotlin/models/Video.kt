package com.example.hoavo.kotlin.models

import android.os.Parcel
import android.os.Parcelable

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 31/07/2017.
 *  For search video first
 */
class Video(val id: Id):Parcelable {
    constructor(parcel: Parcel) : this(parcel.readParcelable<Id>(Id::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(id, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Video> {
        override fun createFromParcel(parcel: Parcel): Video {
            return Video(parcel)
        }

        override fun newArray(size: Int): Array<Video?> {
            return arrayOfNulls(size)
        }
    }
}

class Id(val videoId: String="", val channelId: String="", val playlistId: String=""):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(videoId)
        parcel.writeString(channelId)
        parcel.writeString(playlistId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Id> {
        override fun createFromParcel(parcel: Parcel): Id {
            return Id(parcel)
        }

        override fun newArray(size: Int): Array<Id?> {
            return arrayOfNulls(size)
        }
    }
}