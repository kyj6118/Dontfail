package com.example.afinal.fragmants

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.afinal.MainActivity
import com.example.afinal.R
import com.example.afinal.databinding.FragmentSetBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SetFragment : Fragment() {
    private lateinit var binding : FragmentSetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_set,container,false)


        binding.logoutBtn.setOnClickListener{
            Firebase.auth.signOut()

            val intent = Intent(getActivity(), MainActivity::class.java)
            startActivity(intent)



        }


        binding.comTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_setFragment_to_boardFragment)
        }

        binding.classTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_setFragment_to_classFragment)
        }
        binding.mapTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_setFragment_to_mapFragment)
        }

        binding.homeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_setFragment_to_homeFragment)
        }
        // Inflate the layout for this fragment


        return binding.root
    }
}
