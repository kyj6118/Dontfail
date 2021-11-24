package com.example.afinal.Schdule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.afinal.VO.MyDairy
import com.example.afinal.VO.calenderVO

class MyDiaryListAdapter(val calendarList:MutableList<calenderVO>) : BaseAdapter() {
    override fun getCount(): Int {
        return calendarList.size
    }

    override fun getItem(position: Int): Any {
        return  calendarList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        if (view == null) {


            view = LayoutInflater.from(parent?.context)
                .inflate(com.example.afinal.R.layout.diary_list_item, parent, false)
        }



        val calendar =  calendarList[position]
        val content = view?.findViewById<TextView>(com.example.afinal.R.id.contentArea)



            content?.text=calendar.content



        return view!!
    }




}