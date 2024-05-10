package com.example.myapplication.ui.account

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.databinding.FragmentOrderHistoryBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import com.google.firebase.database.database

class OrderHistoryFragment : Fragment() {

    private lateinit var userPhone: String
    private lateinit var databaseRead: DatabaseReference
    val orderHisArgs: OrderHistoryFragmentArgs by navArgs<OrderHistoryFragmentArgs>()
    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var arrayList: ArrayList<OrderHistoryModel>
    lateinit var thisAdapter: AccountAdapter

    private var _binding: FragmentOrderHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountViewModel =
            ViewModelProvider(this).get(OrderHistoryViewModel::class.java)
        _binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userPhone = orderHisArgs.userPhone

        //setup what this looks like
        recyclerView = binding.recyclerView
        linearLayoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        //all about data
        arrayList = setupData()
        thisAdapter = AccountAdapter(requireContext().applicationContext, arrayList)
        recyclerView.adapter = thisAdapter


//        root.removeView()
        return root
    }

    private fun setupData(): ArrayList<OrderHistoryModel>{
        val items: ArrayList<OrderHistoryModel> = ArrayList()
        //val date = Firebase.database.reference.child("/users").child(userPhone).child("/orders").get()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRead = firebaseDatabase.getReference("/users")
        val getUser = databaseRead.child(userPhone).child("/orders").get()

        getUser.addOnCompleteListener{
            val document = getUser.result.value


            val result = getUser.result
            if (result.exists()){
                result.children
                binding.textView.text = document.toString()
                    val date = getUser.result.key
                    val size = getUser.result.child(date.toString()).child("size").value
                    val coffee = getUser.result.child(date.toString()).child("coffee").value
                    val cream = getUser.result.child(date.toString()).child("cream").value
                    val flavor = getUser.result.child(date.toString()).child("flavor").value
                for (item in getUser.result.children) {
                    items.add(OrderHistoryModel(date.toString(), size.toString(), coffee.toString(), cream.toString(), flavor.toString()))
                }

                }
            else {
                Toast.makeText(
                    context,
                    "User not Found. Please Create an Account and Save an Order.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        return items
    }

}