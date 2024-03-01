package com.example.floatingactionbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private var listItems = ArrayList<String>()
    private var adapter: ArrayAdapter<String>? = null
    private lateinit var listView: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var undoOnClickListener: View.OnClickListener
    private lateinit var enterListValue: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lv1)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)

        listView.adapter = adapter
        fab = findViewById(R.id.fab1)
        fab.setOnClickListener {
            addListItem()
            Snackbar.make(it, "Added an Item", Snackbar.LENGTH_LONG)
                .setAction("UNDO", undoOnClickListener)
                .show()

        }
        undoOnClickListener = View.OnClickListener {
            listItems.removeAt(listItems.size - 1)
            adapter?.notifyDataSetChanged()
            Snackbar.make(it, "Item Removed", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

    }

    private fun addListItem() {
        listItems.add(enterListValue.text.toString())
        adapter?.notifyDataSetChanged()
    }


}