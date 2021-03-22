package com.example.encode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.encode.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    interface onTitleFragmentInteractionListener {
        fun onStudy()
    }
    var listener: onTitleFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as onTitleFragmentInteractionListener?
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
                inflater,
                R.layout.fragment_title,
                container,
                false
        )

        binding.infoTextView.setOnClickListener {
                listener?.onStudy()
        }


        return binding.root
    }
}