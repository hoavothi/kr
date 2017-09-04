package com.example.hoavo.kotlin.models

import android.os.Parcel
import android.os.Parcelable

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 03/08/2017.
 *  For Search detail video
 */
data class Item(var video: Video, var snippet: Snippet, var contentDetails: ContentDetails?, var statistics: Statistics?, var channel: Channel?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Video::class.java.classLoader),
            parcel.readParcelable(Snippet::class.java.classLoader),
            parcel.readParcelable(ContentDetails::class.java.classLoader),
            parcel.readParcelable(Statistics::class.java.classLoader),
            parcel.readParcelable(Channel::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(video, flags)
        parcel.writeParcelable(snippet, flags)
        parcel.writeParcelable(contentDetails, flags)
        parcel.writeParcelable(statistics, flags)
        parcel.writeParcelable(channel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}

class Snippet(val publishedAt: String, val channelId: String?, val title: String, val description: String, val thumbnails: Thumbnails, val channelTitle: String?, val resourceId: ResourceId?, var totalPlaylist: Int?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Thumbnails::class.java.classLoader),
            parcel.readString(),
            parcel.readParcelable(ResourceId::class.java.classLoader),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(publishedAt)
        parcel.writeString(channelId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(thumbnails, flags)
        parcel.writeString(channelTitle)
        parcel.writeParcelable(resourceId, flags)
        parcel.writeValue(totalPlaylist)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Snippet> {
        override fun createFromParcel(parcel: Parcel): Snippet {
            return Snippet(parcel)
        }

        override fun newArray(size: Int): Array<Snippet?> {
            return arrayOfNulls(size)
        }
    }
}

class Thumbnails(val medium: Medium) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readParcelable<Medium>(Medium::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(medium, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Thumbnails> {
        override fun createFromParcel(parcel: Parcel): Thumbnails {
            return Thumbnails(parcel)
        }

        override fun newArray(size: Int): Array<Thumbnails?> {
            return arrayOfNulls(size)
        }
    }
}

class Medium(val url: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medium> {
        override fun createFromParcel(parcel: Parcel): Medium {
            return Medium(parcel)
        }

        override fun newArray(size: Int): Array<Medium?> {
            return arrayOfNulls(size)
        }
    }
}

class ContentDetails(val duration: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContentDetails> {
        override fun createFromParcel(parcel: Parcel): ContentDetails {
            return ContentDetails(parcel)
        }

        override fun newArray(size: Int): Array<ContentDetails?> {
            return arrayOfNulls(size)
        }
    }
}

class Statistics(val viewCount: String, var likeCount: String, var dislikeCount: String, var commentCount: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(viewCount)
        parcel.writeString(likeCount)
        parcel.writeString(dislikeCount)
        parcel.writeString(commentCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Statistics> {
        override fun createFromParcel(parcel: Parcel): Statistics {
            return Statistics(parcel)
        }

        override fun newArray(size: Int): Array<Statistics?> {
            return arrayOfNulls(size)
        }
    }
}

class ResourceId(val videoId: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(videoId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResourceId> {
        override fun createFromParcel(parcel: Parcel): ResourceId {
            return ResourceId(parcel)
        }

        override fun newArray(size: Int): Array<ResourceId?> {
            return arrayOfNulls(size)
        }
    }
}

class PageInfor(val totalResults: Int)