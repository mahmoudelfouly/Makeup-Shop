package com.melfouly.makeupshop.makeupdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.melfouly.makeupshop.R
import com.melfouly.makeupshop.data.database.asDatabaseCartModel
import com.melfouly.makeupshop.databinding.FragmentMakeupDetailsBinding


class MakeupDetailsFragment : Fragment() {

    private val viewModel: MakeupDetailsViewModel by viewModels()
    private lateinit var binding: FragmentMakeupDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMakeupDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // Getting id of makeupItem from args and put it in detailedItem liveData.
        val makeupItemId = MakeupDetailsFragmentArgs.fromBundle(requireArguments()).selectedItem
        viewModel.getItemById(makeupItemId)

        viewModel.detailedItem.observe(viewLifecycleOwner) {
            binding.makeupItem = it
        }

        // Once originalLinkButton clicked open apps that can view the link.
        binding.originalLinkButton.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.detailedItem.value!!.productLink))
            startActivity(intent)
        }

        // Once addToCartButton clicked save item to db and show a snackbar.
        binding.addToCartButton.setOnClickListener {
            viewModel.saveItemToCart(viewModel.detailedItem.value!!.asDatabaseCartModel())
            Snackbar.make(binding.root, R.string.item_added_to_cart, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo) {
                    viewModel.deleteItemFromCart(viewModel.detailedItem.value!!)
                }.show()
        }

        // Once cartFab clicked navigate to cartFragment.
        binding.cartFab.setOnClickListener {
            this.findNavController()
                .navigate(MakeupDetailsFragmentDirections.actionMakeupDetailsFragmentToCartFragment())
        }

        return binding.root
    }


}