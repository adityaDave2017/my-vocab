package com.android.vocab.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri


@Suppress("unused")
class VocabProvider : ContentProvider() {

    private val LOG_TAG: String = VocabProvider::class.java.simpleName

    lateinit var vocabHelper: VocabHelper

    companion object {

        val URI_MATCHER: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        val WORD_TYPE: Int = 100

        val WORD_TYPE_ID: Int = 101

        val WORD: Int = 200

        val WORD_ID: Int = 201

        val SENTENCE: Int = 300

        val SENTENCE_ID: Int = 301

        val SYNONYM: Int = 400

        val SYNONYM_ID: Int = 401

        val ANTONYM: Int = 500

        val ANTONYM_ID: Int = 501

        init {
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_WORD_TYPE, WORD_TYPE)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_WORD_TYPE}/#", WORD_TYPE_ID)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_WORD, WORD)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_WORD}/#", WORD_ID)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_SENTENCE, SENTENCE)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_SENTENCE}/#", SENTENCE_ID)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_ANTONYM, SYNONYM)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_ANTONYM}/#", SYNONYM_ID)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_ANTONYM, ANTONYM)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_ANTONYM}/#", ANTONYM_ID)
        }

    }


    override fun insert(uri: Uri?, contentValues: ContentValues?): Uri {
        val sqliteDatabase: SQLiteDatabase = vocabHelper.writableDatabase
        val id: Long = when (URI_MATCHER.match(uri)) {
            WORD_TYPE -> sqliteDatabase.insert(VocabContract.WordTypeEntry.TABLE_NAME, null, contentValues)
            WORD -> sqliteDatabase.insert(VocabContract.WordEntry.TABLE_NAME, null, contentValues)
            SENTENCE -> sqliteDatabase.insert(VocabContract.SentenceEntry.TABLE_NAME, null, contentValues)
            SYNONYM -> sqliteDatabase.insert(VocabContract.SynonymEntry.TABLE_NAME, null, contentValues)
            ANTONYM -> sqliteDatabase.insert(VocabContract.AntonymEntry.TABLE_NAME, null, contentValues)
            else -> throw IllegalArgumentException("Uri not supported: $uri")
        }
        return ContentUris.withAppendedId(uri, id)
    }


    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val sqliteDatabase: SQLiteDatabase = vocabHelper.readableDatabase
        val cursor: Cursor = when (URI_MATCHER.match(uri)) {
            WORD_TYPE -> sqliteDatabase.query(VocabContract.WordTypeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
//                WORD_TYPE_ID -> sqliteDatabase.query(VocabContract.WordTypeEntry.TABLE_NAME, projection, "${VocabContract.WordTypeEntry.COLUMN_TYPE_NAME}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            WORD -> sqliteDatabase.query(VocabContract.WordEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            WORD_ID -> sqliteDatabase.query(VocabContract.WordEntry.TABLE_NAME, projection, "${VocabContract.WordEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            SENTENCE -> sqliteDatabase.query(VocabContract.SentenceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            SENTENCE_ID -> sqliteDatabase.query(VocabContract.SentenceEntry.TABLE_NAME, projection, "${VocabContract.SentenceEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            SYNONYM_ID -> sqliteDatabase.query(VocabContract.SynonymEntry.TABLE_NAME, projection, "${VocabContract.SynonymEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            ANTONYM_ID -> sqliteDatabase.query(VocabContract.AntonymEntry.TABLE_NAME, projection, "${VocabContract.AntonymEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            else -> throw IllegalArgumentException("Cannot query unknown uri: $uri")
        }
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }


    override fun onCreate(): Boolean {
        vocabHelper = VocabHelper(context)
        return true
    }


    override fun update(uri: Uri?, contentValues: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val sqliteDatabase: SQLiteDatabase = vocabHelper.writableDatabase
        val rows: Int = when (URI_MATCHER.match(uri)) {
            WORD_TYPE_ID -> sqliteDatabase.update(VocabContract.WordTypeEntry.TABLE_NAME, contentValues, selection, selectionArgs)
            WORD_ID -> sqliteDatabase.update(VocabContract.WordEntry.TABLE_NAME, contentValues, selection, selectionArgs)
            SYNONYM_ID -> sqliteDatabase.update(VocabContract.SynonymEntry.TABLE_NAME, contentValues, selection, selectionArgs)
            ANTONYM_ID -> sqliteDatabase.update(VocabContract.AntonymEntry.TABLE_NAME, contentValues, selection, selectionArgs)
            else -> throw IllegalArgumentException("Uri not supported: $uri")
        }
        if (rows != 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rows
    }


    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val sqliteDatabase: SQLiteDatabase = vocabHelper.writableDatabase
        val rows: Int = when (URI_MATCHER.match(uri)) {
            WORD_TYPE_ID -> sqliteDatabase.delete(VocabContract.WordTypeEntry.TABLE_NAME, selection, selectionArgs)
            WORD_ID -> sqliteDatabase.delete(VocabContract.WordEntry.TABLE_NAME, selection, selectionArgs)
            SYNONYM_ID -> sqliteDatabase.delete(VocabContract.SynonymEntry.TABLE_NAME, selection, selectionArgs)
            ANTONYM_ID -> sqliteDatabase.delete(VocabContract.AntonymEntry.TABLE_NAME, selection, selectionArgs)
            else -> throw IllegalArgumentException("Uri not supported: $uri")
        }
        if (rows != 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rows
    }


    override fun getType(uri: Uri?): String {
        return when (URI_MATCHER.match(uri)) {
            WORD_TYPE -> VocabContract.WordTypeEntry.CONTENT_LIST_TYPE
            WORD_TYPE_ID -> VocabContract.WordTypeEntry.CONTENT_ITEM_TYPE
            WORD -> VocabContract.WordEntry.CONTENT_LIST_TYPE
            WORD_ID -> VocabContract.WordEntry.CONTENT_ITEM_TYPE
            SENTENCE -> VocabContract.SentenceEntry.CONTENT_LIST_TYPE
            SENTENCE_ID -> VocabContract.SentenceEntry.CONTENT_ITEM_TYPE
            SYNONYM_ID -> VocabContract.SynonymEntry.CONTENT_LIST_TYPE
            ANTONYM_ID -> VocabContract.AntonymEntry.CONTENT_LIST_TYPE
            else -> throw IllegalArgumentException("No type for uri: $uri")
        }
    }

}