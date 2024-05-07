package com.example.myapplication.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.text.toSpannable
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderPageTwoBinding
import com.example.myapplication.databinding.FragmentSaveOrderLoginBinding
import com.example.myapplication.ui.account.OrderHistoryModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue


class SaveOrderLoginFragment : Fragment() {

    private lateinit var orderHis: ArrayList<OrderHistoryModel>
    private lateinit var databaseRead: DatabaseReference
    private lateinit var databaseWrite: DatabaseReference
    private lateinit var saveOrder: Button
    private lateinit var phoneNum: String
    private val saveArgs: SaveOrderLoginFragmentArgs by navArgs<SaveOrderLoginFragmentArgs>()

    private lateinit var date: String
    private lateinit var size: String
    private lateinit var coffee: String
    private lateinit var cream: String
    private lateinit var flavor: String
    private lateinit var phone: String
    private var _binding: FragmentSaveOrderLoginBinding? = null
    private val binding get() = _binding!!

    val changeListener: ValueEventListener = object : ValueEventListener {
        // GET AND HOLD DATA
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()) {
                for (child in snapshot.children) {
                    val holdData = child.getValue<OrderHistoryModel>()
                    orderHis.add(holdData!!)
                    Log.i("child", "child.key")
                    Log.i("value", child.value.toString())
                }
                for (child in snapshot.children) {
                    val getData = child.getValue<OrderHistoryModel>()
                    // GET USER DATA HERE
                    date = getData?.date.toString()
                    size = getData?.size.toString()
                    coffee = getData?.coffee.toString()
                    cream = getData?.cream.toString()
                    flavor = getData?.flavor.toString()
                    phone = getData?.flavor.toString()
                    Log.i("child", "child.key")
                    Log.i("value", child.value.toString())
                }
            }

        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSaveOrderLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        databaseWrite = Firebase.database.reference
        databaseRead = Firebase.database.getReference("/orders")
        databaseRead.addValueEventListener(changeListener)
        orderHis = ArrayList()

        date = saveArgs.date
        size = saveArgs.size
        coffee = saveArgs.coffee
        cream = saveArgs.cream
        flavor = saveArgs.flavor

        saveOrder = binding.loginBtn
        val phoneNumText = binding.editTextLoginPhone
        phoneNum = phoneNumText.text.toSpannable().toString()
        saveOrder.setOnClickListener {

            val getUser = Firebase.database.reference.child("/users").child(phoneNum).get()
            //val userOrderDoc = databaseRead.child(phoneNum).get()
            getUser.addOnCompleteListener{
                phoneNum = phoneNumText.text.toString()
                val result = getUser.result
                if (result.exists()){
                    //userOrderDoc.addOnCompleteListener {
                        val orderData = OrderHistoryModel("", "", "", "", "", "")
                        orderData.date = date
                        orderData.size = size
                        orderData.coffee = coffee
                        orderData.cream = cream
                        orderData.flavor = flavor
                        orderData.phone = phoneNum
                        databaseWrite.child("/orders").child(orderData.phone.toString()).setValue(orderData)
                        Toast.makeText(context, "Order saved to $phoneNum", Toast.LENGTH_LONG).show()
                    //}
                }
                else{
                    Toast.makeText(context, "User not Found. Please Create an Account before saving an Order.", Toast.LENGTH_LONG).show()
                }
            }
            findNavController().navigate(R.id.action_saveOrderLoginFragment_to_nav_home)

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}