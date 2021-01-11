package com.walterrezende.emojiappdemo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.walterrezende.emojiappdemo.R
import com.walterrezende.emojiappdemo.databinding.FragmentMenuBinding
import com.walterrezende.emojiappdemo.extensions.ifTrue
import com.walterrezende.emojiappdemo.presentation.viewmodel.EmojiListViewModel
import com.walterrezende.emojiappdemo.presentation.viewmodel.EmojiListViewModelFactory
import com.walterrezende.emojiappdemo.presentation.viewmodel.MenuViewModel
import com.walterrezende.emojiappdemo.repository.database.EmojiDatabase

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    private val menuViewModel: MenuViewModel by lazy {
        ViewModelProvider(this).get(MenuViewModel::class.java)
    }

    private val emojiListViewModel: EmojiListViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(EmojiListViewModel::class.java)
    }

    private val viewModelFactory: EmojiListViewModelFactory by lazy {
        val application = requireNotNull(this.activity).application
        val dataSource = EmojiDatabase.getInstance(application).emojiDatabaseDao
        EmojiListViewModelFactory(dataSource)
    }

    private val navigateToEmojisObserver by lazy {
        Observer<Boolean?> { it?.ifTrue { navigateToEmojisList() } }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        binding.viewModel = menuViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setMenuViewModelObservers()
    }

    override fun onStop() {
        super.onStop()
        removeMenuViewModelObservers()
    }

    private fun setMenuViewModelObservers() {
        with(menuViewModel) {
            navigateToEmojiList.observe(this@MenuFragment, navigateToEmojisObserver)
        }
    }

    private fun removeMenuViewModelObservers() {
        with(menuViewModel) {
            navigateToEmojiList.removeObserver(navigateToEmojisObserver)
        }
    }

    private fun navigateToEmojisList() {
        with(MenuFragmentDirections) {
            findNavController().navigate(actionMenuFragmentToEmojiListFragment())
            menuViewModel.doneNavigatingToEmojiList()
        }
    }

}