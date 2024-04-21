package com.example.myapplication.ui.contact

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentContactUsBinding

class ContactUsFragment : Fragment() {

    private var _binding: FragmentContactUsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(ContactUsViewModel::class.java)

        _binding = FragmentContactUsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //        val textView: TextView = binding.menuTitle
//        slideshowViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}