package com.walterrezende.emojiappdemo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walterrezende.emojiappdemo.repository.api.EmojiApi
import com.walterrezende.emojiappdemo.repository.data.Emoji
import kotlinx.coroutines.launch
import timber.log.Timber

class EmojiListViewModel : ViewModel() {

    private val _emojiList = MutableLiveData<List<Emoji>>()
    val emojiList: LiveData<List<Emoji>>
        get() = _emojiList

    init {
        getEmojiList()
    }

    fun getEmojiList() {
        viewModelScope.launch {
            try {
                val result = EmojiApi.retrofitService.getEmojis()

                if (result.isNotEmpty())
                    _emojiList.value = result

            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun onEmojiClicked(emojiId: Long) {

    }
}