package com.android.vocab.provider

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns


class VocabContract {

    private val LOG_TAG: String = VocabContract::class.java.simpleName

    companion object Contract {
        const val CONTENT_AUTHORITY: String = "com.android.vocab.VocabProvider"

        const val PATH_WORD_TYPE: String = "word_type"

        const val PATH_WORD: String = "word"

        const val PATH_WORD_AND_TYPE = "word_and_type"

        const val PATH_SENTENCE: String = "sentence"

        const val PATH_ANTONYM: String = "antonym"

        const val PATH_SYNONYM: String = "synonym"

        const val PATH_SYNONYM_FOR_WORD: String = "synonym_for_word"

        const val PATH_ANTONYM_FOR_WORD: String = "antonym_for_word"

        val BASE_URI: Uri = Uri.parse("content://" + CONTENT_AUTHORITY)
    }


    object WordTypeEntry: BaseColumns {

        val TABLE_NAME: String = "tblWordType"

        val _ID: String = "intTypeId"

        val COLUMN_TYPE_NAME:String = "strTypeName"

        val COLUMN_ABBREVIATION: String = "strAbbr"

        val CONTENT_URI: Uri = Uri.withAppendedPath(Contract.BASE_URI, Contract.PATH_WORD_TYPE)

        val CONTENT_LIST_TYPE: String = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_WORD_TYPE

        val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_WORD_TYPE

    }


    object WordEntry: BaseColumns {

        val TABLE_NAME: String = "tblWord"

        val _ID: String = "intWordId"

        val COLUMN_WORD: String = "strWord"

        val COLUMN_MEANING: String = "strMeaning"

        val COLUMN_TYPE_ID: String = "intTypeId"

        val COLUMN_FREQUENCY: String = "intFrequency"

        val COLUMN_CREATE_TIME: String = "tsCreateTime"

        val COLUMN_LAST_ACCESS_TIME: String = "tsLastAccess"

        val CONTENT_URI: Uri = Uri.withAppendedPath(Contract.BASE_URI, Contract.PATH_WORD)

        val CONTENT_LIST_TYPE: String = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_WORD

        val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_WORD

    }

    object WordAndTypeEntry: BaseColumns {

        val COLUMN_WORD_ID: String = "intWordId"

        val COLUMN_WORD: String = "strWord"

        val COLUMN_MEANING: String = "strMeaning"

        val COLUMN_FREQUENCY: String = "intFrequency"

        val COLUMN_CREATE_TIME: String = "tsCreateTime"

        val COLUMN_LAST_ACCESS_TIME: String = "tsLastAccess"

        val COLUMN_TYPE_ID: String = "intTypeId"

        val COLUMN_TYPE_NAME: String = "strTypeName"

        val COLUMN_ABBR:String = "strAbbr"

        val CONTENT_URI: Uri = Uri.withAppendedPath(Contract.BASE_URI, Contract.PATH_WORD_AND_TYPE)

        val CONTENT_LIST_TYPE: String = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_WORD_AND_TYPE

        val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_WORD_AND_TYPE

    }

    object SentenceEntry: BaseColumns {

        val TABLE_NAME: String = "tblSentence"

        val _ID: String = "intSentenceId"

        val COLUMN_WORD_ID: String = "intWordId"

        val COLUMN_SENTENCE: String = "strSentence"

        val COLUMN_CREATE_TIME: String = "tsCreateTime"

        val CONTENT_URI: Uri = Uri.withAppendedPath(Contract.BASE_URI, Contract.PATH_SENTENCE)

        val CONTENT_LIST_TYPE: String = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_SENTENCE

        val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_SENTENCE

    }


    object SynonymEntry: BaseColumns {

        val TABLE_NAME: String = "tblSynonym"

        val _ID: String = "intSynonymId"

        val COLUMN_MAIN_WORD_ID: String = "intMainWordId"

        val COLUMN_SYNONYM_WORD_ID: String = "intSynonymWordId"

        val CONTENT_URI: Uri = Uri.withAppendedPath(Contract.BASE_URI, Contract.PATH_SYNONYM)

        val CONTENT_LIST_TYPE: String = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_SYNONYM

        val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_SYNONYM

    }


    object AntonymEntry: BaseColumns {

        val TABLE_NAME: String = "tblAntonym"

        val _ID: String = "intAntonymId"

        val COLUMN_MAIN_WORD_ID: String = "intMainWordId"

        val COLUMN_ANTONYM_WORD_ID: String = "intAntonymWordId"

        val CONTENT_URI: Uri = Uri.withAppendedPath(Contract.BASE_URI, Contract.PATH_ANTONYM)

        val CONTENT_LIST_TYPE: String = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_ANTONYM

        val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_ANTONYM

    }


    object SynonymWordEntry: BaseColumns {

        val WORD_ID: String = "intWordId"

        val COLUMN_WORD: String = "strWord"

        val COLUMN_MEANING: String = "strMeaning"

        val COLUMN_TYPE_ID: String = "intTypeId"

        val COLUMN_FREQUENCY: String = "intFrequency"

        val COLUMN_CREATE_TIME: String = "tsCreateTime"

        val COLUMN_LAST_ACCESS_TIME: String = "tsLastAccess"

        val SYNONYM_ID: String = "intSynonymId"

        val COLUMN_MAIN_WORD_ID: String = "intMainWordId"

        val COLUMN_SYNONYM_WORD_ID: String = "intSynonymWordId"

        val CONTENT_URI: Uri = Uri.withAppendedPath(Contract.BASE_URI, Contract.PATH_SYNONYM_FOR_WORD)

        val CONTENT_LIST_TYPE: String = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_SYNONYM_FOR_WORD

        val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_SYNONYM_FOR_WORD

    }


    object AntonymWordEntry: BaseColumns {

        val WORD_ID: String = "intWordId"

        val COLUMN_WORD: String = "strWord"

        val COLUMN_MEANING: String = "strMeaning"

        val COLUMN_TYPE_ID: String = "intTypeId"

        val COLUMN_FREQUENCY: String = "intFrequency"

        val COLUMN_CREATE_TIME: String = "tsCreateTime"

        val COLUMN_LAST_ACCESS_TIME: String = "tsLastAccess"

        val ANTONYM_ID: String = "intAntonymId"

        val COLUMN_MAIN_WORD_ID: String = "intMainWordId"

        val COLUMN_ANTONYM_WORD_ID: String = "intAntonymWordId"

        val CONTENT_URI: Uri = Uri.withAppendedPath(Contract.BASE_URI, Contract.PATH_ANTONYM_FOR_WORD)

        val CONTENT_LIST_TYPE: String = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_ANTONYM_FOR_WORD

        val CONTENT_ITEM_TYPE: String = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Contract.CONTENT_AUTHORITY + "/" + Contract.PATH_ANTONYM_FOR_WORD

    }

}
