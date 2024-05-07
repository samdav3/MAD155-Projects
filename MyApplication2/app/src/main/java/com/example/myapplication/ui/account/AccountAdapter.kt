package com.example.myapplication.ui.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class AccountAdapter (var context: Context, var arrayList: ArrayList<OrderHistoryModel>) :
    RecyclerView.Adapter<AccountAdapter.ItemHolder>(){

        class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var date = itemView.findViewById<TextView>(R.id.orderDate)
            var txt = itemView.findViewById<TextView>(R.id.orderInfo)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemholder = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view, parent, false)
        return ItemHolder(itemholder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var myCard: OrderHistoryModel = arrayList.get(position)

        holder.date.text = myCard.date
        holder.txt.text = "${myCard.size} ${myCard.coffee} ${myCard.cream} ${myCard.flavor}"

        holder.date.setOnClickListener {
            Toast.makeText(
                context,
                myCard.date,
                Toast.LENGTH_SHORT
            )
        }

        holder.txt.setOnClickListener {
            Toast.makeText(
                context,
                "${myCard.size} ${myCard.coffee} ${myCard.cream} ${myCard.flavor}",
                Toast.LENGTH_SHORT
            )
        }
    }

}
