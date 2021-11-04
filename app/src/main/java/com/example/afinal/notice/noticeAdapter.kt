package com.example.afinal.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.afinal.R
import com.example.afinal.VO.notice


class noticeAdapter (val noticeList : MutableList<notice>) : BaseAdapter() {
    override fun getCount(): Int {
        return noticeList.size

    }

    override fun getItem(position: Int): Any {
        return noticeList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {

            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.notice_list_item, parent, false)
        }




        val notice = view?.findViewById<TextView>(R.id.titleArea)


        val Notice = noticeList[position]


        notice!!.text = Notice.notice


        return view!!
    }
}
