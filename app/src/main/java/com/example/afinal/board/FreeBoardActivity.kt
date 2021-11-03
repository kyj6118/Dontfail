package com.example.afinal.board

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.R.layout.activity_free_board
import com.example.afinal.VO.board
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityFreeBoardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FreeBoardActivity : AppCompatActivity() {


    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityFreeBoardBinding
    lateinit var listView: ListView
    private val boardList = mutableListOf<board>()
    private val boardKeyList = mutableListOf<String>()

    private lateinit var boardRVAdapter: FreeBoardListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_free_board)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_board)


        boardRVAdapter = FreeBoardListAdapter(boardList)
        binding.boardListView.adapter = boardRVAdapter

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        getFBBoardData()



            //글쓰기 액티비티 이동
        val ImageView: ImageView = findViewById(R.id.wirteBtn)
        ImageView.setOnClickListener {
          val intent = Intent(this, BoardWriteActivity::class.java)
            startActivity(intent)

        }
        //상세정보 보기 페이

       binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, FreeBoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }
    }



    private fun getFBBoardData() {
        boardRVAdapter = FreeBoardListAdapter(boardList)
        binding.boardListView.adapter = boardRVAdapter
        val db = Firebase.firestore

        boardList.clear()


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(ContentValues.TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(board::class.java)
                    boardList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())

                }


                boardKeyList.reverse()
                boardList.reverse()
                boardRVAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)

        /*db.collection("board")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {


                    boardList.add(
                        board(
                            document["title"].toString(),
                            document["contents"].toString(),
                            document["time"].toString(),
                            document["email"].toString()
                        )
                    )

                    boardKeyList.add(document.id.toString())

                }
                boardKeyList.reverse()
                boardList.reverse()
                boardRVAdapter.notifyDataSetChanged()

            }*/
    }
}

