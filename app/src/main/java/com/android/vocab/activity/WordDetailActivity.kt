package com.android.vocab.activity

import android.content.ContentUris
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.android.vocab.R
import com.android.vocab.adapter.ShowListAdapter
import com.android.vocab.databinding.ActivityWordDetailBinding
import com.android.vocab.fragment.dialog.InputDialog
import com.android.vocab.fragment.dialog.RecyclerViewDialog
import com.android.vocab.provider.*
import com.android.vocab.provider.bean.*


@Suppress("unused")
class WordDetailActivity : AppCompatActivity(),
                            ShowListAdapter.OnButtonClicked,
                            InputDialog.OnInputReceived,
                            RecyclerViewDialog.OnItemSelected {

    private val LOG_TAG: String = WordDetailActivity::class.java.simpleName

    private lateinit var binding: ActivityWordDetailBinding
    private val sentenceList: ArrayList<Sentence?> = arrayListOf(null)
    private val synonymList: ArrayList<SynonymWord?> = arrayListOf(null)
    private val antonymList: ArrayList<AntonymWord?> = arrayListOf(null)

    private val SENTENCE_ADD_DIALOG_TAG: String = "SENTENCE_ADD_DIALOG_TAG"
    private val SYNONYM_ADD_DIALOG_TAG: String = "SYNONYM_ADD_DIALOG_TAG"
    private val ANTONYM_ADD_DIALOG_TAG: String = "ANTONYM_ADD_DIALOG_TAG"

    companion object {
        val WORD_TO_SHOW: String = "WORD_TO_SHOW"
        val SHOW_WORD_REQUEST:Int = 0
        val CHANGE_OCCURRED_RESULT: Int = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_detail)
        val toolBar: Toolbar = findViewById(R.id.appBarWordDetail) as Toolbar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.word = intent.getParcelableExtra(WORD_TO_SHOW)
        binding.detailContent.rvSentenceContainer.adapter = ShowListAdapter(this@WordDetailActivity, ShowListAdapter.SupportedTypes.SENTENCE_TYPE, sentenceList)
        binding.detailContent.rvSynonymContainer.adapter = ShowListAdapter(this@WordDetailActivity, ShowListAdapter.SupportedTypes.SYNONYM_TYPE, synonymList)
        binding.detailContent.rvAntonymContainer.adapter = ShowListAdapter(this@WordDetailActivity, ShowListAdapter.SupportedTypes.ANTONYM_TYPE, antonymList)

        loadSentences()
        loadSynonyms()
        loadAntonyms()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_word_detail, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.miEdit -> {
                val intent: Intent = Intent(this@WordDetailActivity, WordEditorActivity::class.java)
                intent.putExtra(WordEditorActivity.WORD_TO_EDIT, binding.word)
                intent.putExtra(WordEditorActivity.PARENT_ACTIVITY_CLASS, WordDetailActivity::class.java.name)
                startActivityForResult(intent, WordEditorActivity.EDIT_REQUEST)
                true
            }
            else -> false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == WordEditorActivity.CHANGE_OCCURRED_RESULT) {
            val list: ArrayList<WordAndType> = getWordsWithType(baseContext, binding.word.wordId)
            if (list.size == 0) {
                setResult(CHANGE_OCCURRED_RESULT)
                finish()
            } else {
                binding.word = list[0]
            }
        }
    }


    override fun onButtonClicked(type: ShowListAdapter.SupportedTypes) {
        when(type) {
            ShowListAdapter.SupportedTypes.SENTENCE_TYPE -> {
                val inputDialog: InputDialog = InputDialog()
                inputDialog.show(supportFragmentManager, SENTENCE_ADD_DIALOG_TAG)
            }
            ShowListAdapter.SupportedTypes.SYNONYM_TYPE -> {
                val wordSelect: RecyclerViewDialog = RecyclerViewDialog()
                val bundle: Bundle = Bundle()
                bundle.putString(RecyclerViewDialog.TYPE_OF_DATA, RecyclerViewDialog.SupportedTypes.SYNONYM.toString())
                wordSelect.arguments = bundle
                wordSelect.show(supportFragmentManager, SYNONYM_ADD_DIALOG_TAG)
            }
            ShowListAdapter.SupportedTypes.ANTONYM_TYPE -> {
                val wordSelect: RecyclerViewDialog = RecyclerViewDialog()
                val bundle: Bundle = Bundle()
                bundle.putString(RecyclerViewDialog.TYPE_OF_DATA, RecyclerViewDialog.SupportedTypes.ANTONYM.toString())
                wordSelect.arguments = bundle
                wordSelect.show(supportFragmentManager, ANTONYM_ADD_DIALOG_TAG)
            }
        }
    }


    override fun useInput(text: String) {
        insertSentence(baseContext, Sentence(wordId = binding.word.wordId, sentence = text))
        loadSentences()
    }


    override fun processSelectedItem(type: RecyclerViewDialog.SupportedTypes, id: Long) {
        when(type) {
            RecyclerViewDialog.SupportedTypes.SYNONYM -> {
                insertSynonym(baseContext, Synonym(mainWordId = binding.word.wordId, synonymWordId = id))
                loadSynonyms()
            }
            RecyclerViewDialog.SupportedTypes.ANTONYM -> {
                insertAntonym(baseContext, Antonym(mainWordId = binding.word.wordId, antonymWordId = id))
                loadAntonyms()
            }
        }
    }


    fun loadSentences() {
        sentenceList.clear()
        sentenceList.addAll(0, getSentences(baseContext, binding.word.wordId))
        sentenceList.add(null)
        binding.detailContent.rvSentenceContainer.adapter.notifyDataSetChanged()
    }


    fun loadSynonyms() {
        synonymList.clear()
        synonymList.addAll(0, getSynonyms(baseContext, binding.word.wordId))
        synonymList.add(null)
        binding.detailContent.rvSynonymContainer.adapter.notifyDataSetChanged()
    }


    fun loadAntonyms() {
        antonymList.clear()
        antonymList.addAll(0, getAntonyms(baseContext, binding.word.wordId))
        antonymList.add(null)
        binding.detailContent.rvAntonymContainer.adapter.notifyDataSetChanged()
    }

}