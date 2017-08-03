package com.android.vocab.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.vocab.R
import com.android.vocab.adapter.TypeListAdapter
import com.android.vocab.databinding.ActivityWordEditorBinding
import com.android.vocab.provider.bean.Word
import com.android.vocab.provider.bean.WordAndType
import com.android.vocab.provider.bean.WordType
import com.android.vocab.provider.deleteWord
import com.android.vocab.provider.getWordTypes
import com.android.vocab.provider.insertNewWord
import com.android.vocab.provider.updateWord
import com.android.vocab.utils.hideSoftKeyboard
import kotlinx.android.synthetic.main.content_word_editor.*


@Suppress("unused")
class WordEditorActivity : AppCompatActivity() {

    val LOG_TAG: String = WordEditorActivity::class.java.simpleName

    companion object {
        val ADD_REQUEST:Int = 1
        val EDIT_REQUEST:Int = 2
        val NO_CHANGE_RESULT: Int = 3
        val CHANGE_OCCURRED_RESULT: Int = 4
        val WORD_TO_EDIT: String = "WORD_TO_EDIT"
        val PARENT_ACTIVITY_CLASS: String = "PARENT_ACTIVITY"
    }

    private var isEdit: Boolean = false
    private lateinit var binding: ActivityWordEditorBinding
    private lateinit var prevWord: WordAndType


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getStringExtra(PARENT_ACTIVITY_CLASS) == null) {
            throw Exception("Parent Activity must be specified")
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_editor)
        binding.word = if (intent.getParcelableExtra<Word>(WORD_TO_EDIT) != null) {
            isEdit = true
            prevWord = intent.getParcelableExtra(WORD_TO_EDIT)
            prevWord.makeClone()
        } else {
            WordAndType()
        }

        setSupportActionBar(findViewById(R.id.appBarEditWord) as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val typeList: ArrayList<WordType> = ArrayList()
        typeList.add(WordType(typeName = getString(R.string.select_type)))
        typeList.addAll(getWordTypes(baseContext))
        spinnerWordType.adapter =
                TypeListAdapter(baseContext, R.layout.support_simple_spinner_dropdown_item, typeList)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.miDelete -> {
                hideSoftKeyboard(this)
                deleteWord(baseContext, binding.word)
                setResult(CHANGE_OCCURRED_RESULT)
                finish()
                return true
            }

            R.id.miDone -> {
                hideSoftKeyboard(this)
                // Update when old item
                if (isEdit) {
                    if (!isValidInput()) {
                        return true
                    }
                    if (binding.word != prevWord) {
                        updateWord(baseContext, binding.word)
                        setResult(CHANGE_OCCURRED_RESULT)
                    } else {
                        Toast.makeText(baseContext, getString(R.string.no_change), Toast.LENGTH_SHORT).show()
                    }
                    finish()
                // Add when new item
                } else {
                    if (!isValidInput()) {
                        return true
                    }
                    insertNewWord(baseContext, binding.word)
                    setResult(CHANGE_OCCURRED_RESULT)
                    finish()
                }
                return true
            }

            else -> return false
        }
    }


    private fun isValidInput(): Boolean {
        var valid: Boolean = true
        if (binding.word.word.isEmpty()) {
            valid = false
            tilWord.editText?.error = getString(R.string.error_word_enter)
        }
        if (binding.word.meaning.isEmpty()) {
            valid = false
            tilMeaning.editText?.error = getString(R.string.error_meaning_enter)
        }
        if (spinnerWordType.selectedItemPosition == 0) {
            valid = false
            Toast.makeText(baseContext, getString(R.string.select_type), Toast.LENGTH_SHORT).show()
        }
        return valid
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
