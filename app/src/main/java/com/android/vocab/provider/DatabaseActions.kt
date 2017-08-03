@file:Suppress("unused")

package com.android.vocab.provider

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.vocab.provider.bean.*
import java.util.*
import kotlin.collections.ArrayList


fun insertNewWord(context: Context, word: Word): Uri {
    word.createTime = GregorianCalendar().timeInMillis
    word.lastAccessTime = word.createTime
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
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_WORD_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_WORD)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_TYPE_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_MEANING)),
                            cursor.getInt(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_FREQUENCY)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_CREATE_TIME)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.WordAndTypeEntry.COLUMN_LAST_ACCESS_TIME)),
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


fun insertSentence(context: Context, sentence: Sentence): Uri {
    val contentValues: ContentValues = ContentValues()
    contentValues.put(VocabContract.SentenceEntry.COLUMN_WORD_ID, sentence.wordId)
    contentValues.put(VocabContract.SentenceEntry.COLUMN_SENTENCE, sentence.sentence)
    contentValues.put(VocabContract.SentenceEntry.COLUMN_CREATE_TIME, GregorianCalendar().timeInMillis)
    return context.contentResolver.insert(VocabContract.SentenceEntry.CONTENT_URI, contentValues)
}


fun getSentences(context: Context, wordId: Long): ArrayList<Sentence> {
    val list: ArrayList<Sentence> = ArrayList()
    context.contentResolver.query(
            VocabContract.SentenceEntry.CONTENT_URI,
            null,
            "${VocabContract.SentenceEntry.COLUMN_WORD_ID} = ?",
            arrayOf(wordId.toString()),
            null
    ).use {
        cursor ->
        while (cursor.moveToNext()) {
            list.add(
                    Sentence(
                            cursor.getLong(cursor.getColumnIndex(VocabContract.SentenceEntry._ID)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.SentenceEntry.COLUMN_WORD_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.SentenceEntry.COLUMN_SENTENCE)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.SentenceEntry.COLUMN_CREATE_TIME))
                    )
            )
        }
    }
    return list
}


fun insertSynonym(context: Context, synonym: Synonym): Uri {
    val contentValues: ContentValues = ContentValues()
    contentValues.put(VocabContract.SynonymEntry.COLUMN_MAIN_WORD_ID, synonym.mainWordId)
    contentValues.put(VocabContract.SynonymEntry.COLUMN_SYNONYM_WORD_ID, synonym.synonymWordId)
    return context.contentResolver.insert(VocabContract.SynonymEntry.CONTENT_URI, contentValues)
}


fun getSynonyms(context: Context, id:Long): ArrayList<SynonymWord> {
    val list: ArrayList<SynonymWord> = ArrayList()
    context.contentResolver.query(
            ContentUris.withAppendedId(VocabContract.SynonymWordEntry.CONTENT_URI, id),
            null,
            null,
            null,
            null
    ).use {
        cursor ->
        while (cursor.moveToNext()) {
            list.add(
                    SynonymWord(
                            cursor.getLong(cursor.getColumnIndex(VocabContract.SynonymWordEntry.WORD_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.SynonymWordEntry.COLUMN_WORD)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.SynonymWordEntry.COLUMN_TYPE_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.SynonymWordEntry.COLUMN_MEANING)),
                            cursor.getInt(cursor.getColumnIndex(VocabContract.SynonymWordEntry.COLUMN_FREQUENCY)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.SynonymWordEntry.COLUMN_CREATE_TIME)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.SynonymWordEntry.COLUMN_LAST_ACCESS_TIME)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.SynonymWordEntry.SYNONYM_ID))
                    )
            )
        }
    }
    return list
}


fun insertAntonym(context: Context, antonym: Antonym): Uri {
    val contentValues: ContentValues = ContentValues()
    contentValues.put(VocabContract.AntonymEntry.COLUMN_MAIN_WORD_ID, antonym.mainWordId)
    contentValues.put(VocabContract.AntonymEntry.COLUMN_ANTONYM_WORD_ID, antonym.antonymWordId)
    return context.contentResolver.insert(VocabContract.AntonymEntry.CONTENT_URI, contentValues)
}


fun getAntonyms(context: Context, id:Long): ArrayList<AntonymWord> {
    val list: ArrayList<AntonymWord> = ArrayList()
    context.contentResolver.query(
            ContentUris.withAppendedId(VocabContract.AntonymWordEntry.CONTENT_URI, id),
            null,
            null,
            null,
            null
    ).use {
        cursor ->
        while (cursor.moveToNext()) {
            list.add(
                    AntonymWord(
                            cursor.getLong(cursor.getColumnIndex(VocabContract.AntonymWordEntry.WORD_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.AntonymWordEntry.COLUMN_WORD)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.AntonymWordEntry.COLUMN_TYPE_ID)),
                            cursor.getString(cursor.getColumnIndex(VocabContract.AntonymWordEntry.COLUMN_MEANING)),
                            cursor.getInt(cursor.getColumnIndex(VocabContract.AntonymWordEntry.COLUMN_FREQUENCY)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.AntonymWordEntry.COLUMN_CREATE_TIME)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.AntonymWordEntry.COLUMN_LAST_ACCESS_TIME)),
                            cursor.getLong(cursor.getColumnIndex(VocabContract.AntonymWordEntry.ANTONYM_ID))
                    )
            )
        }
    }
    return list
}