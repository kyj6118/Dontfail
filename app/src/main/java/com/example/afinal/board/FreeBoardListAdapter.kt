package com.example.afinal.board

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.board

class FreeBoardListAdapter(val boardList:MutableList<board>
): BaseAdapter(){

    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?):


            View {

        var view = convertView
        if(view == null){

            view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent, false)
        }

        val itemLinearLayoutView = view?.findViewById<LinearLayout>(R.id.itemView)

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)
        val name = view?.findViewById<TextView>(R.id.nameArea)


        val board = boardList[position]

        title!!.text=board.title
        content!!.text=board.contents
        time!!.text=board.time
        name!!.text=board.email

        return view!!


    }


}

