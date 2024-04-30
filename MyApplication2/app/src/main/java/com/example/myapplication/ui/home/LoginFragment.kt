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
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgument
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.account.AccountFragment
import com.example.myapplication.ui.account.AccountModel
import com.example.myapplication.ui.account.AccountViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.database.values


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
    //private lateinit var snapshot: DataSnapshot
    private lateinit var accountViewModel: AccountViewModel

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
        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        // GET REFERENCE
        userData = ArrayList()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRead = firebaseDatabase.getReference("/users")
        databaseRead.addValueEventListener(changeListener)
        databaseRead.get()


        // NEW ACCOUNT BUTTON
        val addAccntBtn = binding.addAccountBtn
        addAccntBtn.setOnClickListener {
            userEmailData = ""
            userPassData = ""
            userNameData = ""
            userPhoneData = ""
            userAddressData = ""
            userCardNumData = ""
            userCardExpData = ""
            userCardCVVData = ""
            // PASS LOGIN USER DATA (which is none)
            val userPassData = LoginFragmentDirections.actionLoginFragmentToNavAccount(userEmailData,
                userPassData,
                userNameData,
                userPhoneData,
                userAddressData,
                userCardNumData,
                userCardExpData,
                userCardCVVData)
            // NAVIGATE TO ACCOUNT FRAGMENT WITH USER DATA (IF EXISTS)
            findNavController().navigate(userPassData)
        }

        val loginBtn = binding.loginBtn
        loginBtn.setOnClickListener {
            // CHECK IF DOCUMENT EXISTS
            userPhoneData = binding.editTextLoginPhone.text.toString()
            val getUser = databaseRead.child(userPhoneData).get()
            getUser.addOnCompleteListener {
                databaseRead.orderByKey()
                if (getUser.result.exists()) {
                    //userData.apply {
                        databaseRead = firebaseDatabase.getReference("/users").child(userPhoneData)
                        userEmailData = databaseRead.child("/email").toString()
                        userPassData = databaseRead.child("/password").toString()
                        userNameData = databaseRead.child("/name").toString()
//                        userPhoneData = userPhoneData
                        userAddressData = databaseRead.child("/address").toString()
                        userCardNumData = databaseRead.child("/cardNum").toString()
                        userCardExpData = databaseRead.child("/cardExp").toString()
                        userCardCVVData = databaseRead.child("/cardCVV").toString()
                    //}

                    // PASS LOGIN USER DATA
                    val userPassData = LoginFragmentDirections.actionLoginFragmentToNavAccount(userEmailData,
                                                                                                userPassData,
                                                                                                userNameData,
                                                                                                userPhoneData,
                                                                                                userAddressData,
                                                                                                userCardNumData,
                                                                                                userCardExpData,
                                                                                                userCardCVVData)
                    // NAVIGATE TO ACCOUNT FRAGMENT WITH USER DATA (IF EXISTS)
                    findNavController().navigate(userPassData)

                }else{
                    Toast.makeText(context, "User not Found", Toast.LENGTH_LONG).show()
                }
            }



        }

        return root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}