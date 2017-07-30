package com.android.vocab.activity

import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.vocab.R
import com.android.vocab.adapter.TypeListAdapter
import com.android.vocab.databinding.ActivityWordEditorBinding
import com.android.vocab.provider.bean.Word
import com.android.vocab.provider.bean.WordType
import com.android.vocab.provider.deleteWord
import com.android.vocab.provider.getWordTypes
import com.android.vocab.provider.insertNewWord
import com.android.vocab.provider.updateWord
import com.android.vocab.utils.hideSoftKeyboard
import kotlinx.android.synthetic.main.activity_word_editor.*
import kotlinx.android.synthetic.main.content_word_editor.*


@Suppress("unused")
class WordEditorActivity : AppCompatActivity() {

    val LOG_TAG: String = WordEditorActivity::class.java.simpleName


    private var isEdit: Boolean = false
    private lateinit var binding: ActivityWordEditorBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_editor)
        binding.word = if (intent.getParcelableExtra<Word>(WordsActivity.WORD_SENT) != null) {
            isEdit = true
            intent.getParcelableExtra(WordsActivity.WORD_SENT)
        } else {
            Word()
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
                val confirmDelete: Snackbar = Snackbar.make(
                        findViewById(R.id.activity_editor_word_cl),
                        getString(R.string.confirm_word_delete),
                        Snackbar.LENGTH_LONG)
                confirmDelete.setAction(getString(R.string.yes), { deleteWord(baseContext, binding.word!!) })
                confirmDelete.setActionTextColor(getColor(R.color.colorAccent))
                confirmDelete.show()
                return true
            }

            R.id.miDone -> {
                hideSoftKeyboard(this)
                // Update when new item
                if (!isEdit) {
                    if (!isValidInput()) {
                        return true
                    }

                    val uri: Uri? = insertNewWord(baseContext, binding.word!!)
                    if (uri != null) {
                        val confirmInsert: Snackbar = Snackbar.make(
                                findViewById(R.id.activity_editor_word_cl),
                                getString(R.string.confirm_word_inserted),
                                Snackbar.LENGTH_LONG)
                        confirmInsert.setAction(getString(R.string.undo), { deleteWord(baseContext, binding.word!!) })
                        confirmInsert.setActionTextColor(getColor(R.color.colorAccent))
                        confirmInsert.show()
                    }
                    // Add when old item
                } else {
                    if (!isValidInput()) {
                        return true
                    }

                    val confirmEdit: Snackbar = Snackbar.make(
                            findViewById(R.id.activity_editor_word_cl),
                            getString(R.string.confirm_word_edit),
                            Snackbar.LENGTH_LONG)
                    confirmEdit.setAction(getString(R.string.yes), { updateWord(baseContext, binding.word!!) })
                    confirmEdit.setActionTextColor(getColor(R.color.colorAccent))
                    confirmEdit.show()
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

}
