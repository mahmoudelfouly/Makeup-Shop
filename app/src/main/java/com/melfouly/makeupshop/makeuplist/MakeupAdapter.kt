package com.melfouly.makeupshop.makeuplist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melfouly.makeupshop.databinding.MakeupListItemBinding
import com.melfouly.makeupshop.model.MakeupItem

class MakeupAdapter(private val clickListener: MakeupItemClickListener) :
    ListAdapter<MakeupItem, MakeupAdapter.MakeupViewHolder>(DiffCallback()) {

    class MakeupViewHolder(private val binding: MakeupListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(makeupItem: MakeupItem, clickListener: MakeupItemClickListener) {
            binding.makeupItem = makeupItem
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeupViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MakeupListItemBinding.inflate(layoutInflater, parent, false)
        return MakeupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MakeupViewHolder, position: Int) {
        val makeupItem = getItem(position)
        holder.bind(makeupItem, clickListener)
    }

    class DiffCallback : DiffUtil.ItemCallback<MakeupItem>() {
        override fun areItemsTheSame(oldItem: MakeupItem, newItem: MakeupItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MakeupItem, newItem: MakeupItem): Boolean {
            return oldItem == newItem
        }
    }

    class MakeupItemClickListener(val clickListener: (makeupItem: MakeupItem) -> Unit) {
        fun onClick(makeupItem: MakeupItem) = clickListener(makeupItem)
    }

}