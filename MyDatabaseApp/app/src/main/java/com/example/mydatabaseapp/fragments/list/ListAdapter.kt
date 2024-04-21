package com.example.mydatabaseapp.fragments.list


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mydatabaseapp.R
import com.example.mydatabaseapp.model.User

class ListAdapter(private var userList: List<User>): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return MyViewHolder(view)
        //MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = userList[position]
        holder.itemView.findViewById<TextView>(R.id.textViewID).text = currentItem.id.toString()
        holder.itemView.findViewById<TextView>(R.id.textViewFName).text = currentItem.firstName
        holder.itemView.findViewById<TextView>(R.id.textViewLName).text = currentItem.lastName
        holder.itemView.findViewById<TextView>(R.id.textViewAge).text = currentItem.age.toString()
        var row = holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout)
        //holder.itemView.findViewById<TextView>(R.layout.custom_row)
        row.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}