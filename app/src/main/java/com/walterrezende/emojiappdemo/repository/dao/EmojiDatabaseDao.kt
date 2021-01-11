package com.walterrezende.emojiappdemo.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.walterrezende.emojiappdemo.repository.data.Emoji

@Dao
interface EmojiDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emoji: Emoji)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emojis: List<Emoji>)

    @Update
    suspend fun update(emoji: Emoji)

    @Query("SELECT * from emojis")
    fun getAllEmojis(): LiveData<List<Emoji>>

    @Query("SELECT * from emojis WHERE emoji_name = :name")
    fun getEmojiWithName(name: String): LiveData<Emoji>

    @Query("DELETE FROM emojis")
    suspend fun clear()

    @Delete
    suspend fun deleteEmoji(emoji: Emoji): Int
}