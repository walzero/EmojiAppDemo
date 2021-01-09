package com.walterrezende.emojiappdemo

import android.app.Application
import timber.log.Timber

class EmojiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}