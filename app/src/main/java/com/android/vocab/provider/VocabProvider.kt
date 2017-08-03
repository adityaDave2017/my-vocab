package com.android.vocab.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log


@Suppress("unused")
class VocabProvider : ContentProvider() {

    private val LOG_TAG: String = VocabProvider::class.java.simpleName

    lateinit var vocabHelper: VocabHelper

    companion object {

        val queryWordsAndType:String = """
            SELECT *
            FROM
            ${VocabContract.WordEntry.TABLE_NAME} INNER JOIN ${VocabContract.WordTypeEntry.TABLE_NAME}
            ON ${VocabContract.WordEntry.TABLE_NAME}.${VocabContract.WordEntry.COLUMN_TYPE_ID} = ${VocabContract.WordTypeEntry.TABLE_NAME}.${VocabContract.WordTypeEntry._ID}
        """

        val queryWordsAndTypeForId:String = "$queryWordsAndType WHERE ${VocabContract.WordEntry._ID} = ?"

        val queryForSynonymsWord: String = """
            SELECT *
            FROM
            ${VocabContract.SynonymEntry.TABLE_NAME} INNER JOIN ${VocabContract.WordEntry.TABLE_NAME}
            ON ${VocabContract.SynonymEntry.TABLE_NAME}.${VocabContract.SynonymEntry.COLUMN_SYNONYM_WORD_ID} = ${VocabContract.WordEntry.TABLE_NAME}.${VocabContract.WordEntry._ID}
            WHERE ${VocabContract.SynonymEntry.TABLE_NAME}.${VocabContract.SynonymEntry.COLUMN_MAIN_WORD_ID} = ?
        """

        val queryForAntonymsWord: String = """
            SELECT *
            FROM
            ${VocabContract.AntonymEntry.TABLE_NAME} INNER JOIN ${VocabContract.WordEntry.TABLE_NAME}
            ON ${VocabContract.AntonymEntry.TABLE_NAME}.${VocabContract.AntonymEntry.COLUMN_ANTONYM_WORD_ID} = ${VocabContract.WordEntry.TABLE_NAME}.${VocabContract.WordEntry._ID}
            WHERE ${VocabContract.AntonymEntry.TABLE_NAME}.${VocabContract.AntonymEntry.COLUMN_MAIN_WORD_ID} = ?
        """

        val URI_MATCHER: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        val WORD_TYPE_LIST: Int = 100

        val WORD_TYPE_ITEM: Int = 101

        val WORD_LIST: Int = 200

        val WORD_ITEM: Int = 201

        val SENTENCE_LIST: Int = 300

        val SENTENCE_ITEM: Int = 301

        val SYNONYM_LIST: Int = 400

        val SYNONYM_ITEM: Int = 401

        val ANTONYM_LIST: Int = 500

        val ANTONYM_ITEM: Int = 501

        val WORD_AND_TYPE_LIST:Int = 600

        val WORD_AND_TYPE_ITEM: Int = 601

        val SYNONYM_FOR_WORD_LIST: Int = 700

        val ANTONYM_FOR_WORD_LIST: Int = 701

        init {
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_WORD_TYPE, WORD_TYPE_LIST)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_WORD_TYPE}/#", WORD_TYPE_ITEM)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_WORD, WORD_LIST)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_WORD}/#", WORD_ITEM)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_SENTENCE, SENTENCE_LIST)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_SENTENCE}/#", SENTENCE_ITEM)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_SYNONYM, SYNONYM_LIST)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_SYNONYM}/#", SYNONYM_ITEM)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_ANTONYM, ANTONYM_LIST)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_ANTONYM}/#", ANTONYM_ITEM)

