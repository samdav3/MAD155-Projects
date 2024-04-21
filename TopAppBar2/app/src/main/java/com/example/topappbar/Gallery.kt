package com.example.topappbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class Gallery : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        setSupportActionBar(findViewById<Toolbar>(R.id.topToolBar))
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_bar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.contact -> { item.setOnMenuItemClickListener {
            val intent = Intent (this, ContactUs::class.java)
            startActivity(intent)
            true
        }
            true
        }

        R.id.gallery -> {
            item.setOnMenuItemClickListener {
                val intent = Intent (this, Gallery::class.java)
                startActivity(intent)
                true
            }
            true
        }

        R.id.profile -> {
            item.setOnMenuItemClickListener {
                val intent = Intent (this, Profile::class.java)
                startActivity(intent)
                true
            }
            true
        }

        else -> {
            // The user's action isn't recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }

    }

}