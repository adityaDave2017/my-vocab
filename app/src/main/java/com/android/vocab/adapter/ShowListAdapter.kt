package com.android.vocab.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.vocab.R
import com.android.vocab.holder.AddButtonHolder
import com.android.vocab.holder.TextShowHolder
import com.android.vocab.provider.bean.Antonym
import com.android.vocab.provider.bean.Sentence
import com.android.vocab.provider.bean.Synonym


@Suppress("unused")
class ShowListAdapter(val context:Context, val type: SupportedTypes, val list: List<*>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOG_TAG: String = ShowListAdapter::class.java.simpleName

    private val TEXT_VIEW_TYPE: Int = 0
    private val BUTTON_VIEW_TYPE: Int = 1


    init {
        if (context !is ShowListAdapter.OnButtonClicked) {
            throw ClassCastException("Must implement AddButtonHolder.OnButtonClicked")
        }
    }


    interface OnButtonClicked {
        fun onButtonClicked(type: SupportedTypes)
    }


    enum class SupportedTypes {
        SENTENCE_TYPE, SYNONYM_TYPE, ANTONYM_TYPE
    }


    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is TextShowHolder) {
            holder.textView.text = when(type) {
                SupportedTypes.SENTENCE_TYPE -> (list[position] as Sentence).sentence
                SupportedTypes.SYNONYM_TYPE -> (list[position] as Synonym).synonymId.toString()
                SupportedTypes.ANTONYM_TYPE -> (list[position] as Antonym).antonymWordId.toString()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
        if(viewType == BUTTON_VIEW_TYPE) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.add_button, parent, false)
            AddButtonHolder(
                    view,
                    when(type){ SupportedTypes.SENTENCE_TYPE -> context.resources.getString(R.string.add_sentence)
                                SupportedTypes.SYNONYM_TYPE -> context.resources.getString(R.string.add_synonym)
                                SupportedTypes.ANTONYM_TYPE -> context.resources.getString(R.string.add_antonym)
                    },
                    object: AddButtonHolder.OnButtonClicked {
                            override fun buttonClicked() = (context as OnButtonClicked).onButtonClicked(type)
                    }
            )
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.text_view_for_display, parent, false)
            TextShowHolder(view)
        }


    override fun getItemViewType(position: Int): Int  = if(list[position] == null) BUTTON_VIEW_TYPE else TEXT_VIEW_TYPE

}