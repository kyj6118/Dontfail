package com.example.afinal.evaluate

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.board
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityEvaluateBoardInsideBinding
import com.example.afinal.databinding.ActivityEvaluateBoardModifyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class EvaluateBoardModifyActivity : AppCompatActivity() {

    private lateinit var key:String
    private lateinit var binding: ActivityEvaluateBoardModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_board_modify)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_evaluate_board_modify)



        key = intent.getStringExtra("key").toString()

        getEvaluateBoardData(key)



        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }



    }

    private fun editBoardData(key: String) {

        FBRef.EvaluBoardRef
            .child(key)
            .setValue(
                evaluate(binding.classArea.text.toString(),
                    binding.professorArea.text.toString(),
                    binding.classRating.rating.toFloat(),
                    binding.contentArea.text.toString(),
                    FBAuth.getemail())
            )


        Toast.makeText(this, "Modify Success", Toast.LENGTH_LONG).show()

        finish()
    }

    private fun getEvaluateBoardData(key: String) {



        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(evaluate::class.java)
//                Log.d(TAG, dataModel.toString())
//                Log.d(TAG, dataModel!!.title)
//                Log.d(TAG, dataModel!!.time)

                binding.classArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.contents)
                binding.professorArea.setText(dataModel?.professor)
                binding.classRating.rating = dataModel!!.rating.toString().toFloat()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FBRef.EvaluBoardRef.child(key).addValueEventListener(postListener)



    }
}