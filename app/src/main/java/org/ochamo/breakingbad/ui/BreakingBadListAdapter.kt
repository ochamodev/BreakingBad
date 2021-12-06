package org.ochamo.breakingbad.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.ochamo.breakingbad.R
import org.ochamo.breakingbad.databinding.BreakingBadListItemBinding
import org.ochamo.breakingbad.ui.model.BreakingBadCharacterModel
import org.ochamo.breakingbad.ui.viewmodel.BreakingBadListViewModel
import org.ochamo.breakingbad.ui.viewmodel.SharedCharacterItemViewModel

class BreakingBadListAdapter(
    var characters: MutableSet<BreakingBadCharacterModel>,
    var viewModel: BreakingBadListViewModel,
    var sharedViewModel: SharedCharacterItemViewModel
): RecyclerView.Adapter<BreakingBadListAdapter.BreakingBadListViewHolder>() {

    class BreakingBadListViewHolder(
        val binding: BreakingBadListItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(viewModel: BreakingBadListViewModel, sharedViewModel: SharedCharacterItemViewModel,  characterModel: BreakingBadCharacterModel) {
            binding.model = characterModel
            binding.viewModel = viewModel
            binding.sharedViewModel = sharedViewModel
        }

    }

    fun addMoreItems(newItems: MutableSet<BreakingBadCharacterModel>) {
        if (characters.isEmpty()) {
            characters.addAll(newItems)
            notifyDataSetChanged()
            viewModel.updateLoadingStatus(false)

        } else {
            val last = itemCount
           /*val news = newItems.size
            val lastItem = newItems.last()
            val character = characters.last()
            if (lastItem.id != character.id) {
                characters.addAll(newItems)
                notifyItemRangeChanged(itemCount - 1, newItems.size)
            }*/
            characters.clear()
            characters.addAll(newItems)
            notifyDataSetChanged()
            viewModel.updateLoadingStatus(false)

        }
    }

    fun updateItem(item: BreakingBadCharacterModel) {
        val index = getItemPosition(item)
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakingBadListViewHolder {
        val binding = BreakingBadListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreakingBadListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreakingBadListViewHolder, position: Int) {
        holder.bind(viewModel, sharedViewModel, characters.elementAt(position))
    }

    override fun getItemCount(): Int = characters.size

    fun getItemPosition(item: BreakingBadCharacterModel): Int{
        return characters.indexOf(item)
    }

    override fun getItemId(position: Int): Long {
        return characters.elementAt(position).id.toLong()
    }

}

