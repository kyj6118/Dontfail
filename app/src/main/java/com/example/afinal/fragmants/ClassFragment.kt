package com.example.afinal.fragmants

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.afinal.R
import com.example.afinal.Schdule.CourseActivity
import com.example.afinal.board.FreeBoardActivity
import com.example.afinal.databinding.FragmentClassBinding


class ClassFragment : Fragment() {

    private lateinit var binding : FragmentClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_class,container,false)

        binding.CourseBtn.setOnClickListener{

            val intent = Intent(getActivity(), CourseActivity::class.java)
            startActivity(intent)
        }


        binding.comTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_classFragment_to_boardFragment)
        }

        binding.homeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_classFragment_to_homeFragment)
        }
        binding.mapTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_classFragment_to_mapFragment)
        }

        binding.setTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_classFragment_to_setFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
    }


