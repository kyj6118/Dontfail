package com.example.afinal.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.example.afinal.R
import com.example.afinal.VO.evaluate

class ResListAdapter  (val ResList:MutableList<Restaurant>): BaseAdapter(){
    override fun getCount(): Int {
        return ResList.size
    }

    override fun getItem(position: Int): Any {
        return  ResList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?):

            View {
        var view =convertView
        if (view == null) {

            view = LayoutInflater.from(parent?.context).inflate(R.layout.res_list_item, parent, false)
        }


        val title = view?.findViewById<TextView>(R.id.titleArea)
        val contents = view?.findViewById<TextView>(R.id.contentArea)
        val rating = view?.findViewById<RatingBar>(R.id.tasteRating)

        val Res=ResList[position]


        title!!.text=Res.title
        contents!!.text=Res.contents
        rating!!.rating= Res.tasterating!!


        return view!!
    }
}