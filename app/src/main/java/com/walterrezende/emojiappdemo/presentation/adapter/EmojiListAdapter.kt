package com.walterrezende.emojiappdemo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walterrezende.emojiappdemo.databinding.ItemEmojiBinding
import com.walterrezende.emojiappdemo.repository.data.Emoji

class EmojiListAdapter(
    private val emojiClickListener: EmojiClickListener
) : ListAdapter<Emoji, EmojiViewHolder>(EmojiDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        return EmojiViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, emojiClickListener)
    }
}

class EmojiDiffCallback : DiffUtil.ItemCallback<Emoji>() {
    override fun areItemsTheSame(oldItem: Emoji, newItem: Emoji): Boolean {
        return oldItem.emojiId == newItem.emojiId
    }

    override fun areContentsTheSame(oldItem: Emoji, newItem: Emoji): Boolean {
        return oldItem == newItem
    }

}

class EmojiViewHolder(val binding: ItemEmojiBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Emoji, emojiClickListener: EmojiClickListener) {
        binding.emoji = item
        binding.emojiListener = emojiClickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): EmojiViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemEmojiBinding.inflate(layoutInflater, parent, false)
            return EmojiViewHolder(binding)
        }
    }
}

class EmojiClickListener(val clickListener: (emojiId: Long) -> Unit) {
    fun onClick(emoji: Emoji) = clickListener(emoji.emojiId)
}