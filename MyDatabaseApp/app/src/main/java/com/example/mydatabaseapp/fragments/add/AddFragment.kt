package com.example.mydatabaseapp.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mydatabaseapp.R
import com.example.mydatabaseapp.model.User
import com.example.mydatabaseapp.viewmodel.UserViewModel


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    //private  lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        view.findViewById<Button>(R.id.addBtn).setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val firstName = view?.findViewById<EditText>(R.id.addFirstName_et)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.addLastName_et)?.text.toString()
        val age = view?.findViewById<EditText>(R.id.addAge_et)?.text

        if (inputCheck(firstName, lastName, age as Editable)){
            //create user object
            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
            //Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_LONG).show()
            //Navigate Back
            this.findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

}