            /* Custom Query Registration */
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, VocabContract.PATH_WORD_AND_TYPE, WORD_AND_TYPE_LIST)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_WORD_AND_TYPE}/#", WORD_AND_TYPE_ITEM)

            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_SYNONYM_FOR_WORD}/#", SYNONYM_FOR_WORD_LIST)
            URI_MATCHER.addURI(VocabContract.CONTENT_AUTHORITY, "${VocabContract.PATH_ANTONYM_FOR_WORD}/#", ANTONYM_FOR_WORD_LIST)
        }

    }


    override fun insert(uri: Uri?, contentValues: ContentValues?): Uri {
        val sqliteDatabase: SQLiteDatabase = vocabHelper.writableDatabase
        val id: Long = when (URI_MATCHER.match(uri)) {
            WORD_TYPE_LIST -> sqliteDatabase.insert(VocabContract.WordTypeEntry.TABLE_NAME, null, contentValues)
            WORD_LIST -> sqliteDatabase.insert(VocabContract.WordEntry.TABLE_NAME, null, contentValues)
            SENTENCE_LIST -> sqliteDatabase.insert(VocabContract.SentenceEntry.TABLE_NAME, null, contentValues)
            SYNONYM_LIST -> sqliteDatabase.insert(VocabContract.SynonymEntry.TABLE_NAME, null, contentValues)
            ANTONYM_LIST -> sqliteDatabase.insert(VocabContract.AntonymEntry.TABLE_NAME, null, contentValues)
            else -> throw IllegalArgumentException("Uri not supported: $uri")
        }
        return ContentUris.withAppendedId(uri, id)
    }


    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val sqliteDatabase: SQLiteDatabase = vocabHelper.readableDatabase
        val cursor: Cursor = when (URI_MATCHER.match(uri)) {
            WORD_TYPE_LIST -> sqliteDatabase.query(VocabContract.WordTypeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            WORD_TYPE_ITEM -> sqliteDatabase.query(VocabContract.WordTypeEntry.TABLE_NAME, projection, "${VocabContract.WordTypeEntry.COLUMN_TYPE_NAME}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            WORD_LIST -> sqliteDatabase.query(VocabContract.WordEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            WORD_ITEM -> sqliteDatabase.query(VocabContract.WordEntry.TABLE_NAME, projection, "${VocabContract.WordEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            SENTENCE_LIST -> sqliteDatabase.query(VocabContract.SentenceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            SENTENCE_ITEM -> sqliteDatabase.query(VocabContract.SentenceEntry.TABLE_NAME, projection, "${VocabContract.SentenceEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            SYNONYM_ITEM -> sqliteDatabase.query(VocabContract.SynonymEntry.TABLE_NAME, projection, "${VocabContract.SynonymEntry.COLUMN_MAIN_WORD_ID}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            ANTONYM_ITEM -> sqliteDatabase.query(VocabContract.AntonymEntry.TABLE_NAME, projection, "${VocabContract.AntonymEntry.COLUMN_MAIN_WORD_ID}=?", arrayOf(ContentUris.parseId(uri).toString()), null, null, sortOrder)
            WORD_AND_TYPE_LIST -> sqliteDatabase.rawQuery(queryWordsAndType, null)
            WORD_AND_TYPE_ITEM -> sqliteDatabase.rawQuery(queryWordsAndTypeForId, arrayOf(ContentUris.parseId(uri).toString()))
            SYNONYM_FOR_WORD_LIST -> sqliteDatabase.rawQuery(queryForSynonymsWord, arrayOf(ContentUris.parseId(uri).toString()))
            ANTONYM_FOR_WORD_LIST -> sqliteDatabase.rawQuery(queryForAntonymsWord, arrayOf(ContentUris.parseId(uri).toString()))
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
//            WORD_TYPE_ITEM -> sqliteDatabase.update(VocabContract.WordTypeEntry.TABLE_NAME, contentValues, VocabContract.WordTypeEntry._, selectionArgs)
            WORD_ITEM -> sqliteDatabase.update(VocabContract.WordEntry.TABLE_NAME, contentValues , "${VocabContract.WordEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()))
//            SENTENCE_ITEM ->
            SYNONYM_ITEM -> sqliteDatabase.update(VocabContract.SynonymEntry.TABLE_NAME, contentValues, "${VocabContract.SynonymEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()))
            ANTONYM_ITEM -> sqliteDatabase.update(VocabContract.AntonymEntry.TABLE_NAME, contentValues, "${VocabContract.AntonymEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()))
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
//            WORD_TYPE_ITEM -> sqliteDatabase.delete(VocabContract.WordTypeEntry.TABLE_NAME, selection, selectionArgs)
            WORD_ITEM -> sqliteDatabase.delete(VocabContract.WordEntry.TABLE_NAME, "${VocabContract.WordEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()))
            SYNONYM_ITEM -> sqliteDatabase.delete(VocabContract.SynonymEntry.TABLE_NAME, "${VocabContract.SynonymEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()))
            ANTONYM_ITEM -> sqliteDatabase.delete(VocabContract.AntonymEntry.TABLE_NAME, "${VocabContract.AntonymEntry._ID}=?", arrayOf(ContentUris.parseId(uri).toString()))
            else -> throw IllegalArgumentException("Uri not supported: $uri")
        }
        if (rows != 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rows
    }


    override fun getType(uri: Uri?): String {
        return when (URI_MATCHER.match(uri)) {
            WORD_TYPE_LIST -> VocabContract.WordTypeEntry.CONTENT_LIST_TYPE
            WORD_TYPE_ITEM -> VocabContract.WordTypeEntry.CONTENT_ITEM_TYPE
            WORD_LIST -> VocabContract.WordEntry.CONTENT_LIST_TYPE
            WORD_ITEM -> VocabContract.WordEntry.CONTENT_ITEM_TYPE
            SENTENCE_LIST -> VocabContract.SentenceEntry.CONTENT_LIST_TYPE
            SENTENCE_ITEM -> VocabContract.SentenceEntry.CONTENT_ITEM_TYPE
            SYNONYM_ITEM -> VocabContract.SynonymEntry.CONTENT_LIST_TYPE
            ANTONYM_ITEM -> VocabContract.AntonymEntry.CONTENT_LIST_TYPE
            // Todo: Custom items left
            else -> throw IllegalArgumentException("No type for uri: $uri")
        }
    }

}