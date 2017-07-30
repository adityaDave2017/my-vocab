package com.android.vocab.provider

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import com.android.vocab.provider.bean.Word
import com.android.vocab.provider.bean.WordAndType
import com.android.vocab.provider.bean.WordType


fun insertNewWord(context: Context, word: Word): Uri {
    val contentValues: ContentValues = ContentValues()
    contentValues.put(VocabContract.WordEntry.COLUMN_WORD, word.word)
    contentValues.put(VocabContract.WordEntry.COLUMN_TYPE_ID, word.typeId)
    contentValues.put(VocabContract.WordEntry.COLUMN_MEANING, word.meaning)
    contentValues.put(VocabContract.WordEntry.COLUMN_FREQUENCY, word.frequency)
    contentValues.put(VocabContract.WordEntry.COLUMN_CREATE_TIME, word.createTime)
    contentValues.put(VocabContract.WordEntry.COLUMN_LAST_ACCESS_TIME, word.lastAccessTime)
    return context.contentResolver.insert(VocabContract.WordEntry.CONTENT_URI, contentValues)
}


fun getWords(context: Context, id: Long = 0L): ArrayList<Word> {
    val list: ArrayList<Word> = ArrayList()
    context.contentResolver.query(
            if(id == 0L) {
                VocabContract.WordEntry.CONTENT_URI
            } else {
                ContentUris.withAppendedId(VocabContract.WordEntry.CONTENT_URI, id)
            },
            null,
            null,
            null,
            null
    ).use {
        cursor ->
        while (cursor.moveToNext()) {
            list.add(
                    Word(
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_WORD)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_TYPE_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_MEANING)),
                            cursor.getInt(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_FREQUENCY)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_CREATE_TIME)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_LAST_ACCESS_TIME))
                    )
            )
        }
    }
    return list
}


fun updateWord(context: Context, word: Word): Int {
    val contentValues: ContentValues = ContentValues()
    contentValues.put(VocabContract.WordEntry.COLUMN_WORD, word.word)
    contentValues.put(VocabContract.WordEntry.COLUMN_TYPE_ID, word.typeId)
    contentValues.put(VocabContract.WordEntry.COLUMN_MEANING, word.meaning)
    contentValues.put(VocabContract.WordEntry.COLUMN_FREQUENCY, word.frequency)
    contentValues.put(VocabContract.WordEntry.COLUMN_CREATE_TIME, word.createTime)
    contentValues.put(VocabContract.WordEntry.COLUMN_LAST_ACCESS_TIME, word.lastAccessTime)
    return context.contentResolver.update(ContentUris.withAppendedId(VocabContract.WordEntry.CONTENT_URI, word.wordId),
            contentValues,
            null,
            null
    )
}


fun deleteWord(context: Context, word: Word): Int = context.contentResolver.delete(
        ContentUris.withAppendedId(VocabContract.WordEntry.CONTENT_URI, word.wordId),
        null,
        null
)


fun getWordsWithType(context: Context, id: Long = 0L): ArrayList<WordAndType> {

    val list: ArrayList<WordAndType> = ArrayList()
    context.contentResolver.query(
            if(id == 0L) {
                VocabContract.WordAndTypeEntry.CONTENT_URI
            } else {
                ContentUris.withAppendedId(VocabContract.WordAndTypeEntry.CONTENT_URI, id)
            },
            null,
            null,
            null,
            null
    ).use {
        cursor ->
        while (cursor.moveToNext()) {
            list.add(
                    WordAndType(
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_WORD)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_TYPE_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_MEANING)),
                            cursor.getInt(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_FREQUENCY)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_CREATE_TIME)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordEntry.COLUMN_LAST_ACCESS_TIME)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_TYPE_NAME)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_ABBR))
                    )
            )
        }
    }
    return list

}


fun getWordTypes(context: Context, id:Long = 0L): ArrayList<WordType> {
    val list: ArrayList<WordType> = ArrayList()
    context.contentResolver.query(
            if(id == 0L) {
                VocabContract.WordTypeEntry.CONTENT_URI
            } else {
                ContentUris.withAppendedId(VocabContract.WordTypeEntry.CONTENT_URI, id)
            },
            null,
            null,
            null,
            null
    ).use {
        cursor ->
        while (cursor.moveToNext()) {
            list.add(
                    WordType(
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordTypeEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordTypeEntry.COLUMN_TYPE_NAME)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordTypeEntry.COLUMN_ABBREVIATION))
                    )
            )
        }
    }
    return list
}