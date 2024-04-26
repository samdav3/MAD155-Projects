package com.example.myapplication.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.account.AccountModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class LoginFragment : Fragment() {

    private lateinit var databaseRead: DatabaseReference
    private lateinit var userEmailData: String
    private lateinit var userPassData: String
    private lateinit var userNameData: String
    private lateinit var userPhoneData: String
    private lateinit var userAddressData: String
    private lateinit var userCardNumData: String
    private lateinit var userCardExpData: String
    private lateinit var userCardCVVData: String
    lateinit var userData: ArrayList<AccountModel>
    private lateinit var snapshot: DataSnapshot

    val changeListener: ValueEventListener = object : ValueEventListener {
        // GET AND HOLD DATA
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()) {
                for (child in snapshot.children) {
                    val holdData = child.getValue<AccountModel>()
                    userData.add(holdData!!)
                    Log.i("child", "child.key")
                    Log.i("value", child.value.toString())
                }
                for (child in snapshot.children) {
                    val getData = child.getValue<AccountModel>()
                    // GET USER DATA HERE
                    userEmailData = getData?.email.toString()
                    userPassData = getData?.password.toString()
                    userNameData = getData?.name.toString()
                    userPhoneData = getData?.phone.toString()
                    userAddressData = getData?.address.toString()
                    userCardNumData = getData?.cardNum.toString()
                    userCardExpData = getData?.cardExp.toString()
                    userCardCVVData = getData?.cardCVV.toString()
                    Log.i("child", "child.key")
                    Log.i("value", child.value.toString())
                }
            }

        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // GET REFERENCE
        userData = ArrayList()
        userPhoneData = binding.editTextLoginPhone.text.toString()
        println(userPhoneData)
        val firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRead = firebaseDatabase.getReference("/users").child(userPhoneData)
        databaseRead.addValueEventListener(changeListener)
        databaseRead.get()



        // NEW ACCOUNT BUTTON
        val addAccntBtn = binding.addAccountBtn
        addAccntBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_nav_account)
        }

        // LOGIN BUTTON
        val loginBtn = binding.loginBtn
        loginBtn.setOnClickListener {
            // CHECK IF DOCUMENT EXISTS
            if (databaseRead.get().isSuccessful){
                findNavController().navigate(R.id.action_loginFragment_to_nav_account)
            }else{
                Toast.makeText(context, "User not Found", Toast.LENGTH_LONG).show()
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}