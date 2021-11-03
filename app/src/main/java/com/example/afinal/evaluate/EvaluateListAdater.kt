package com.example.afinal.evaluate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.example.afinal.R
import com.example.afinal.VO.evaluate


class EvaluateListAdater (val evaluateList:MutableList<evaluate>):BaseAdapter(){
    override fun getCount(): Int {
        return evaluateList.size
    }

    override fun getItem(position: Int): Any {
        return  evaluateList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?):

        View {
            var view =convertView
            if (view == null) {

                view = LayoutInflater.from(parent?.context).inflate(R.layout.evaluate_list_item, parent, false)
            }


        val title = view?.findViewById<TextView>(R.id.titleArea)
        val professor = view?.findViewById<TextView>(R.id.professorArea)
        val rating = view?.findViewById<RatingBar>(R.id.classRating)

        val evaluate=evaluateList[position]


        title!!.text=evaluate.title
        professor!!.text=evaluate.professor
        rating!!.rating= evaluate.rating!!

        return view!!
    }
}