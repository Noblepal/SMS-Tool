package com.intelligence.smscounter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.row_layout2.view.*
import java.util.*

class ListAdapter(val context: Context, val list: ArrayList<SMSData>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout2, parent, false)

        view.tv_sender.text = list[position].senderName
        view.tv_date.text = list[position].date
        view.tv_message.text = list[position].message

        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}
