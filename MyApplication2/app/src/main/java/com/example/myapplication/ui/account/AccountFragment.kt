package com.example.myapplication.ui.account

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import org.w3c.dom.Text

class AccountFragment : Fragment() {

    //READING DATA IN
    private lateinit var newUserEmail: EditText
    private lateinit var newUserPass: EditText
    private lateinit var newUserName: EditText
    private lateinit var newUserPhone: EditText
    private lateinit var newUserAddress: EditText
    private lateinit var newUserCardNum: EditText
    private lateinit var newUserCardExp: EditText
    private lateinit var newUserCardCVV: EditText
    private lateinit var updateBtn: Button
    lateinit var userData: ArrayList<AccountModel>

    // DATABASES
    private lateinit var databaseWrite: DatabaseReference
    private lateinit var databaseRead: DatabaseReference
    private lateinit var userEmailData: String
    private lateinit var userPassData: String
    private lateinit var userNameData: String
    private lateinit var userPhoneData: String
    private lateinit var userAddressData: String
    private lateinit var userCardNumData: String
    private lateinit var userCardExpData: String
    private lateinit var userCardCVVData: String

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

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // RECEIVE LOGIN USER DATA
        val args: AccountFragmentArgs by navArgs<AccountFragmentArgs>()

        newUserEmail = binding.editTextEmail
        newUserPass = binding.editTextPassword
        newUserName = binding.editTextName
        newUserPhone = binding.editTextPhone
        newUserAddress = binding.editTextAddress
        newUserCardNum = binding.editTextCardNum
        newUserCardExp = binding.editTextExpiration
        newUserCardCVV = binding.editTextCVV

        var emailArg = args.email
        var passArg = args.password
        var nameArg = args.name
        var phoneArg = args.phone
        var addressArg = args.address
        var cardNumArg = args.cardNum
        var cardExpArg = args.cardExp
        var cardCVVArg = args.cardCVV

        //newUserEmail.text = emailArg
        //newUserPhone.text = phoneArg



        val orderHisBtn = binding.accountOrdersBtn
        orderHisBtn.setOnClickListener {
            findNavController().navigate(R.id.action_nav_account_to_orderHistoryFragment)
        }

        // READ DATA
        databaseRead = Firebase.database.getReference("/users")
        databaseRead.addValueEventListener(changeListener)
        userData = ArrayList()



        updateBtn = binding.updateBtn
        updateBtn.setOnClickListener {
            val newUserData = AccountModel("", "", "", "", "", "", "", "")
            newUserData.email = newUserEmail.text.toString()
            newUserData.password = newUserPass.text.toString()
            newUserData.name = newUserName.text.toString()
            newUserData.phone = newUserPhone.toString()
            newUserData.address = newUserAddress.text.toString()
            newUserData.cardNum = newUserCardNum.text.toString()
            newUserData.cardExp = newUserCardExp.text.toString()
            newUserData.cardCVV = newUserCardCVV.text.toString()

            // WRITE TO DATABASE
            databaseWrite.child("users").child(newUserData.phone.toString()).setValue(newUserData)
            Toast.makeText(context, "New Entry successfully added!", Toast.LENGTH_SHORT).show()
        }

        databaseWrite = Firebase.database.reference




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}