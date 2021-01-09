package com.walterrezende.emojiappdemo.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.walterrezende.emojiappdemo.repository.data.Emoji

@Dao
interface EmojiDatabaseDao {
    @Insert
    suspend fun insert(emoji: Emoji)

    @Update
    suspend fun update(emoji: Emoji)

    @Query("SELECT * from emojis WHERE emojiId = :id")
    fun getEmojiWithId(id: Long): LiveData<Emoji>

    @Query("DELETE FROM emojis")
    suspend fun clear()

    @Delete
    suspend fun deleteEmoji(emoji: Emoji): Int
}