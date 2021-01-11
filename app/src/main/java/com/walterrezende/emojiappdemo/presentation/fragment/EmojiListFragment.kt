package com.walterrezende.emojiappdemo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.walterrezende.emojiappdemo.R
import com.walterrezende.emojiappdemo.databinding.FragmentEmojisListBinding
import com.walterrezende.emojiappdemo.extensions.createGridLayoutManager
import com.walterrezende.emojiappdemo.presentation.adapter.EmojiClickListener
import com.walterrezende.emojiappdemo.presentation.adapter.EmojiListAdapter
import com.walterrezende.emojiappdemo.presentation.viewmodel.EmojiListViewModel
import com.walterrezende.emojiappdemo.repository.data.Emoji

class EmojiListFragment : Fragment() {

    private lateinit var binding: FragmentEmojisListBinding

    private val adapter by lazy { EmojiListAdapter(adapterListener) }

    private val adapterListener = EmojiClickListener { emojiId ->
        viewModel.onEmojiClicked(emojiId)
    }

    private val viewModel: EmojiListViewModel by lazy {
        ViewModelProvider(this).get(EmojiListViewModel::class.java)
    }

    private val emojiListObserver by lazy {
        Observer<List<Emoji>?> { emojis -> emojis?.let { adapter.submitList(it) } }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_emojis_list, container, false)

        binding.viewModel = viewModel
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
        with(viewModel) {
            emojiList.observe(this@EmojiListFragment, emojiListObserver)
        }
    }

    private fun removeObservers() {
        with(viewModel) {
            emojiList.removeObserver(emojiListObserver)
        }
    }

}