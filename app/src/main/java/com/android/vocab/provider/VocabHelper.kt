package com.android.vocab.provider

import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.vocab.provider.VocabHelper.constants.DB_NAME
import com.android.vocab.provider.VocabHelper.constants.DB_VERSION


class VocabHelper(val baseContext: Context) : SQLiteOpenHelper(baseContext, DB_NAME, null, DB_VERSION) {

    object constants {
        const val SQL_FILE_NAME: String = "vocab.sql"

        const val DB_NAME: String = "vocab.sqlite"

        const val DB_VERSION: Int = 1
    }

    val LOG_TAG: String = VocabHelper::class.simpleName!!

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        val assetManager: AssetManager = baseContext.assets
        var queries: String = ""
        assetManager.open(constants.SQL_FILE_NAME).reader().use {
            reader -> queries = reader.readText()
        }
        sqLiteDatabase?.execSQL(queries)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(sqLiteDatabase)
    }

}
