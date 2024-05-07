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
import com.google.firebase.database.core.Context
import com.google.firebase.database.database

class OrderHistoryFragment : Fragment() {

    private lateinit var userPhone: String
    private lateinit var databaseRead: DatabaseReference
    val orderHisArgs: OrderHistoryFragmentArgs by navArgs<OrderHistoryFragmentArgs>()
    lateinit var recyclerView: RecyclerView
    lateinit var gridLayoutManager: GridLayoutManager
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
        gridLayoutManager = GridLayoutManager(context, 2,
            LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)

        //all about data
        arrayList = setupData()
        thisAdapter = AccountAdapter(requireContext().applicationContext, arrayList)
        recyclerView.adapter = thisAdapter



        return root
    }

    private fun setupData(): ArrayList<OrderHistoryModel>{
        var items: ArrayList<OrderHistoryModel> = ArrayList()

        val getUser = Firebase.database.reference.child("/orders").child(userPhone).get()
        val result = getUser.result
        getUser.addOnCompleteListener{
            if (result.exists()){

                    val date = getUser.result.child("date").value
                    val size = getUser.result.child("size").value
                    val coffee = getUser.result.child("coffee").value
                    val cream = getUser.result.child("cream").value
                    val flavor = getUser.result.child("flavor").value
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