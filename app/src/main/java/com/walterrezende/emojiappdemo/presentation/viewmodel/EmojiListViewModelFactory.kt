package com.walterrezende.emojiappdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.walterrezende.emojiappdemo.repository.dao.EmojiDatabaseDao

class EmojiListViewModelFactory(
    private val dataSource: EmojiDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmojiListViewModel::class.java)) {
            return EmojiListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}