package com.example.afinal

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.afinal.VO.FBAuth
import com.example.afinal.VO.user
import com.example.afinal.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class JoinActivity : AppCompatActivity() {




    private lateinit var binding : ActivityJoinBinding
    var firestore : FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)




        binding = DataBindingUtil.setContentView(this,R.layout.activity_join)
        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val db = Firebase.firestore



        binding.Joinbtn.setOnClickListener{

            var isGoToJoin = true

            val email = binding.EmailArea.text.toString()
            val password1 = binding.PasswordArea1.text.toString()
            val password2 = binding.PasswordArea2.text.toString()
            val name = binding.nameArea.text.toString()
            val uid= FBAuth.getUid()
            val spinner = findViewById<Spinner>(R.id.Uni)

            spinner.adapter=ArrayAdapter.createFromResource(this,R.array.university,android.R.layout.simple_dropdown_item_1line)


            if(email.isEmpty()){
                Toast.makeText(this,"Enter your Email",Toast.LENGTH_LONG).show()
                isGoToJoin = false

            }
            if(name.isEmpty()){
                Toast.makeText(this,"Enter your name",Toast.LENGTH_LONG).show()
                isGoToJoin = false

            }
            if(password1.isEmpty()){
                Toast.makeText(this,"Enter your Password",Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(password2.isEmpty()){
                Toast.makeText(this,"Enter your Password",Toast.LENGTH_LONG).show()
                isGoToJoin = false

            }
            if(!password1.equals(password2)){
                Toast.makeText(this,"not same password",Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(password1.length<6){
                Toast.makeText(this,"plese password with 6 digits or more",Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if(isGoToJoin){
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val user = hashMapOf(
                                "id" to email,
                                "name" to name,
                                "uni" to "no",
                                "password" to password1,
                                 "uid" to uid
                            )

                            db.collection("user").document(email)
                                .set(user)
                                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


                            Toast.makeText(this,"Success Check your email",Toast.LENGTH_LONG).show()

                            val intent = Intent(this,MainActivity::class.java)
                            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)

                        } else {
                            //Toast.makeText(this,"fail",Toast.LENGTH_LONG).show()

                            Toast.makeText(this,"fail",Toast.LENGTH_LONG).show()
                        }

                    }



            }

            }


        }




    }
