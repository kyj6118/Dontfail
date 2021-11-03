package com.example.afinal

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.afinal.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding :ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)
        auth = Firebase.auth


        binding.Loginbtn.setOnClickListener{

            val email= binding.EmailArea.text.toString()
            val password = binding.PasswordArea.text.toString()





            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this,MainPageActivity::class.java)
                        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)


                        Toast.makeText(this,"Success Login",Toast.LENGTH_LONG).show()
                        // Sign in success, update UI with the signed-in user's informationxt

                    } else {
                        Toast.makeText(this,"Fail Login",Toast.LENGTH_LONG).show()
                        // If sign in fails, display a message to the user.

                    }
                }




        }



    }
}