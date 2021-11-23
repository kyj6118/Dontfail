package com.example.afinal

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.ReviewVO
import com.example.afinal.VO.evaluate
import com.example.afinal.databinding.ActivityReviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ReviewActivity : AppCompatActivity() {


    var firestore : FirebaseFirestore? = null
    private var isImageUpload1 = false
    private var isImageUpload2 = false
    private var isImageUpload3 = false
    private var isImageUpload4 = false
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityReviewBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)



        binding = DataBindingUtil.setContentView(this, R.layout.activity_review)



        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val db = Firebase.firestore


       val place =   intent.getStringExtra("name").toString()
       val address = intent.getStringExtra("Address").toString()

        binding.placeName.text = place
        binding.placeAddress.text = address

        binding.writeBtn.setOnClickListener {

            val place_name = binding.placeName.text.toString()
            val place_Address = binding.placeAddress.text.toString()
            val content = binding.contentArea.text.toString()
            val rating = binding.placeRating.rating.toFloat()
            val email = FBAuth.getemail()


            // 파이어베이스 store에 이미지를 저장하고 싶습니다
            // 만약에 내가 게시글을 클릭했을 때, 게시글에 대한 정보를 받아와야 하는데
            // 이미지 이름에 대한 정보를 모르기 때문에
            // 이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 쉽게 해놓음.

            val key = FBRef.ReviewRef.push().key.toString()





            FBRef.ReviewRef
                .child(key)
                .setValue(ReviewVO(place_name,place_Address,rating,content,email))



            if(isImageUpload1 == true ) {
                imageUpload1(key)
            }
            else if(isImageUpload2 == true ) {
                imageUpload1(key)
            }else if(isImageUpload3 == true ) {
                imageUpload1(key)
            }else if(isImageUpload4 == true ) {
                imageUpload1(key)
            }



            val review = hashMapOf(
                "place_name" to place_name,
                "content" to content,
                "place_address" to place_Address,
                "email" to email,
                "place_rating" to rating

            )
            // 파이어베이스 store에 이미지를 저장하고 싶습니다
            // 만약에 내가 게시글을 클릭했을 때, 게시글에 대한 정보를 받아와야 하는데
            // 이미지 이름에 대한 정보를 모르기 때문에
            // 이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 쉽게 해놓음.




            db.collection("review")
                .document(key)
                .set(review)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }


            Toast.makeText(this,"Sucess write board",Toast.LENGTH_LONG).show()
            finish()


        }

        binding.imageArea1.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload1 = true
        }


        binding.imageArea2.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 99)
            isImageUpload2 = true
        }

        binding.imageArea3.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 98)
            isImageUpload3 = true
        }


        binding.imageArea4.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 97)
            isImageUpload4 = true
        }



        }

    private fun imageUpload1(key : String){
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        firestore = FirebaseFirestore.getInstance()
        val mountainsRef =storageRef.child("review").child(key+"1")

        val imageView = binding.imageArea1
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }


    }


    private fun imageUpload2(key : String){
        // Get the data from an ImageView as bytes

        val storage = Firebase.storage
        val storageRef = storage.reference
        firestore = FirebaseFirestore.getInstance()
        val mountainsRef = storageRef.child("review").child(key+"2")

        val imageView2 = binding.imageArea2
        imageView2.isDrawingCacheEnabled = true
        imageView2.buildDrawingCache()
        val bitmap2 = (imageView2.drawable as BitmapDrawable).bitmap
        val baos2 = ByteArrayOutputStream()
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 99, baos2)
        val data = baos2.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }


    }

    private fun imageUpload3(key: String) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        firestore = FirebaseFirestore.getInstance()
        val mountainsRef = storageRef.child("review").child(key+"3")

        val imageView3 = binding.imageArea3
        imageView3.isDrawingCacheEnabled = true
        imageView3.buildDrawingCache()
        val bitmap2 = (imageView3.drawable as BitmapDrawable).bitmap
        val baos2 = ByteArrayOutputStream()
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 98, baos2)
        val data = baos2.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }


    private fun imageUpload4(key: String) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        firestore = FirebaseFirestore.getInstance()
        val mountainsRef = storageRef.child("review").child(key+"4")

        val imageView4 = binding.imageArea4
        imageView4.isDrawingCacheEnabled = true
        imageView4.buildDrawingCache()
        val bitmap2 = (imageView4.drawable as BitmapDrawable).bitmap
        val baos2 = ByteArrayOutputStream()
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 97, baos2)
        val data = baos2.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }












    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            binding.imageArea1.setImageURI(data?.data)
        }
        if(resultCode == RESULT_OK && requestCode == 99) {
            binding.imageArea2.setImageURI(data?.data)
        }
        if(resultCode == RESULT_OK && requestCode == 98) {
            binding.imageArea3.setImageURI(data?.data)
        }
        if(resultCode == RESULT_OK && requestCode == 97) {
            binding.imageArea4.setImageURI(data?.data)
        }



    }

}
