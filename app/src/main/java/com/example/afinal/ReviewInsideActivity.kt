package com.example.afinal

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.bumptech.glide.Glide
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.Review
import com.example.afinal.VO.ReviewVO
import com.example.afinal.VO.evaluate
import com.example.afinal.board.BoardModifyActivity
import com.example.afinal.board.FreeBoardInsideActivity
import com.example.afinal.databinding.ActivityFreeBoardInsideBinding
import com.example.afinal.databinding.ActivityReviewInsideBinding
import com.example.afinal.evaluate.EvaluateBoardModifyActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class ReviewInsideActivity : AppCompatActivity() {


    private lateinit var databaseRef: DatabaseReference

    private lateinit var binding: ActivityReviewInsideBinding

    private lateinit var key: String

    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_inside)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_inside)



        key = intent.getStringExtra("key").toString()


        getReviewData(key)
        getImageData(key.toString())
        getImageData2(key.toString())
        getImageData3(key.toString())
        getImageData4(key.toString())


        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }


    }


    private fun showDialog() {


        val db = Firebase.firestore

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Modify/Delete")

        val alertDialog = mBuilder.show()

        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
          val intent = Intent(this, ReviewModifyActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }
        alertDialog.findViewById<Button>(R.id.deletBtn)?.setOnClickListener {

            db.collection("review").document(key)
                .delete()
                .addOnSuccessListener {
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot successfully deleted!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(
                        ContentValues.TAG,
                        "Error deleting document",
                        e
                    )
                }

            FBRef.ReviewRef.child(key).removeValue()
            Toast.makeText(this, "delet Success", Toast.LENGTH_LONG).show()
            finish()

        }
    }

    private fun getReviewData(key: String) {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val rating = findViewById<RatingBar>(R.id.classRating)
                    val dataModel = dataSnapshot.getValue(ReviewVO::class.java)

                    binding.placeName.text = dataModel!!.place_name
                    binding.placeAddress.text = dataModel!!.place_address
                    binding.placeContent.text = dataModel!!.content
                    binding.placeRating.rating = dataModel!!.place_rating.toString().toFloat()


                    val myUid = FBAuth.getemail()
                    val writerUid = dataModel.email

                    Log.d(ContentValues.TAG, "getemail" +  FBAuth.getemail() )
                    Log.d(ContentValues.TAG, "writeUid" +  writerUid )


                    if (myUid.equals(writerUid)) {
                        Log.d(ContentValues.TAG, "내가 쓴 글")
                        binding.boardSettingIcon.isVisible = true
                    } else {
                        Log.d(ContentValues.TAG, "내가 쓴 글 아님")
                    }

                } catch (e: Exception) {
                    Log.d(ContentValues.TAG, "getemail" +  FBAuth.getemail() )
                    Log.d(ContentValues.TAG, "삭제완료")

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.ReviewRef.child(key).addValueEventListener(postListener)
    }

    private fun getImageData(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child("review").child(key + "1")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.getImageArea.isVisible = false

            }
        })
    }

    private fun getImageData2(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child("review").child(key + "2")


        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea1

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.getImageArea1.isVisible = false

            }
        })
    }

    private fun getImageData3(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child("review").child(key + "3")


        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea2

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.getImageArea2.isVisible = false

            }
        })
    }

    private fun getImageData4(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child("review").child(key + "4")


        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea3

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

                binding.getImageArea3.isVisible = false

            }
        })
    }



}

