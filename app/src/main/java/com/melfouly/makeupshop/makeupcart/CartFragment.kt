package com.melfouly.makeupshop.makeupcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.melfouly.makeupshop.databinding.CartListItemBinding
import com.melfouly.makeupshop.databinding.FragmentCartBinding
import com.melfouly.makeupshop.model.MakeupItem

class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var binding: FragmentCartBinding

    //private lateinit var itemBinding: CartListItemBinding
    private lateinit var adapter: CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)
//        val itemBinding = CartListItemBinding.inflate(inflater)
//        itemBinding.viewModel = viewModel
//        itemBinding.makeupItem = x
//        itemBinding.deleteButton.setOnClickListener {
//            viewModel.deleteItemFromCart(x)
//        }
        (activity as AppCompatActivity).setSupportActionBar(binding.myToolbar)

        adapter = CartAdapter(CartAdapter.CartItemClickListener { cartItem ->
            viewModel.onMakeupItemClicked(cartItem)
        })

        binding.cartRecyclerview.adapter = adapter

        viewModel.cartList.observe(viewLifecycleOwner) { cartList ->
            cartList?.let { adapter.submitList(it) }
        }

        viewModel.detailedItem.observe(viewLifecycleOwner) { item ->
            item?.let {
                this.findNavController().navigate(
                    CartFragmentDirections.actionCartFragmentToMakeupDetailsFragment(it)
                )
                viewModel.doneNavigating()
            }
        }

        binding.cancelButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        return binding.root
    }
    
    fun toast() {
        Toast.makeText(requireContext(), "hi", Toast.LENGTH_SHORT).show()
    }


}