package com.android.vocab.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.android.vocab.R


@Suppress("unused")
class AddButtonHolder(itemView: View, title: String, callback: OnButtonClicked): RecyclerView.ViewHolder(itemView) {

    interface OnButtonClicked {
        fun buttonClicked()
    }

    init {
        val button: Button = (itemView.findViewById(R.id.add_button) as Button)
        button.text = title
        button.setOnClickListener({ callback.buttonClicked() })
    }

}