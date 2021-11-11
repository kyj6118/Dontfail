package com.example.afinal.Schdule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.afinal.VO.MyDairy

class MyDiaryListAdapter(val mydiaryList:MutableList<MyDairy>) : BaseAdapter() {
    override fun getCount(): Int {
        return mydiaryList.size
    }

    override fun getItem(position: Int): Any {
        return  mydiaryList[position]
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


        val diary = mydiaryList[position]
        val diaryTime = view?.findViewById<TextView>(com.example.afinal.R.id.timeArea)
        val diarycontent = view?.findViewById<TextView>(com.example.afinal.R.id.contentArea)

        diaryTime!!.text=diary.time
        diarycontent!!.text=diary.content



        return view!!
    }




}