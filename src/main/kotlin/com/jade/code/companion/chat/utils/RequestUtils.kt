package com.jade.code.companion.chat.utils

import com.jade.code.companion.chat.data.ChatResult
import com.jade.code.companion.chat.setting.ChatSetting
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit

object RequestUtils {

    private var okHttpClient = createHttpClient()
    private var retrofit = createRetrofit()

    fun request(question: String, key: String): Observable<ChatResult> {
        val body = """{"model":"gpt-3.5-turbo", "messages":[{"role":"user", "content":"$question"}]}"""
        val mediaType = MediaType.parse("application/json")
        val requestBody = RequestBody.create(mediaType, body)
        return retrofit.create(Service::class.java).getData("Bearer $key", requestBody)
    }

    fun apply() {
        okHttpClient = createHttpClient()
        retrofit = createRetrofit()
    }


    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.openai.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val hostName = ChatSetting.getInstance().hostName.trim()
            val port = ChatSetting.getInstance().port
            if (hostName.isNotEmpty() && port > 0) {
                proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(hostName, port)))
            }
            callTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()
    }
}