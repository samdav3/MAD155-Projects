package com.example.appsettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var pickClass: EditText
    lateinit var pickNum: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.edit1)
        pickClass = findViewById(R.id.edit2)
        pickNum = findViewById(R.id.edit3)

    }

    override fun onResume() {
        super.onResume()

        val SP = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)

        // look for the key value pairs
        val key1 = SP.getString("name", "")
        val key2 = SP.getString("pickClass", "")
        val key3 = SP.getInt("pickNum", 0)

        //assign them to the widgets
        name!!.setText(key1)
        pickClass!!.setText(key2)
        pickNum!!.setText(key3.toString())


    }

    override fun onPause() {
        super.onPause()

        val SP = getSharedPreferences("MySharedPreferences", MODE_PRIVATE)
        //opened in provate mode for writing
        val myEdit = SP.edit()

        //write to the file
        myEdit.putString("name", name!!.text.toString())
        myEdit.putString("pickClass", pickClass!!.text.toString())
        myEdit.putString("pickNum", pickNum!!.text.toString())

        // could use commit here instead
        myEdit.apply()

    }

}