package org.ochamo.breakingbad.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.ochamo.breakingbad.databinding.BreakingBadListItemBinding
import org.ochamo.breakingbad.ui.model.BreakingBadCharacterModel
import org.ochamo.breakingbad.ui.viewmodel.BreakingBadListViewModel

class BreakingBadListAdapter(
    var characters: ArrayList<BreakingBadCharacterModel>,
    var viewModel: BreakingBadListViewModel
): RecyclerView.Adapter<BreakingBadListAdapter.BreakingBadListViewHolder>() {

    class BreakingBadListViewHolder(
        val binding: BreakingBadListItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(viewModel: BreakingBadListViewModel, characterModel: BreakingBadCharacterModel) {
            binding.model = characterModel
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakingBadListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BreakingBadListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = characters.size

}