package com.example.afinal

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.bumptech.glide.Glide
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.ReviewVO
import com.example.afinal.VO.board
import com.example.afinal.databinding.ActivityBoardModifyBinding
import com.example.afinal.databinding.ActivityReviewModifyBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ReviewModifyActivity : AppCompatActivity() {

    private lateinit var key: String

    private lateinit var binding: ActivityReviewModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_modify)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_modify)



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
        val storageReference = Firebase.storage.reference.child("review").child(key + "4")

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
        val storageReference = Firebase.storage.reference.child("review").child(key + "3")

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
        val storageReference = Firebase.storage.reference.child("review").child(key + "2")

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
        val storageReference = Firebase.storage.reference.child("review").child(key + "1")


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

                val dataModel = dataSnapshot.getValue(ReviewVO::class.java)
//

                binding.placeName.text = dataModel!!.place_name
                binding.contentArea.setText(dataModel?.content)
                binding.placeRating.rating = dataModel!!.place_rating.toString().toFloat()
                binding.placeAddress.text = dataModel!!.place_address

                val myUid = FBAuth.getemail()
                val writerUid = dataModel.email


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FBRef.ReviewRef.child(key).addValueEventListener(postListener)

    }


    private fun editBoardData(key: String) {


        val place_name = binding.placeName.text.toString()
        val place_Address = binding.placeAddress.text.toString()
        val content = binding.contentArea.text.toString()
        val rating = binding.placeRating.rating.toFloat()
        val email = FBAuth.getemail()


        // 파이어베이스 store에 이미지를 저장하고 싶습니다
        // 만약에 내가 게시글을 클릭했을 때, 게시글에 대한 정보를 받아와야 하는데
        // 이미지 이름에 대한 정보를 모르기 때문에
        // 이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 쉽게 해놓음.




        FBRef.ReviewRef
            .child(key)
            .setValue(ReviewVO(place_name,place_Address,content,rating,email))

        Toast.makeText(this, "success modify", Toast.LENGTH_LONG).show()

        finish()

    }
}