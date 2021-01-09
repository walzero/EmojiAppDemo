package com.walterrezende.emojiappdemo.repository.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.walterrezende.emojiappdemo.repository.data.Emoji
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.github.com"

private val moshi by lazy {
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

private val retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()
}


interface EmojiApiService {
    @GET("emojis")
    suspend fun getEmojis(): List<Emoji>
}

object EmojiApi {
    val retrofitService: EmojiApiService by lazy {
        retrofit.create(EmojiApiService::class.java)
    }
}