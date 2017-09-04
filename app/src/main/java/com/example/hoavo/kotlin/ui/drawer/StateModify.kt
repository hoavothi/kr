package com.example.hoavo.kotlin.ui.drawer

import android.content.Context
import android.database.Cursor
import android.util.Log
import org.jetbrains.anko.db.insert

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 07/08/2017.
 */
class StateModify(private val context: Context) {

    fun insertData(position: String, bitmap: String) {
        val database = StateDBHelper.newInstance(context)
        database.use {
            insert(StateDBHelper.StateTable.NAME, StateDBHelper.StateTable.POSITION to position, StateDBHelper.StateTable.AVATAR to bitmap)
        }
    }

    fun getData(): List<String> {
        val list: MutableList<String> = mutableListOf()
        val database = StateDBHelper.newInstance(context)
        val sql = "select * from " + StateDBHelper.StateTable.NAME
        val cursor: Cursor? = database.readableDatabase.rawQuery(sql, null)
        if (cursor != null && cursor.moveToFirst()) {
            list.add(cursor.getString(cursor.getColumnIndex(StateDBHelper.StateTable.POSITION)))
            list.add(cursor.getString(cursor.getColumnIndex(StateDBHelper.StateTable.AVATAR)))
            cursor.close()
        }
        return list
    }
}
