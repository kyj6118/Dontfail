package com.example.afinal.fragmants

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.afinal.R
import com.example.afinal.databinding.FragmentHomeBinding
import com.example.afinal.notice.NoticeActivity


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        binding.noticeView.setOnClickListener{

            val intent = Intent(getActivity(), NoticeActivity::class.java)
            startActivity(intent)

        }


        binding.comTap.setOnClickListener{
           it.findNavController().navigate(R.id.action_homeFragment_to_boardFragment)
        }

        binding.classTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_classFragment)
        }
        binding.mapTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }

        binding.setTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_setFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}