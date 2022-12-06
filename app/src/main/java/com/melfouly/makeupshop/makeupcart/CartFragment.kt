package com.melfouly.makeupshop.makeupcart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.melfouly.makeupshop.R
import com.melfouly.makeupshop.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        binding = FragmentCartBinding.inflate(inflater)

        // Adjust the toolbar.
        (activity as AppCompatActivity).setSupportActionBar(binding.myToolbar)

        // Adjust the adapter to make onCartItemClickListener and onDeleteItemClickListener.
        adapter = CartAdapter(CartAdapter.CartItemClickListener { makeupItem ->
            viewModel.onMakeupItemClicked(makeupItem)
        }, CartAdapter.DeleteItemClickListener { makeupItem ->
            viewModel.deleteItemFromCart(makeupItem)
        }
        )

        binding.cartRecyclerview.adapter = adapter

        // Observe cartList liveData and set the changes to the adapter.
        viewModel.cartList.observe(viewLifecycleOwner) { cartList ->
            cartList?.let { adapter.submitList(it) }
        }

        // Observe detailedItem liveData to navigate to details fragment of that item.
        viewModel.detailedItem.observe(viewLifecycleOwner) { item ->
            item?.let {
                this.findNavController().navigate(
                    CartFragmentDirections.actionCartFragmentToMakeupDetailsFragment(it.id)
                )
                viewModel.doneNavigating()
            }
        }

        binding.sendOrderButton.setOnClickListener {
            shareCart()
        }

        binding.cancelButton.setOnClickListener {
            this.findNavController().navigateUp()
        }

        return binding.root
    }

    // Getting share button ready to be used, Showing a toast if cart is empty, if not then
    // store the cartItem name and product link to a list and send it with an intent.
    private fun shareCart() {
        if (viewModel.cartList.value!!.isEmpty()) {
            Toast.makeText(requireContext(), R.string.list_is_empty, Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(Intent.ACTION_SEND).setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
            val shareSummaryList = arrayListOf<String>()
            for (item in 0 until viewModel.cartList.value!!.size) {
                val shareSummary = getString(
                    R.string.share_details,
                    viewModel.cartList.value!![item].name,
                    viewModel.cartList.value!![item].productLink
                )
                shareSummaryList.add(shareSummary)
            }
            val finalShareSummary = shareSummaryList.joinToString(separator = "\n\n")
            intent.putExtra(Intent.EXTRA_TEXT, finalShareSummary)
            startActivity(intent)
        }
    }

}