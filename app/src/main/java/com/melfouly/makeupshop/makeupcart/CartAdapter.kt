package com.melfouly.makeupshop.makeupcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melfouly.makeupshop.databinding.CartListItemBinding
import com.melfouly.makeupshop.model.MakeupItem

class CartAdapter(private val clickListener: CartItemClickListener) :
    ListAdapter<MakeupItem, CartAdapter.CartViewHolder>(DiffCallback()) {

    class CartViewHolder(private val binding: CartListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(makeupItem: MakeupItem, clickListener: CartItemClickListener) {
            binding.makeupItem = makeupItem
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CartListItemBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
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

    class CartItemClickListener(val clickListener: (makeupItem: MakeupItem) -> Unit) {
        fun onClick(makeupItem: MakeupItem) = clickListener(makeupItem)
    }

}