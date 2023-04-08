package com.jade.code.companion.chat.utils

import com.jade.code.companion.chat.data.ChatResult
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface Service {
    @Headers("Content-Type:application/json", "Accept-Charset:UTF-8", "Accept:*/*", "Content-Length:1000")
    @POST("v1/chat/completions")
    fun getData(@Header("Authorization") apiKey: String, @Body body: RequestBody): Observable<ChatResult>
}