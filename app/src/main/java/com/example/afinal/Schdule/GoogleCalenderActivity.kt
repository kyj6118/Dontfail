package com.example.afinal.Schdule

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bokchi.mysolelife.utils.FBRef
import com.example.afinal.R
import com.example.afinal.VO.*
import com.example.afinal.board.FreeBoardInsideActivity
import com.example.afinal.databinding.ActivityGoogleCalenderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class GoogleCalenderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGoogleCalenderBinding
    var firestore: FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth

    lateinit var fname: String
    var str: String ?= null
    lateinit var calendarView: CalendarView
    lateinit var diaryTextView: TextView

    private lateinit var calendarRVAdapter: MyDiaryListAdapter
    private val calendarList = mutableListOf<calenderVO>()
    private val calendarKeyList = mutableListOf<String>()
    var pro:ProgressDialog? = null
    var pra:ProgressDialog? = null
    var prb:ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_calender)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_google_calender)


            calendarView=findViewById(R.id.calendarView)
            diaryTextView=findViewById(R.id.diaryTextView)





            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                diaryTextView.visibility = View.VISIBLE
                diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
                str = diaryTextView.text.toString()

                getdata(str!!)




            }


        binding.writeBtn.setOnClickListener {
            if(str == null){
                Toast.makeText(this,"Please selet date", Toast.LENGTH_LONG).show()
            }

            else showDialog(str!!)
        }






        binding.calendarListView.setOnItemClickListener { parent, view, position, id ->

        /*    val intent = Intent(this, CalenderInsideActivity::class.java)
            intent.putExtra("key", calendarKeyList[position])
            startActivity(intent)*/

            showInside(calendarKeyList[position].toString())





        }




    }

    private fun showInside(toString: String) {

        val db = Firebase.firestore

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.calendar_inside_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)




        val alertDialog = mBuilder.show()

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val dataModel = dataSnapshot.getValue(calenderVO::class.java)

                alertDialog.findViewById<TextView>(R.id.date)?.text = dataModel?.date
                alertDialog.findViewById<TextView>(R.id.content)?.text = dataModel?.content


            }



            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.calRef.child(toString).addValueEventListener(postListener)



        alertDialog.findViewById<Button>(R.id.modifyBtn)?.setOnClickListener {


            ModifyDialog(toString)



        }


        alertDialog.findViewById<Button>(R.id.deleteBtn)?.setOnClickListener {



            FBRef.calRef.child(toString).removeValue()



            pra = ProgressDialog.show(this, "delete" , "\nwait")


            // 핸들러를 통해서 종료 작업을 한다.
            var handler = Handler()
            var thread = Runnable { pra?.cancel() }
            handler.postDelayed(thread,2000) // 딜레이는 5초
            alertDialog.dismiss()




        }





    }

    private fun ModifyDialog(toString: String) {
        val db = Firebase.firestore

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.calender_modift, null)

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)


        val alertDialog = mBuilder.show()


        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val dataModel = dataSnapshot.getValue(calenderVO::class.java)


                alertDialog.findViewById<TextView>(R.id.date)?.text = dataModel?.date
                alertDialog.findViewById<EditText>(R.id.content)?.setText(dataModel?.content)


            }



            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.calRef.child(toString).addValueEventListener(postListener)




        alertDialog.findViewById<Button>(R.id.modifyBtn)?.setOnClickListener {





            val date =  alertDialog.findViewById<TextView>(R.id.date)?.text.toString()
            val content = alertDialog.findViewById<EditText>(R.id.content)?.text.toString()
            val email = FBAuth.getemail()
            val confirm = FBAuth.getemail()+date


            // 파이어베이스 store에 이미지를 저장하고 싶습니다
            // 만약에 내가 게시글을 클릭했을 때, 게시글에 대한 정보를 받아와야 하는데
            // 이미지 이름에 대한 정보를 모르기 때문에
            // 이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 쉽게 해놓음.



            FBRef.calRef
                .child(toString)
                .setValue(calenderVO(content,date,email,confirm))



            // 핸들러를 통해서 종료 작업을 한다.
            prb = ProgressDialog.show(this, "modify" , "\nwait")

            // 핸들러를 통해서 종료 작업을 한다.
            var handler = Handler()
            var thread = Runnable { prb?.cancel() }
            handler.postDelayed(thread,2000) // 딜레이는 5초
            alertDialog.dismiss()

        }


    }


    private fun getdata(str: String) {
        calendarRVAdapter = MyDiaryListAdapter(calendarList)
        binding.calendarListView.adapter = calendarRVAdapter
        val db = Firebase.firestore

        calendarList.clear()


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                calendarList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(ContentValues.TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(calenderVO::class.java)
                    calendarList.add(item!!)
                    calendarKeyList.add(dataModel.key.toString())

                }


                calendarKeyList.reverse()
                calendarList.reverse()
                calendarRVAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.calRef.orderByChild("confirm").equalTo(FBAuth.getemail()+str.toString()).addValueEventListener(postListener)
    }

    private fun showDialog(str: String) {

        val db = Firebase.firestore

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.calender_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val alertDialog = mBuilder.show()

        alertDialog.findViewById<TextView>(R.id.date)?.text = str

        alertDialog.findViewById<Button>(R.id.writeBtn)?.setOnClickListener{

            val date =  alertDialog.findViewById<TextView>(R.id.date)?.text.toString()
            val content = alertDialog.findViewById<EditText>(R.id.content)?.text.toString()
            val email = FBAuth.getemail()
            val confirm = FBAuth.getemail()+date


            // 파이어베이스 store에 이미지를 저장하고 싶습니다
            // 만약에 내가 게시글을 클릭했을 때, 게시글에 대한 정보를 받아와야 하는데
            // 이미지 이름에 대한 정보를 모르기 때문에
            // 이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 쉽게 해놓음.

            val key = FBRef.EvaluBoardRef.push().key.toString()


            FBRef.calRef
                .child(key)
                .setValue(calenderVO(content,date,email,confirm))



            pro = ProgressDialog.show(this, "save" , "\nwait")

            // 핸들러를 통해서 종료 작업을 한다.
            var handler = Handler()
            var thread = Runnable { pro?.cancel() }
            handler.postDelayed(thread,2000) // 딜레이는 5초
            alertDialog.dismiss()

        }





        }



}




