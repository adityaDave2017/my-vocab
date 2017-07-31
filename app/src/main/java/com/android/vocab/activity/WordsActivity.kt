package com.android.vocab.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.android.vocab.R
import com.android.vocab.adapter.WordsAdapter
import com.android.vocab.provider.bean.Word
import com.android.vocab.provider.bean.WordAndType
import com.android.vocab.provider.getWordsWithType


@Suppress("unused")
class WordsActivity : AppCompatActivity(), WordsAdapter.OnWordClicked {

    private val LOG_TAG: String = WordsActivity::class.java.simpleName

    private lateinit var rvWords: RecyclerView
    private lateinit var tvNoWords: TextView
    private lateinit var pbWordsLoading: ProgressBar
    private lateinit var wordsAdapter: WordsAdapter
    private val wordsList: ArrayList<WordAndType> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)
        val toolbar = findViewById(R.id.appBarWords) as Toolbar
        setSupportActionBar(toolbar)

        rvWords = findViewById(R.id.rvWords) as RecyclerView
        tvNoWords = findViewById(R.id.tvNoWords) as TextView
        pbWordsLoading = findViewById(R.id.pbWordsLoading) as ProgressBar

        wordsAdapter = WordsAdapter(this, wordsList)
        rvWords.adapter = wordsAdapter
        rvWords.addItemDecoration(DividerItemDecoration(baseContext, LinearLayoutManager.VERTICAL))

        loadWords()

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { _: View ->
            val intent: Intent = Intent(this@WordsActivity, WordEditorActivity::class.java)
            intent.putExtra(WordEditorActivity.PARENT_ACTIVITY_CLASS, WordsActivity::class.java.name)
            startActivityForResult(intent, WordEditorActivity.ADD_REQUEST)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == WordEditorActivity.CHANGE_OCCURRED_RESULT
                || resultCode == WordDetailActivity.CHANGE_OCCURRED_RESULT) {
            loadWords()
        }
    }


    override fun wordClicked(word: Word) {
        val intent: Intent = Intent(this@WordsActivity, WordDetailActivity::class.java)
        intent.putExtra(WordDetailActivity.WORD_TO_SHOW, word)
        startActivityForResult(intent, WordDetailActivity.SHOW_WORD_REQUEST)
    }


    private fun loadWords() {
        val retrieved: ArrayList<WordAndType> = getWordsWithType(baseContext)
        pbWordsLoading.visibility = View.GONE
        if (retrieved.size == 0) {
            wordsList.clear()
            wordsAdapter.notifyDataSetChanged()
            rvWords.visibility = View.GONE
            tvNoWords.visibility = View.VISIBLE
        } else {
            wordsList.clear()
            tvNoWords.visibility = View.GONE
            rvWords.visibility = View.VISIBLE
            wordsList.addAll(retrieved)
            wordsAdapter.notifyDataSetChanged()
        }
    }

}
