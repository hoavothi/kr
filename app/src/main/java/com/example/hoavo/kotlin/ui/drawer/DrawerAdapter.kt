package com.example.hoavo.kotlin.ui.drawer

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hoavo.karaoke.R
import kotlinx.android.synthetic.main.item_header_drawer.view.*
import kotlinx.android.synthetic.main.item_list_drawer.view.*


@RequiresApi(Build.VERSION_CODES.M)
/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 25/07/2017.
 */
class DrawerAdapter(private val items: List<String>?, private val context: Context?, private val onItemDrawerClickListener: OnItemDrawerClickListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mWallpaperDrawable: Drawable
    private var mAvatarBitmap: Bitmap
    private var mPosition: Int = 0

    companion object {
        val TYPE_HEADER: Int = 0
        val TYPE_ITEM: Int = 1
    }

    init {
        val wallpapermanager: WallpaperManager = WallpaperManager.getInstance(context)
        mWallpaperDrawable = wallpapermanager.drawable
        mAvatarBitmap = drawableToBitmap(ContextCompat.getDrawable(context, R.mipmap.ic_user))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is HeaderDrawerHolder -> {
                holder.itemView.imgAvatar.setImageBitmap(mAvatarBitmap)
                holder.itemView.imgBackground.setImageDrawable(mWallpaperDrawable)
            }
            is ItemDrawerHolder -> {
                holder.itemView.tvFunction.text = items?.get(position - 1)
                if (position == mPosition) {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItemPressed))
                } else {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItem))
                }
            }
        }
    }

    override fun getItemCount(): Int = items?.size?.plus(1) ?: 0

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return TYPE_HEADER
            else -> return TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            return HeaderDrawerHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_header_drawer, parent, false))
        }
        return ItemDrawerHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_list_drawer, parent, false))
    }

//    private fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
//        val output: Bitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(output)
//        val paint = Paint()
//        val rect = Rect(0, 0, bitmap.width, bitmap.height)
//        val rectF = RectF(rect)
//        paint.isAntiAlias = true
//        canvas.drawARGB(0, 0, 0, 0)
//        paint.color = ContextCompat.getColor(context, R.color.colorGrayLight)
//        canvas.drawRoundRect(rectF, pixels.toFloat(), pixels.toFloat(), paint)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        canvas.drawBitmap(bitmap, rect, rect, paint)
//        return output
//    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bimap: Bitmap
        if (drawable is BitmapDrawable) {
            val bitmapdrawable: BitmapDrawable = drawable
            if (bitmapdrawable.bitmap != null) {
                return bitmapdrawable.bitmap
            }
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bimap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            bimap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }
        return bimap
    }

    internal fun getBitmapAvatar(): Bitmap {
        return mAvatarBitmap
    }

    internal fun setBitMapAvatar(avatarBitmap: Bitmap) {
//        mAvatarBitmap = getRoundedCornerBitmap(avatarBitmap, 50)
        mAvatarBitmap=avatarBitmap
    }

    internal fun setPosition(position: Int) {
        mPosition = position
    }


    inner class HeaderDrawerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.imgAvatar.setOnClickListener {
                onItemDrawerClickListener?.onClick(adapterPosition, false)
            }
        }
    }

    inner class ItemDrawerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemDrawerClickListener?.onClick(adapterPosition, true)
            }
        }
    }
}