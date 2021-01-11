package com.walterrezende.emojiappdemo.repository.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "emojis", indices = [Index(value = ["emoji_name"], unique = true)])
data class Emoji(
    @PrimaryKey(autoGenerate = true)
    var emojiId: Long = 0L,

    @ColumnInfo(name = "emoji_name")
    var name: String? = "",

    @ColumnInfo(name = "emoji_url")
    var url: String? = ""
) : Parcelable
