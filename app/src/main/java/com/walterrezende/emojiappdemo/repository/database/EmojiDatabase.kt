package com.walterrezende.emojiappdemo.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.walterrezende.emojiappdemo.repository.dao.EmojiDatabaseDao
import com.walterrezende.emojiappdemo.repository.data.Emoji

@Database(entities = [Emoji::class], version = 1, exportSchema = false)
abstract class EmojiDatabase : RoomDatabase() {

    abstract val emojiDatabaseDao: EmojiDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: EmojiDatabase? = null

        fun getInstance(context: Context): EmojiDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        EmojiDatabase::class.java,
                        "emoji_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}