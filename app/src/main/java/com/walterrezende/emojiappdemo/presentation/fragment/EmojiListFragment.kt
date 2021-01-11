package com.walterrezende.emojiappdemo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.walterrezende.emojiappdemo.databinding.FragmentEmojisListBinding
import com.walterrezende.emojiappdemo.extensions.createGridLayoutManager
import com.walterrezende.emojiappdemo.presentation.adapter.EmojiClickListener
import com.walterrezende.emojiappdemo.presentation.adapter.EmojiListAdapter
import com.walterrezende.emojiappdemo.presentation.viewmodel.EmojiViewModel
import com.walterrezende.emojiappdemo.presentation.viewmodel.EmojiViewModelFactory
import com.walterrezende.emojiappdemo.repository.data.Emoji
import com.walterrezende.emojiappdemo.repository.database.EmojiDatabase
import timber.log.Timber

class EmojiListFragment : Fragment() {

    private lateinit var binding: FragmentEmojisListBinding

    private val adapter by lazy { EmojiListAdapter(adapterListener) }

    private val adapterListener = EmojiClickListener { emojiId ->
        emojiViewModel.onEmojiClicked(emojiId)
    }

    private val emojiViewModel: EmojiViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(EmojiViewModel::class.java)
    }

    private val viewModelFactory: EmojiViewModelFactory by lazy {
        val application = requireNotNull(this.activity).application
        val dataSource = EmojiDatabase.getInstance(application).emojiDatabaseDao
        EmojiViewModelFactory(dataSource)
    }

    private val emojiListObserver by lazy {
        Observer<List<Emoji>?> { emojis ->
            emojis?.let {
                Timber.d("##EMOJI DATABASE_LIST_SIZE: ${it.size}")
                adapter.submitList(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEmojisListBinding.inflate(inflater, container, false)

        binding.viewModel = emojiViewModel
        binding.gridLayoutManager = createGridLayoutManager()
        binding.emojiList.adapter = adapter
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        addObservers()
    }

    override fun onStop() {
        super.onStop()
        removeObservers()
    }

    private fun addObservers() {
        with(emojiViewModel) {
            emojiList.observe(this@EmojiListFragment, emojiListObserver)
        }
    }

    private fun removeObservers() {
        with(emojiViewModel) {
            emojiList.removeObserver(emojiListObserver)
        }
    }

}