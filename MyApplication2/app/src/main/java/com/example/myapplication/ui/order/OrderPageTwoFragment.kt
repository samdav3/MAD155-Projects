package com.example.myapplication.ui.order

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderPageOneBinding
import com.example.myapplication.databinding.FragmentOrderPageTwoBinding
import com.example.myapplication.ui.account.AccountModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import java.util.Date

class OrderPageTwoFragment : Fragment() {

    private val newOrderArgs: OrderPageTwoFragmentArgs by navArgs<OrderPageTwoFragmentArgs>()

    // ORDER DATA
    private lateinit var orderDate: String
    private lateinit var size: String
    private lateinit var coffee: String
    private lateinit var cream: String
    private lateinit var flavor: String


    private var _binding: FragmentOrderPageTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(OrderPageTwoViewModel::class.java)

        _binding = FragmentOrderPageTwoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        orderDate = newOrderArgs.orderDate
        size = newOrderArgs.size
        coffee = newOrderArgs.coffee
        cream = newOrderArgs.cream
        flavor = newOrderArgs.flavor

        binding.orderDetailsDateChoice.text = orderDate
        binding.orderDetailsSizeChoice.text = size
        binding.orderDetailsCoffeeChoice.text = coffee
        binding.orderDetailsCreamChoice.text = cream
        binding.orderDetailsFlavorChoice.text = flavor

        val saveOrderBtn = binding.saveOrderBtn
        saveOrderBtn.setOnClickListener {
            // ADD ORDER DETAILS TO DATABASE
            // PASS ORDER DETAILS TO SAVE ORDER LOGIN FRAG
            val passDate = orderDate
            val passSize = size
            val passCoffee = coffee
            val passCream = cream
            val passFlavor = flavor
            val passData = OrderPageTwoFragmentDirections.actionNavOrderPageTwoFragmentToSaveOrderLoginFragment(passDate, passSize, passCoffee, passCream, passFlavor)
            findNavController().navigate(passData)
            //findNavController().navigate(R.id.action_nav_orderPageTwoFragment_to_saveOrderLoginFragment)
        }

        val startOrderBtn = binding.startOrderBtn
        startOrderBtn.setOnClickListener {
            findNavController().navigate(R.id.action_orderPageTwoFragment_to_orderPageOneFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}