package com.walterrezende.emojiappdemo.repository.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.walterrezende.emojiappdemo.repository.data.Emoji
import org.json.JSONException
import java.io.IOException


internal class EmojiJsonAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): List<Emoji> {
        val emojiList = mutableListOf<Emoji>()
        try {
            reader.beginObject()
            while (reader.hasNext()) {
                val name = reader.nextName()
                val url = reader.nextString()
                val emoji = Emoji(name = name, url = url)

                emojiList.add(emoji)
            }
            reader.endObject()
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return emojiList
    }
}