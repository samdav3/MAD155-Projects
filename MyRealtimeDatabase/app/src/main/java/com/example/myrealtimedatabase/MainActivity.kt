package com.example.myrealtimedatabase

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var thisAdapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayout: LinearLayoutManager
    lateinit var usersM: ArrayList<MyModel>
    private lateinit var addBtn: Button

    //READING DATA IN
    private lateinit var newUserID: EditText
    private lateinit var newUserName: EditText
    private lateinit var newUserEmail: EditText

    // DATABASES
    private lateinit var databaseWrite: DatabaseReference
    private lateinit var databaseRead: DatabaseReference
    private lateinit var userIDData: String
    private lateinit var userNameData: String
    private lateinit var userEmailData: String

    val changeListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChildren()) {
                    var count = snapshot.childrenCount
                    usersM.clear()
                    for (child in snapshot.children) {
                        val holdData = child.getValue<MyModel>()
                        usersM.add(holdData!!)
                        Log.i("child", "child.key")
                        Log.i("value", child.value.toString())
                    }
                    for (child in snapshot.children) {
                        val getData = child.getValue<MyModel>()
                        userIDData = getData?.userID.toString()
                        userNameData = getData?.userName.toString()
                        userEmailData = getData?.useremail.toString()

                        Log.i("child", "child.key")
                        Log.i("value", child.value.toString())
                        val userInfo = MyModel(userIDData, userNameData, userEmailData)
                        //usersM.add(userInfo)
                    }
                }
            thisAdapter.notifyDataSetChanged()
            }


        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // READ DATA
        databaseRead = Firebase.database.getReference("/users")
        databaseRead.addValueEventListener(changeListener)


        usersM = ArrayList()

        newUserID = findViewById(R.id.editTextID)
        newUserName = findViewById(R.id.editTextName)
        newUserEmail = findViewById(R.id.editTextEmail)
        addBtn = findViewById(R.id.addBtn)

        addBtn.setOnClickListener {

            val newUserInfo = MyModel("","","")
            newUserInfo.userID = newUserID.text.toString()
            newUserInfo.userName = newUserName.text.toString()
            newUserInfo.useremail = newUserEmail.text.toString()
            usersM.add(newUserInfo)

            thisAdapter.notifyDataSetChanged()
            databaseWrite.child("users").child(newUserInfo.userID.toString()).setValue(newUserInfo)
            newUserID.text = null
            newUserName.text = null
            newUserEmail.text = null
            Toast.makeText(applicationContext, "New Entry successfully added!", Toast.LENGTH_SHORT).show()
        }



        //WRITE TO DATABASE
        databaseWrite = Firebase.database.reference

        //setup what this looks like RECYCLER VIEW
        recyclerView = findViewById(R.id.recyclerView2)
        linearLayout = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayout

        //CALL RECYCLER VIEW
        //usersM = setupData()
        thisAdapter = MyAdapter(applicationContext, usersM)
        recyclerView.adapter = thisAdapter

    }

//    private fun setupData(): ArrayList<MyModel> {
//        return usersM
//    }



    override fun onDestroy() {
        super.onDestroy()
        databaseRead.removeEventListener(changeListener)
    }

}