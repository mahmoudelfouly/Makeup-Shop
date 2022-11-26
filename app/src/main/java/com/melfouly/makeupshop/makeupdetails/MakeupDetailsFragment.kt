package com.melfouly.makeupshop.makeupdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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

        val makeupItem = MakeupDetailsFragmentArgs.fromBundle(requireArguments()).selectedItem
        Log.d("list", "Details of ${makeupItem.id}")

        binding.makeupItem = makeupItem

        binding.originalLinkButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(makeupItem.productLink))
            startActivity(intent)
        }

        binding.addToCartButton.setOnClickListener {
            viewModel.saveItemToCart(makeupItem.asDatabaseCartModel())
            Snackbar.make(binding.root, R.string.item_added_to_cart, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo) {
                    viewModel.deleteItemFromCart(makeupItem)
                }.show()
        }

        binding.cartFab.setOnClickListener {
            this.findNavController()
                .navigate(MakeupDetailsFragmentDirections.actionMakeupDetailsFragmentToCartFragment())
        }

        return binding.root
    }


}