package com.android.vocab.activity

import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.android.vocab.R
import com.android.vocab.bean.Word
import com.android.vocab.provider.VocabContract
import java.util.*


@Suppress("unused")
class WordEditorActivity : AppCompatActivity() {

    val LOG_TAG: String = WordEditorActivity::class.java.simpleName

    private lateinit var tilNewWord: TextInputLayout
    private lateinit var spinnerWordType: Spinner
    /*private lateinit var spinnerAntonym: Spinner
    private lateinit var spinnerSynonym: Spinner*/
    private lateinit var btnAddWord: Button
    private lateinit var tilMeaning: TextInputLayout
    private var isEdit: Boolean = false
    private var word: Word? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_editor)

        setSupportActionBar(findViewById(R.id.appBarInsertWord) as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tilNewWord = findViewById(R.id.tilWord) as TextInputLayout
        spinnerWordType = findViewById(R.id.spinnerWordType) as Spinner
        tilMeaning = findViewById(R.id.tilMeaning) as TextInputLayout
        /*spinnerSynonym = findViewById(R.id.spinnerSynonym) as Spinner
        spinnerAntonym = findViewById(R.id.spinnerAntonym) as Spinner*/

        val wordTypes: ArrayList<String> = ArrayList()
        contentResolver.query(VocabContract.WordTypeEntry.CONTENT_URI, null, null, null, null)?.use {
            cursor ->
            while (cursor.moveToNext()) {
                wordTypes.add(cursor.getString(cursor.getColumnIndex(VocabContract.WordTypeEntry.COLUMN_TYPE_NAME)))
            }
            spinnerWordType.adapter = ArrayAdapter<String>(baseContext, R.layout.support_simple_spinner_dropdown_item, wordTypes)
        }

        if (intent.getParcelableExtra<Word>(WordsActivity.WORD_SENT) !== null) {
            isEdit = true
            word = intent.getParcelableExtra(WordsActivity.WORD_SENT)
            tilNewWord.editText?.setText(word?.word)
            spinnerWordType.setSelection(wordTypes.indexOf(word?.wordType))
            tilMeaning.editText?.setText(word?.meaning)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.miDelete -> {
                val confirmDelete: Snackbar = Snackbar.make(
                        findViewById(R.id.activity_editor_word_cl),
                        getString(R.string.confirm_word_delete),
                        Snackbar.LENGTH_LONG)
                confirmDelete.setAction(getString(R.string.yes), {
                    contentResolver.delete(
                            ContentUris.withAppendedId(VocabContract.WordEntry.CONTENT_URI, word?.wordId?.toLong() as Long), null, null)
                })
                confirmDelete.setActionTextColor(getColor(R.color.colorAccent))
                confirmDelete.show()
                return true
            }

            R.id.miDone -> {
                // Update when new item
                if (!isEdit) {
                    var trouble: Boolean = false
                    if (tilNewWord.editText?.text.toString().isBlank()) {
                        tilNewWord.error = getString(R.string.word_not_entered)
                        trouble = true
                    }

                    if (tilMeaning.editText?.text.toString().isBlank()) {
                        tilMeaning.error = getString(R.string.meaning_not_entered)
                        trouble = true
                    }

                    if (trouble) {
                        return true
                    }

                    val contentValues: ContentValues = ContentValues()
                    contentValues.put(VocabContract.WordEntry.COLUMN_WORD, tilNewWord.editText?.text.toString())
                    contentValues.put(VocabContract.WordEntry.COLUMN_TYPE, spinnerWordType.selectedItem?.toString())
                    contentValues.put(VocabContract.WordEntry.COLUMN_MEANING, tilMeaning.editText?.text.toString())
                    val uri: Uri? = contentResolver.insert(VocabContract.WordEntry.CONTENT_URI, contentValues)

                    if (uri != null) {
                        val confirmInsert: Snackbar = Snackbar.make(
                                findViewById(R.id.activity_editor_word_cl),
                                getString(R.string.confirm_word_inserted),
                                Snackbar.LENGTH_LONG)
                        confirmInsert.setAction(getString(R.string.undo), { contentResolver.insert(VocabContract.WordEntry.CONTENT_URI, contentValues) })
                        confirmInsert.setActionTextColor(getColor(R.color.colorAccent))
                        confirmInsert.show()
                    }
                // Add when old item
                } else {
                    var trouble: Boolean = false
                    if (tilNewWord.editText?.text.toString().isBlank()) {
                        tilNewWord.error = getString(R.string.word_not_entered)
                        trouble = true
                    }

                    if (tilMeaning.editText?.text.toString().isBlank()) {
                        tilMeaning.error = getString(R.string.meaning_not_entered)
                        trouble = true
                    }

                    if (trouble) {
                        return true
                    }

                    val contentValues: ContentValues = ContentValues()
                    contentValues.put(VocabContract.WordEntry._ID, word?.wordId)
                    contentValues.put(VocabContract.WordEntry.COLUMN_WORD, tilNewWord.editText?.text.toString())
                    contentValues.put(VocabContract.WordEntry.COLUMN_TYPE, spinnerWordType.selectedItem?.toString())
                    contentValues.put(VocabContract.WordEntry.COLUMN_MEANING, tilMeaning.editText?.text.toString())
                    contentValues.put(VocabContract.WordEntry.COLUMN_FREQUENCY, word?.frequency?.plus(1))
                    contentValues.put(VocabContract.WordEntry.COLUMN_CREATE_TIME, word?.createTime.toString())
                    contentValues.put(VocabContract.WordEntry.COLUMN_LAST_ACCESS, Date().toString())

                    val confirmEdit: Snackbar = Snackbar.make(
                            findViewById(R.id.activity_editor_word_cl),
                            getString(R.string.confirm_word_edit),
                            Snackbar.LENGTH_LONG)
                    confirmEdit.setAction(getString(R.string.yes), { contentResolver.update(ContentUris.withAppendedId(VocabContract.WordEntry.CONTENT_URI, word?.wordId?.toLong() as Long), contentValues, null, null) })
                    confirmEdit.setActionTextColor(getColor(R.color.colorAccent))
                    confirmEdit.show()
                }
                return true
            }
            else -> return false
        }
    }

}
