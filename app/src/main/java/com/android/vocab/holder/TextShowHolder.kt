package com.android.vocab.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.android.vocab.R


class TextShowHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var textView: TextView = itemView.findViewById(R.id.tvTextShow) as TextView
}