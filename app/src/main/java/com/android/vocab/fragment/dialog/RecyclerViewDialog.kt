package com.android.vocab.fragment.dialog

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


@Suppress("unused")
class RecyclerViewDialog: DialogFragment() {

    private val LOG_TAG: String = RecyclerViewDialog::class.java.simpleName


    interface OnItemSelected {
        fun processSelectedItem()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog.setCanceledOnTouchOutside(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}