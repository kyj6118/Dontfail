package com.example.afinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.example.afinal.VO.ReviewVO
import com.example.afinal.VO.evaluate

class ReviewListAdater  (val reviewList:MutableList<ReviewVO>): BaseAdapter(){
    override fun getCount(): Int {
        return reviewList.size
    }

    override fun getItem(position: Int): Any {
        return  reviewList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?):

            View {
        var view =convertView
        if (view == null) {

            view = LayoutInflater.from(parent?.context).inflate(R.layout.review_list_item, parent, false)
        }


        val name = view?.findViewById<TextView>(R.id.place_name)
        val address = view?.findViewById<TextView>(R.id.place_area)
        val rating = view?.findViewById<RatingBar>(R.id.review_Rating)



        val review=reviewList[position]


        name!!.text=review.place_name
        address!!.text=review.content
        rating!!.rating= review.place_rating!!

        return view!!
    }
}