package com.walterrezende.emojiappdemo.repository.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emojis")
data class Emoji(
    @PrimaryKey(autoGenerate = true)
    var emojiId: Long = 0L,
)
