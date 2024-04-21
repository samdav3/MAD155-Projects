package com.example.myapplication.ui.order

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderPageOneBinding
import com.example.myapplication.databinding.FragmentOrderPageTwoBinding

class OrderPageTwoFragment : Fragment() {

    private var _binding: FragmentOrderPageTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(OrderPageTwoViewModel::class.java)

        _binding = FragmentOrderPageTwoBinding.inflate(inflater, container, false)
        val root: View = binding.root

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