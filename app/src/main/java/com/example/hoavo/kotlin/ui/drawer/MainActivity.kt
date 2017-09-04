package com.example.hoavo.kotlin.ui.drawer

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Base64
import android.view.View
import android.widget.Toast
import com.example.hoavo.karaoke.R
import com.example.hoavo.kotlin.ui.list_video.VideoFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity(), OnItemDrawerClickListener {
    val stateModify: StateModify by lazy { StateModify(this) }

    companion object {
        val TYPE_GALLERY = 0
        val TYPE_CAMERA = 1
    }

    var mUsers = ArrayList<String>()
    @RequiresApi(Build.VERSION_CODES.M)
    lateinit var mDrawerAdapter: DrawerAdapter
    var mDialog: Dialog? = null
    var mPositon: Int = 0
    var mAvatarBitmap: Bitmap? = null
    var mCheckPickAvatar = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCheckPickAvatar = false
        setContentView(R.layout.activity_main)
        setToolBar()
        initData()
        initDrawerLayout()
        updateFragment(VideoFragment.newInstance())
        mDrawerAdapter = DrawerAdapter(mUsers, this, this)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerViewDrawer.layoutManager = linearLayoutManager
        recyclerViewDrawer.adapter = mDrawerAdapter
        mDrawerAdapter.setPosition(1)
    }

    override fun onResume() {
        super.onResume()
        val list = stateModify.getData()
        if (list.isNotEmpty() && !mCheckPickAvatar) {
            mAvatarBitmap = convertStringToBitMap(list[1])
        }
        mDrawerAdapter.setBitMapAvatar(mAvatarBitmap ?: mDrawerAdapter.getBitmapAvatar())
    }

    override fun onStop() {
        super.onStop()
        stateModify.insertData(mPositon.toString(), convertBitMapToString(mAvatarBitmap))
    }

    override fun onClick(position: Int, check: Boolean) {
        if (check) {
            mDrawerAdapter.setPosition(position)
            drawerLayout.closeDrawers()
            mPositon = position
        } else {
            mDialog = createDialog()
            mDialog?.show()
        }
        mDrawerAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val extras = data.extras
            if (extras != null) {
                // Get image
                mAvatarBitmap = extras.getParcelable<Parcelable>("data") as Bitmap
                mDrawerAdapter.setBitMapAvatar(mAvatarBitmap as Bitmap)
                mDrawerAdapter.notifyDataSetChanged()
                drawerLayout.openDrawer(GravityCompat.START)
                mDialog?.cancel()
                mCheckPickAvatar = true
            }
        }
    }

    fun updateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frMain, fragment).commit()
    }

    fun setToolBar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        toolBar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers()
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun initData() {
        mUsers.add("Search")
        mUsers.add("Your Record")
    }

    private fun initDrawerLayout() {
        val drawerToggle = object : ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                llDrawer.translationX = slideOffset * drawerView!!.width
            }
        }
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    private fun createDialog(): Dialog {
        val builder = AlertDialog.Builder(DrawerActivity@ this)
        builder.setTitle(R.string.dialog_title_please_choose)
                .setItems(R.array.items) { dialog, which ->
                    when (which) {
                        TYPE_GALLERY -> {
                            val intent = Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                            intent.type = "image/*"
                            setCropImage(intent)
                            startActivityForResult(intent, TYPE_GALLERY)
                        }
                        else -> try {
                            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            setCropImage(cameraIntent)
                            startActivityForResult(cameraIntent, TYPE_CAMERA)
                        } catch (anfe: ActivityNotFoundException) {
                            val toast = Toast
                                    .makeText(applicationContext, "This device doesn't support the camera action!", Toast.LENGTH_SHORT)
                            toast.show()
                        }

                    }
                }
        return builder.create()
    }

    private fun setCropImage(intent: Intent) {
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("outputX", R.dimen.image_outputx)
        intent.putExtra("outputY", R.dimen.image_outputy)
        intent.putExtra("aspectX", R.dimen.image_aspectx)
        intent.putExtra("aspectY", R.dimen.image_aspecty)
        intent.putExtra("return-data", true)
    }

    private fun convertStringToBitMap(encodedString: String): Bitmap {
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        return bitmap ?: mDrawerAdapter.getBitmapAvatar()
    }

    private fun convertBitMapToString(bitmap: Bitmap?): String {
        val ByteStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, ByteStream)
        val b = ByteStream.toByteArray()
        val temp = Base64.encodeToString(b, Base64.DEFAULT)
        return temp ?: ""
    }
}