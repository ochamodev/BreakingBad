package org.ochamo.breakingbad.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.ochamo.breakingbad.R
import org.ochamo.breakingbad.databinding.FragmentBreakingBadCharactersListBinding
import org.ochamo.breakingbad.ui.viewmodel.BreakingBadListViewModel
import org.ochamo.breakingbad.ui.viewmodel.SharedCharacterItemViewModel
import org.ochamo.breakingbad.util.EventObserver

@AndroidEntryPoint
class BreakingBadCharactersList : Fragment() {


    private val breakingBadListViewModel by viewModels<BreakingBadListViewModel>()
    private val sharedCharacterItemViewModel by activityViewModels<SharedCharacterItemViewModel>()
    private lateinit var binding: FragmentBreakingBadCharactersListBinding;
    private lateinit var breakingBadListAdapter: BreakingBadListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_breaking_bad_characters_list, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListAdapter()
        setupListeners()
        getCharacters()
    }

    private fun setupListAdapter() {
        breakingBadListAdapter = BreakingBadListAdapter(
            viewModel = breakingBadListViewModel,
            characters = mutableSetOf(),
            sharedViewModel = sharedCharacterItemViewModel
        )

        binding.breakingBadRecyclerView.adapter = breakingBadListAdapter
        binding.breakingBadRecyclerView.layoutManager = LinearLayoutManager(context)



    }

    private fun setupListeners() {
        breakingBadListViewModel.listOfCharacters.observe(this.viewLifecycleOwner, {
            breakingBadListAdapter.addMoreItems(it)
        })

        binding.breakingBadRecyclerView.addOnScrollListener(object:
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val layoutManager = binding.breakingBadRecyclerView.layoutManager!! as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        breakingBadListViewModel.listOfCharacters.value!!.size
                        breakingBadListViewModel.getCharacters()
                    }
                }
            }
        })

        breakingBadListViewModel.selectedItem.observe(this.viewLifecycleOwner, EventObserver {
            sharedCharacterItemViewModel.setItem(it)
            val navCtrl = findNavController()
            navCtrl.navigate(R.id.action_breakingBadCharactersList_to_breakingBadCharacterDetail)

        })

    }

    private fun getCharacters() {
        if (breakingBadListViewModel.listOfCharacters.value!!.isEmpty()) {
            breakingBadListViewModel.getCharacters()
        }
    }

}