package com.example.myrealtimedatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ValueEventListener
import org.jetbrains.annotations.NonNls

class MyAdapter(var context: Context, var arrayList: ArrayList<MyModel>) :
    RecyclerView.Adapter<MyAdapter.ItemHolder>() {

    class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var ID = itemView.findViewById<TextView>(R.id.textViewID1)
            var name = itemView.findViewById<TextView>(R.id.textViewName1)
            var email = itemView.findViewById<TextView>(R.id.textViewEmail1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_layout, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val myCard: MyModel = arrayList.get(position)
        holder.ID.text = myCard.userID
        holder.name.text = myCard.userName
        holder.email.text = myCard.useremail
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }



}