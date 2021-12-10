
package com.example.afinal.board

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.comment.CommentLVAdapter
import com.bokchi.mysolelife.utils.FBRef
import com.bumptech.glide.Glide
import com.example.afinal.R
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.board
import com.example.afinal.VO.commentVO
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityFreeBoardInsideBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception


class FreeBoardInsideActivity : AppCompatActivity() {

    private val TAG = FreeBoardInsideActivity::class.java.simpleName

    private lateinit var databaseRef : DatabaseReference

    private lateinit var binding: ActivityFreeBoardInsideBinding

    private lateinit var key : String

    var firestore : FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth



    private val commentDataList = mutableListOf<commentVO>()

    private lateinit var commentAdapter : CommentLVAdapter



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_free_board_inside)


      // 첫번째 방법


        val db = FirebaseFirestore.getInstance()


        // 두번째 방법
        key = intent.getStringExtra("key").toString()
        boardData(key.toString())
        getImageData(key.toString())
        getImageData2(key.toString())
        getImageData3(key.toString())
        getImageData4(key.toString())
        getCommentData(key.toString())

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.CommentListView.adapter = commentAdapter


        binding.commentBtn.setOnClickListener{
            insertComment(key)
        }



        binding.boardSettingIcon.setOnClickListener {
            showDialog()


        }


            }


    fun getCommentData(key: String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentDataList.clear()

                for (dataModel in dataSnapshot.children) {

                    val item = dataModel.getValue(commentVO::class.java)
                    commentDataList.add(item!!)
                }

                commentAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)



    }

        fun insertComment(key: String) {
            // comment
            //   - BoardKey
            //        - CommentKey
            //            - CommentData
            //            - CommentData
            //            - CommentData
            FBRef.commentRef
                .child(key)
                .push()
                .setValue(
                    commentVO(
                        binding.commentArea.text.toString(),
                        FBAuth.getemail(),
                        FBAuth.getTime()
                    )
                )

            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            binding.commentArea.setText("")



        }



    //게시글 파이어스토어에서 받아오기
    private fun boardData(key: String) {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    val dataModel = dataSnapshot.getValue(board::class.java)

                    binding.titleArea.text = dataModel!!.title
                    binding.textArea.text = dataModel!!.contents
                    binding.timeArea.text = dataModel!!.time
                    binding.nameArea.text = dataModel!!.email


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
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }


    private fun showDialog(){

        val db = Firebase.firestore

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder =AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Modify/delete")



        val alertDialog = mBuilder.show()
        //수정 버튼 누르면

        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener{
            Toast.makeText(this,"Modify button",Toast.LENGTH_LONG).show()
            val intent = Intent(this,BoardModifyActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
        }


        //삭제버튼 누르
        alertDialog.findViewById<Button>(R.id.deletBtn)?.setOnClickListener{

            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "Sucess delete", Toast.LENGTH_LONG).show()
            finish()


            db.collection("board").document(key)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
            Toast.makeText(this,"Success remove",Toast.LENGTH_LONG).show()
            finish()
        }


    }

    private fun getImageData(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + "1")

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
        val storageReference = Firebase.storage.reference.child(key + "2")

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
        val storageReference = Firebase.storage.reference.child(key + "3")

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
        val storageReference = Firebase.storage.reference.child(key + "4")

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





