package org.ochamo.breakingbad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.ochamo.breakingbad.R
import org.ochamo.breakingbad.databinding.FragmentBreakingBadCharacterDetailBinding
import org.ochamo.breakingbad.ui.viewmodel.SharedCharacterItemViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class BreakingBadCharacterDetail : Fragment() {

    private val sharedCharacterItemViewModel by activityViewModels<SharedCharacterItemViewModel>()
    private lateinit var binding: FragmentBreakingBadCharacterDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_bad_character_detail, container, false)
        binding.model = sharedCharacterItemViewModel.breakingBadItem.value!!
        binding.sharedViewModel = sharedCharacterItemViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarTitle()
        setupListeners()

    }

    private fun setupToolbarTitle() {
        val breakingBadItem = sharedCharacterItemViewModel.breakingBadItem
        (activity as AppCompatActivity).supportActionBar!!.title = breakingBadItem.value!!.name
    }

    private fun setupListeners() {
    }

}