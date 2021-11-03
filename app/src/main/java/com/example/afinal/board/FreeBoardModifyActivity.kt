package com.example.afinal.board

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.bumptech.glide.Glide
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.board
import com.example.afinal.databinding.ActivityFreeBoardModifyBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class FreeBoardModifyActivity : AppCompatActivity() {

    private lateinit var key:String
    private lateinit var writeUid : String
    private lateinit var binding: ActivityFreeBoardModifyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_free_board_modify)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_board_modify)

        key = intent.getStringExtra("key").toString()
        ModifyBoardData(key)
        ModifyImageDate(key)



        binding.ModifyBtn.setOnClickListener {
            EditBoardData(key)

        }

    }

    private fun EditBoardData(key: String) {
        val db = Firebase.firestore
        val docRef = db.collection("board").document(key)


        val title = binding.titleArea.text.toString()
        val content = binding.contentArea.text.toString()
        val uid = FBAuth.getUid()
        val time = FBAuth.getTime()
        val email = FBAuth.getemail()

        Log.d(TAG, title)
        Log.d(TAG, content)

       /* FBRef.boardRef
            .child(key)
            .setValue(
                board(binding.titleArea.text.toString(),
                    binding.contentArea.text.toString(),
                    FBAuth.getTime(),email,uid)
            )

*/
        val board = hashMapOf(
            "title" to title,
            "contents" to content,
            "id" to uid,
            "email" to email,
            "time" to time

        )

        db.collection("board")
            .document(key)
            .set(board)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

        Toast.makeText(this,"Sucess modify board", Toast.LENGTH_LONG).show()
        finish()

        Toast.makeText(this, "게시글 수정 완료", Toast.LENGTH_LONG).show()


    }

    private fun ModifyImageDate(key: String) {
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)

            } else {

            }
        })
    }

    private fun ModifyBoardData(key: String) {
        val db = Firebase.firestore
        val docRef = db.collection("board").document(key)



        docRef
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")



                    binding.titleArea.setText(document.get("title").toString())
                    binding.contentArea.setText(document.get("contents").toString())
                    writeUid = document.get("id").toString()



                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }
}