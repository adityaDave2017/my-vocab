package com.android.vocab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.vocab.provider.bean.WordType
import com.android.vocab.utils.convertToDp


class TypeListAdapter(var ocontext: Context, var layout: Int, var typeList: ArrayList<WordType>) : ArrayAdapter<WordType>(ocontext, layout, typeList) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View =
            getCustomView(position, convertView, parent)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
            getCustomView(position, convertView, parent)

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row: View = convertView ?: LayoutInflater.from(ocontext).inflate(android.R.layout.simple_spinner_item, parent, false)
        val tv: TextView = row.rootView.findViewById(android.R.id.text1) as TextView
        val p15: Int = convertToDp(ocontext.resources.displayMetrics.density, 15)
        tv.setPadding(p15, p15, p15, p15)
        tv.text = typeList[position].typeName
        return row
    }

    override fun isEnabled(position: Int): Boolean = position != 0

}