package com.example.realtimedatabase

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedatabase.adapter.MyAdapter
import com.example.realtimedatabase.model.MyModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {

    lateinit var thisAdapter: MyAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var addBtn: Button

    //READING DATA IN
    private lateinit var userID: TextView
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    //private lateinit var list: RecyclerView
    private lateinit var newUserID: EditText
    private lateinit var newUserName: EditText
    private lateinit var newUserEmail: EditText

    // DATABASES
    private lateinit var databaseWrite: DatabaseReference
    private lateinit var databaseRead: DatabaseReference
    private var users = ArrayList<Person>()

    val changeListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()){
                var count = snapshot.childrenCount
                users.clear()
                for (child in snapshot.children){
                    val holdData = child.getValue(Person::class.java)
                    users.add(holdData!!)

                    Log.i("child", "child.key")
                    Log.i("value", child.getValue().toString())
                }
                for (child in snapshot.children){
                    val getData = child.getValue(Person::class.java)
                    
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // WRITE DATA
        databaseWrite = Firebase.database.reference

//        databaseWrite.child("person").child("user").setValue("Sam")
//        databaseWrite.child("person").child("address").setValue("1 Hickory Rd")

        // FIND NEW ENTRY DATA
        newUserID = findViewById(R.id.editTextID)
        newUserName = findViewById(R.id.editTextName)
        newUserEmail = findViewById(R.id.editTextEmail)
        var newUserInfoString = Person(newUserID.toString(), newUserName.toString(), newUserEmail.toString())
        addBtn = findViewById(R.id.addBtn)

        addBtn.setOnClickListener {
            databaseWrite.child("users").child(newUserInfoString.toString()).setValue(newUserInfoString)
        }


        var user1 = Person("001", "Sam Davenport",
            "sldavenport6@gmail.com")
        var user2 = Person("002", "Katie Callahan",
            "katiecal1118@yahoo.com")
        var user3 = Person("003", "Kelsey Davenport",
            "klsydaven@gmail.com")

        databaseWrite.child("users").child(user1.userID.toString()).setValue(user1)
        databaseWrite.child("users").child(user2.userID.toString()).setValue(user2)
        databaseWrite.child("users").child(user3.userID.toString()).setValue(user3)


        // READ DATA
        databaseRead = Firebase.database.getReference("/users")
        databaseRead.addValueEventListener(changeListener)

        //READ DATA IN
        userID = findViewById(R.id.editTextID)
        userName = findViewById(R.id.editTextName)
        userEmail = findViewById(R.id.editTextEmail)


        databaseRead = Firebase.database.getReference("/users")
        databaseRead.addValueEventListener(changeListener)

        fun setupData(): ArrayList<Person> {
            var items: ArrayList<Person> = ArrayList()

            var user1 = Person("001", "Sam Davenport",
                "sldavenport6@gmail.com")
            var user2 = Person("002", "Katie Callahan",
                "katiecal1118@yahoo.com")
            var user3 = Person("003", "Kelsey Davenport",
                "klsydaven@gmail.com")

            items.add(user1)
            items.add(user2)
            items.add(user3)

            items.add(newUserInfoString)

            return items
        }

        setupData()

    }

    override fun onDestroy() {
        super.onDestroy()
        databaseRead.removeEventListener(changeListener)
    }


}