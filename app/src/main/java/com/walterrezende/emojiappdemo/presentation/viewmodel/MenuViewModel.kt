package com.walterrezende.emojiappdemo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {

    private val _navigateToEmojiList = MutableLiveData<Boolean?>()
    val navigateToEmojiList: LiveData<Boolean?>
        get() = _navigateToEmojiList

    fun doneNavigatingToEmojiList() {
        _navigateToEmojiList.value = null
    }

    fun goToEmojisList() {
        _navigateToEmojiList.value = true
    }

}