package com.example.myapplication.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(MenuViewModel::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val beansBtn = binding.beansBtn
        val cyoBtn = binding.cyoBtn
        val premBtn = binding.preMadeBtn
        beansBtn.setOnClickListener {
            findNavController().navigate(R.id.action_nav_menu_to_beansFragment)
        }
        cyoBtn.setOnClickListener {
            findNavController().navigate(R.id.action_nav_menu_to_CYOFragment)
        }
        premBtn.setOnClickListener {
            findNavController().navigate(R.id.action_nav_menu_to_preMadeFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}