package com.walterrezende.emojiappdemo.presentation.viewmodel

import androidx.lifecycle.*
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

    private val _randomEmoji = MutableLiveData<Emoji?>()
    val randomEmoji = Transformations.map(_randomEmoji) {
        it ?: Emoji()
    }

    init {
        fetchEmojiList()
    }

    fun onEmojiClicked(emojiId: Long) {

    }

    fun chooseRandomEmoji() {
        viewModelScope.launch {
            _randomEmoji.value = queryRandomEmoji()
        }
    }

    fun fetchEmojiList() {
        Timber.d("##EMOJI FETCHING_SERVER_LIST!")
        viewModelScope.launch {
            try {
                val result = EmojiApi.retrofitService.getEmojis()
                Timber.d("##EMOJI SERVICE_LIST_SIZE: ${result.size}")

                if (result.isNotEmpty()) {
                    insert(result)
                    chooseRandomEmoji()
                }

            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private suspend fun queryRandomEmoji(): Emoji? {
        return withContext(Dispatchers.IO) {
            database.getRandomEmoji()
        }
    }

    private suspend fun insert(emojis: List<Emoji>) {
        withContext(Dispatchers.IO) {
            database.insert(emojis)
        }
    }
}