package com.android.vocab.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.android.vocab.R
import com.android.vocab.databinding.ActivityWordDetailBinding
import com.android.vocab.provider.bean.WordAndType
import com.android.vocab.provider.getWordsWithType


@Suppress("unused")
class WordDetailActivity : AppCompatActivity() {

    private val LOG_TAG: String = WordDetailActivity::class.java.simpleName

    private lateinit var binding: ActivityWordDetailBinding

    companion object {
        val WORD_TO_SHOW: String = "WORD_TO_SHOW"
        val SHOW_WORD_REQUEST:Int = 0
        val CHANGE_OCCURRED_RESULT: Int = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_detail)
        binding.word = intent.getParcelableExtra(WORD_TO_SHOW)

        val toolBar: Toolbar = findViewById(R.id.appBarWordDetail) as Toolbar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

}
