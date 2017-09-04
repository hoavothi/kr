package com.example.hoavo.kotlin.ui.drawer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable
import org.jetbrains.anko.db.dropTable

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by rimoka on 07/08/2017.
 */
class StateDBHelper private constructor(context: Context) : ManagedSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "State.db"
        val DB_VERSION = 1

        private var instance: StateDBHelper? = null

        @Synchronized
        fun newInstance(ctx: Context): StateDBHelper {
            return instance ?: StateDBHelper(ctx.applicationContext)
        }
    }

    object StateTable {
        val NAME = "State"
        val POSITION = "Position"
        val AVATAR = "Avatar"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(StateTable.NAME, true, StateTable.POSITION to TEXT, Pair(StateTable.AVATAR, TEXT))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(StateTable.NAME, true)
        onCreate(db)
    }
}
