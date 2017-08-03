package com.android.vocab.fragment.dialog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.android.vocab.R
import com.android.vocab.holder.TextShowHolder
import com.android.vocab.provider.bean.Word
import com.android.vocab.provider.getWords


@Suppress("unused")
class RecyclerViewDialog: DialogFragment() {

    private val LOG_TAG: String = RecyclerViewDialog::class.java.simpleName


    companion object {
        val TYPE_OF_DATA: String = "TYPE_OF_DATA"
    }


    enum class SupportedTypes {
        SYNONYM, ANTONYM
    }


    interface OnItemSelected {
        fun processSelectedItem(type: SupportedTypes, id: Long)
    }


    var selectedWord: Long = 0L
    lateinit var list: List<Word>
    lateinit var type: SupportedTypes

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnItemSelected) {
            throw ClassCastException("Must implement OnItemSelect")
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        type = SupportedTypes.valueOf(arguments.getString(TYPE_OF_DATA))

        dialog.setCanceledOnTouchOutside(false)
        val view: View = inflater!!.inflate(R.layout.dialog_recyclerview, container, false)
        (view.findViewById(R.id.btnDismiss) as Button).setOnClickListener { dismiss() }

        val rv: RecyclerView = view.findViewById(R.id.rvList) as RecyclerView
        rv.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        list = getWords(context)
        rv.adapter = WordListAdapter()
        return view
    }


    inner class WordListAdapter: RecyclerView.Adapter<TextShowHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TextShowHolder =
                TextShowHolder(LayoutInflater.from(context).inflate(R.layout.text_view_for_display, parent, false))

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: TextShowHolder?, position: Int) {
            holder?.textView?.text = list[position].word
            holder?.textView?.setOnClickListener {
                (context as OnItemSelected).processSelectedItem(type, list[position].wordId)
                dismiss()
            }
        }

    }

}