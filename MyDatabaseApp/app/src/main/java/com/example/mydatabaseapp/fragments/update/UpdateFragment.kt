package com.example.mydatabaseapp.fragments.update

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mydatabaseapp.R
import com.example.mydatabaseapp.model.User
import com.example.mydatabaseapp.viewmodel.UserViewModel


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var currentUser: User
    private lateinit var mUserViewModel: UserViewModel
    //private  lateinit var navController: NavController

    @Suppress("DEPRECATION")
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_update, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        currentUser = args.currentUser

        view.findViewById<EditText>(R.id.updateFirstName_et).setText(args.currentUser.firstName)
        view.findViewById<EditText>(R.id.updateLastName_et).setText(args.currentUser.lastName)
        view.findViewById<EditText>(R.id.updateAge_et).setText(args.currentUser.age.toString())

        view.findViewById<Button>(R.id.updateBtn).setOnClickListener{
            updateItem()
        }
        //Add menu
        setHasOptionsMenu(true)
        return view
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun updateItem(){
        val firstName = view?.findViewById<EditText>(R.id.updateFirstName_et)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.updateLastName_et)?.text.toString()
        val age = Integer.parseInt(view?.findViewById<EditText>(R.id.updateAge_et)?.text.toString())

        if (inputCheck(firstName, lastName, age)){
            //Create User Object
            val updateUser = User(args.currentUser.id, firstName, lastName, age)
            // Update Current User
            mUserViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
            //Navigate Back
            this.findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Int): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age.toString()))
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "inflater.inflate(R.menu.delete_menu, menu)",
        "com.example.mydatabaseapp.R"
    )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onContextItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "Successfully removed: ${args.currentUser.firstName}", Toast.LENGTH_SHORT).show()
            this.findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){ _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()
    }

}