package com.example.bagimodal.ui.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bagimodal.R
import com.example.bagimodal.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var fragmentSearchBinding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater,container,false)
        return fragmentSearchBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSearchBinding.searchView.maxWidth

    }
}