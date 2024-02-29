package com.example.topappbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    //private lateinit var contact: MenuItem
    //private lateinit var gallery: MenuItem
    //private lateinit var profile: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.topToolBar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_bar_menu, menu)
        //contact = findViewById(R.id.contact)
        //gallery = findViewById(R.id.gallery)
        //profile = findViewById(R.id.profile)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
            R.id.contact -> {
               // contact.setOnMenuItemClickListener {
               //     val intent = Intent (this, ContactUs::class.java)
               //     startActivity(intent)
               //    true
               // }
                true
            }

            R.id.gallery -> {
               // gallery.setOnMenuItemClickListener {
               //     val intent = Intent (this, Gallery::class.java)
               //     startActivity(intent)
              //      true
               // }
                true
            }

            R.id.profile -> {
                //profile.setOnMenuItemClickListener {
                //    val intent = Intent (this, Profile::class.java)
                //    startActivity(intent)
                //    true
               // }
                true
            }

            else -> {
                // The user's action isn't recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
            }

    }

}

