package com.example.afinal.board

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.bumptech.glide.Glide
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.board
import com.example.afinal.databinding.ActivityBoardModifyBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardModifyActivity : AppCompatActivity() {


    private lateinit var key:String

    private lateinit var binding : ActivityBoardModifyBinding


    private lateinit var writerUid : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_modify)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_modify)



        key = intent.getStringExtra("key").toString()
        getBoardData(key)
       getImageData(key)
        getImageData2(key)
        getImageData3(key)
        getImageData4(key)

        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }



    }

    private fun getImageData4(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + "4")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea4

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.imageArea4.isVisible = false

            }
        })


    }

    private fun getImageData3(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + "3")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea3

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.imageArea3.isVisible = false

            }
        })


    }

    private fun getImageData2(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + "2")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea2

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.imageArea2.isVisible = false

            }
        })


    }

    private fun getImageData(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + "1")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea1

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.imageArea1.isVisible = false

            }
        })
    }

    private fun getBoardData(key: String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(board::class.java)
//                Log.d(TAG, dataModel.toString())
//                Log.d(TAG, dataModel!!.title)
//                Log.d(TAG, dataModel!!.time)

                binding.titleArea.setText(dataModel?.title)
                binding.contentArea.setText(dataModel?.contents)
                writerUid = dataModel!!.email!!


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }

    private fun editBoardData(key: String) {



        FBRef.boardRef
            .child(key)
            .setValue(
                board(binding.titleArea.text.toString(),
                    binding.contentArea.text.toString(),
                    FBAuth.getTime(),
                    writerUid,
                FBAuth.getUid())
            )

        Toast.makeText(this, "success modify", Toast.LENGTH_LONG).show()

        finish()


    }
}