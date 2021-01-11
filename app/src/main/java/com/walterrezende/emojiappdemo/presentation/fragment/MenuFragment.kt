package com.walterrezende.emojiappdemo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.walterrezende.emojiappdemo.databinding.FragmentMenuBinding
import com.walterrezende.emojiappdemo.extensions.ifTrue
import com.walterrezende.emojiappdemo.presentation.viewmodel.EmojiViewModel
import com.walterrezende.emojiappdemo.presentation.viewmodel.EmojiViewModelFactory
import com.walterrezende.emojiappdemo.presentation.viewmodel.MenuViewModel
import com.walterrezende.emojiappdemo.repository.database.EmojiDatabase

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    private val menuViewModel: MenuViewModel by lazy {
        ViewModelProvider(this).get(MenuViewModel::class.java)
    }

    private val emojiViewModel: EmojiViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(EmojiViewModel::class.java)
    }

    private val viewModelFactory: EmojiViewModelFactory by lazy {
        val application = requireNotNull(this.activity).application
        val dataSource = EmojiDatabase.getInstance(application).emojiDatabaseDao
        EmojiViewModelFactory(dataSource)
    }

    private val navigateToEmojisObserver by lazy {
        Observer<Boolean?> { it?.ifTrue { navigateToEmojisList() } }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.menuViewModel = menuViewModel
        binding.emojiViewModel = emojiViewModel
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