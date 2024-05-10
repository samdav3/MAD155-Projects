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

    private lateinit var userPhone: String

    //READING DATA IN
    private lateinit var newUserEmail: EditText
    private lateinit var newUserPass: EditText
    private lateinit var newUserName: EditText
    private lateinit var newUserPhone: EditText
    private lateinit var newUserAddress: EditText
    private lateinit var newUserCardNum: EditText
    private lateinit var newUserCardExp: EditText
    private lateinit var newUserCardCVV: EditText
    private lateinit var newUserCardName: EditText
    private lateinit var updateBtn: Button
    lateinit var userData: ArrayList<AccountModel>

    //PASS DATA
    val accountArgs: AccountFragmentArgs by navArgs<AccountFragmentArgs>()



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
    private lateinit var userCardNameData: String

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
                    userCardNameData = getData?.cardName.toString()
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
        userPhone = accountArgs.phone


        val orderHisBtn = binding.accountOrdersBtn
        orderHisBtn.setOnClickListener {
            userPhone = accountArgs.phone
            val passData = AccountFragmentDirections.actionNavAccountToOrderHistoryFragment(userPhone)
            findNavController().navigate(passData)
        }

        // READ DATA
        databaseRead = Firebase.database.getReference("/users")
        databaseRead.addValueEventListener(changeListener)
        userData = ArrayList()

        // RECEIVE LOGIN USER DATA


        newUserEmail = binding.editTextEmail
        newUserPass = binding.editTextPassword
        newUserName = binding.editTextName
        newUserPhone = binding.editTextPhone
        newUserAddress = binding.editTextAddress
        newUserCardNum = binding.editTextCardNum
        newUserCardExp = binding.editTextExpiration
        newUserCardCVV = binding.editTextCVV
        newUserCardName = binding.editTextCardName

        val emailArg = accountArgs.email
        val passArg = accountArgs.password
        val nameArg = accountArgs.name
        val phoneArg = accountArgs.phone
        val addressArg = accountArgs.address
        val cardNumArg = accountArgs.cardNum
        val cardExpArg = accountArgs.cardExp
        val cardCVVArg = accountArgs.cardCVV
        val cardNameArg = accountArgs.cardName


        (newUserEmail as TextView).text = emailArg
        (newUserPass as TextView).text = passArg
        (newUserPhone as TextView).text = phoneArg
        (newUserName as TextView).text = nameArg
        (newUserAddress as TextView).text = addressArg
        (newUserCardNum as TextView).text = cardNumArg
        (newUserCardExp as TextView).text = cardExpArg
        (newUserCardCVV as TextView).text = cardCVVArg
        (newUserCardName as TextView).text = cardNameArg


        databaseWrite = Firebase.database.reference
        val user = databaseRead.child("users").child(userPhone).child("orders").get()

        updateBtn = binding.updateBtn
        updateBtn.setOnClickListener {
//            if (user.result.exists()){
////                // UPDATE USER INFO BUT KEEP EXISTING ORDERS
//                print("User Exists with Orders Saved.")
//                val updateUserData = UpdateAccountModel("", "", "", "", "", "", "", "", "", "")
//                updateUserData.email = newUserEmail.text.toString()
//                updateUserData.password = newUserPass.text.toString()
//                updateUserData.name = newUserName.text.toString()
//                updateUserData.phone = newUserPhone.text.toString()
//                updateUserData.address = newUserAddress.text.toString()
//                updateUserData.cardNum = newUserCardNum.text.toString()
//                updateUserData.cardExp = newUserCardExp.text.toString()
//                updateUserData.cardCVV = newUserCardCVV.text.toString()
//                updateUserData.cardName = newUserCardName.text.toString()
//                updateUserData.orders = user.result.value.toString()
//
//                // WRITE TO DATABASE
//                databaseWrite.child("users").child(updateUserData.phone.toString()).setValue(updateUserData)
//                Toast.makeText(context, "User Info Updated", Toast.LENGTH_SHORT).show()
//
//            }else{
                val newUserData = AccountModel("", "", "", "", "", "", "", "", "")
                newUserData.email = newUserEmail.text.toString()
                newUserData.password = newUserPass.text.toString()
                newUserData.name = newUserName.text.toString()
                newUserData.phone = newUserPhone.text.toString()
                newUserData.address = newUserAddress.text.toString()
                newUserData.cardNum = newUserCardNum.text.toString()
                newUserData.cardExp = newUserCardExp.text.toString()
                newUserData.cardCVV = newUserCardCVV.text.toString()
                newUserData.cardName = newUserCardName.text.toString()

                // WRITE TO DATABASE
                databaseWrite.child("users").child(newUserData.phone.toString()).setValue(newUserData)
                Toast.makeText(context, "User Info Updated", Toast.LENGTH_SHORT).show()
           // }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}