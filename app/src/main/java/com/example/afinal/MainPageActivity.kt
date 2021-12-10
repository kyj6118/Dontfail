package com.example.afinal

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.afinal.board.BoardModifyActivity
import com.example.afinal.databinding.ActivityFreeBoardInsideBinding
import com.example.afinal.databinding.ActivityMainPageBinding
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainPageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        auth = Firebase.auth

        var button = findViewById<ImageView>(R.id.boardSettingIcon)


        button.setOnClickListener {
            showDialog()


        }


        

        }

    private fun showDialog() {


        val db = Firebase.firestore

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Logout")



        val alertDialog = mBuilder.show()
        //수정 버튼 누르면

        alertDialog.findViewById<Button>(R.id.logoutBtn)?.setOnClickListener{
            Firebase.auth.signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
