package com.example.hoavo.kotlin.models

import android.os.Parcel
import android.os.Parcelable

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 10/08/2017.
 */
class Channel(val id: String, val snippet: Snippet) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Snippet::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(snippet, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Channel> {
        override fun createFromParcel(parcel: Parcel): Channel {
            return Channel(parcel)
        }

        override fun newArray(size: Int): Array<Channel?> {
            return arrayOfNulls(size)
        }
    }
}