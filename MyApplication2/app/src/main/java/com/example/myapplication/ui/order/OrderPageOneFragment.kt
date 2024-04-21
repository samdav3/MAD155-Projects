package com.example.myapplication.ui.order

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderPageOneBinding

class OrderPageOneFragment : Fragment() {

    private var _binding: FragmentOrderPageOneBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fab = R.id.fab
        val viewModel = ViewModelProvider(this).get(OrderPageOneViewModel::class.java)

        _binding = FragmentOrderPageOneBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.menuTitle
//        slideshowViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val submitBtn = binding.submitBtn
        submitBtn.setOnClickListener {
            findNavController().navigate(R.id.action_orderPageOneFragment_to_orderPageTwoFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}