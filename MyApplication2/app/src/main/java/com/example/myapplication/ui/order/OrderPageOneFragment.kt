package com.example.myapplication.ui.order

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentOrderPageOneBinding

class OrderPageOneFragment : Fragment() {

    private val orderArgs: OrderPageOneFragmentArgs by navArgs<OrderPageOneFragmentArgs>()


    //private lateinit var userPhone: String

    private lateinit var orderDate: String
    private lateinit var size: String
    private lateinit var coffee: String
    private lateinit var cream: String
    private lateinit var flavor: String

    private var _binding: FragmentOrderPageOneBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(OrderPageOneViewModel::class.java)

        _binding = FragmentOrderPageOneBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //userPhone = orderArgs.userPhone
        //println(userPhone)

        orderDate = ""
        size = ""
        coffee = ""
        cream = ""
        flavor = ""


        val submitBtn = binding.submitBtn
        submitBtn.setOnClickListener {

            val sizeID: Int = binding.chipGroup.checkedRadioButtonId
            val sizeRB: RadioButton = binding.chipGroup.findViewById(sizeID)
            size = sizeRB.text.toString()

            val coffeeID: Int = binding.linearLayoutCoffee.checkedRadioButtonId
            val coffeeRB: RadioButton = binding.linearLayoutCoffee.findViewById(coffeeID)
            coffee = coffeeRB.text.toString()

            val creamID: Int = binding.linearLayout.checkedRadioButtonId
            val creamRB: RadioButton = binding.linearLayout.findViewById(creamID)
            cream = creamRB.text.toString()

            val flavorID: Int = binding.linearLayoutFlavor.checkedRadioButtonId
            val flavorRB: RadioButton = binding.linearLayoutFlavor.findViewById(flavorID)
            flavor = flavorRB.text.toString()

            val passSize = size
            val passCoffee = coffee
            val passCream = cream
            val passFlavor = flavor
            orderDate = binding.editTextDate.text.toString()
            val passData = OrderPageOneFragmentDirections.actionOrderPageOneFragmentToOrderPageTwoFragment(
                                                                                        orderDate, passSize, passCoffee, passCream, passFlavor)
            findNavController().navigate(passData)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}