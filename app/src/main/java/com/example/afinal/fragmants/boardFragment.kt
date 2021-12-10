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
import com.example.afinal.board.FreeBoardActivity
import com.example.afinal.databinding.FragmentBoardBinding
import com.example.afinal.databinding.FragmentHomeBinding
import com.example.afinal.evaluate.EvaluateBoard
import com.example.afinal.evaluate.EvaluateBoardWrite


class boardFragment : Fragment() {

    private lateinit var binding : FragmentBoardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_board,container,false)

        binding.FreeBtn.setOnClickListener{
           val intent = Intent(getActivity(),FreeBoardActivity::class.java)
            startActivity(intent)
        }

        binding.evalBtn.setOnClickListener{

            val intent = Intent(getActivity(), EvaluateBoard::class.java)
            startActivity(intent)

        }


        binding.homeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_boardFragment_to_homeFragment)
        }


        binding.classTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_boardFragment_to_classFragment)
        }
        binding.mapTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_boardFragment_to_mapFragment)
        }

        binding.setTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_boardFragment_to_setFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}


