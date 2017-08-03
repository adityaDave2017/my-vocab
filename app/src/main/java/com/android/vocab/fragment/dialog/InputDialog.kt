package com.android.vocab.fragment.dialog

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.android.vocab.R


@Suppress("unused")
class InputDialog: DialogFragment() {

    private val LOG_TAG: String = InputDialog::class.java.simpleName


    interface OnInputReceived {
        fun useInput(text: String)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnInputReceived) {
            throw ClassCastException("Must implement InputDialog.OnInputReceived")
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setCanceledOnTouchOutside(false)
        val view: View = View.inflate(context, R.layout.dialog_input_text, container)
        val etInput: EditText = (view.findViewById(R.id.tilInputText) as TextInputLayout).editText!!
        val btnConfirm: Button = view.findViewById(R.id.btnConfirm) as Button
        btnConfirm.setOnClickListener {
            if (etInput.text.isNullOrEmpty()) {
                etInput.error = "Enter a sentence"
            } else {
                (context as OnInputReceived).useInput(etInput.text.toString())
                dismiss()
            }
        }
        val btnCancel: Button = view.findViewById(R.id.btnCancel) as Button
        btnCancel.setOnClickListener { dismiss() }
        return view
    }

}