package com.android.vocab.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.vocab.R
import com.android.vocab.provider.bean.Word


@Suppress("unused")
class WordsAdapter(context: Context, val wordsList: ArrayList<Word>) : RecyclerView.Adapter<WordsAdapter.WordHolder>() {

    private val LOG_TAG: String = WordsAdapter::class.java.simpleName

    private var wordClicked: OnWordClicked

    init {
        wordClicked = context as OnWordClicked
    }

    interface OnWordClicked {
        fun wordClicked(word: Word)
    }

    override fun onBindViewHolder(holder: WordHolder?, position: Int) {
        holder?.tvWord?.text = wordsList[position].word
        holder?.tvWordType?.text = wordsList[position].typeId.toString()
        holder?.tvMeaning?.text = wordsList[position].meaning
        holder?.clContainer?.setOnClickListener({ wordClicked.wordClicked(wordsList[position]) })
    }

    override fun getItemCount(): Int = wordsList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WordHolder = WordHolder(
            LayoutInflater.from(parent?.context).inflate(R.layout.activity_words_item, parent, false)
    )

    inner class WordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.tvWord) as TextView
        val tvWordType: TextView = itemView.findViewById(R.id.tvWordType) as TextView
        val tvMeaning: TextView = itemView.findViewById(R.id.tvMeaning) as TextView
        val clContainer: ConstraintLayout = itemView.findViewById(R.id.word_item_cl) as ConstraintLayout
    }

}