package com.walterrezende.emojiappdemo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walterrezende.emojiappdemo.repository.api.EmojiApi
import com.walterrezende.emojiappdemo.repository.dao.EmojiDatabaseDao
import com.walterrezende.emojiappdemo.repository.data.Emoji
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class EmojiViewModel(
    private val database: EmojiDatabaseDao,
) : ViewModel() {

    val emojiList: LiveData<List<Emoji>>
        get() = database.getAllEmojis()

    init {
        fetchEmojiList()
    }

    fun onEmojiClicked(emojiId: Long) {

    }

    fun fetchEmojiList() {
        Timber.d("##EMOJI FETCHING_SERVER_LIST!")
        viewModelScope.launch {
            try {
                val result = EmojiApi.retrofitService.getEmojis()
                Timber.d("##EMOJI SERVICE_LIST_SIZE: ${result.size}")

                if (result.isNotEmpty())
                    insert(result)

            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private suspend fun insert(emojis: List<Emoji>) {
        withContext(Dispatchers.IO) {
            database.insert(emojis)
        }
    }
}