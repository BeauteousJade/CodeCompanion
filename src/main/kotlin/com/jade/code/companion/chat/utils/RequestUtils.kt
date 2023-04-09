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

/**
 * chatgpt接口请求的工具类。
 */
object RequestUtils {

    private var okHttpClient = createHttpClient()
    private var retrofit = createRetrofit()

    /**
     * 发起请求。
     */
    fun request(question: String, key: String): Observable<ChatResult> {
        val body = """{"model":"gpt-3.5-turbo", "messages":[{"role":"user", "content":"$question"}]}"""
        val mediaType = MediaType.parse("application/json")
        val requestBody = RequestBody.create(mediaType, body)
        return retrofit.create(Service::class.java).getData("Bearer $key", requestBody)
    }

    /**
     * 当重新更新了配置，例如代理服务器等，所以重新创建okHttpClient和retrofit的实例。
     */
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
            createProxy()?.let {
                proxy(it)
            } ?: run {
                proxy(null)
            }
            callTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()
    }

    /**
     * 根据不同的配置，设置不同的代理
     */
    private fun createProxy(): Proxy? {
        val proxyType = ChatSetting.getInstance().proxyType
        val hostName = ChatSetting.getInstance().hostName.trim()
        val port = ChatSetting.getInstance().port
        if (proxyType == ChatSetting.PROXY_TYPE_NONE) {
            return null
        }
        if (hostName.isEmpty() || proxyType <= 0) {
            return null
        }
        return when (proxyType) {
            ChatSetting.PROXY_TYPE_HTTP -> {
                Proxy(Proxy.Type.HTTP, InetSocketAddress(hostName, port))
            }

            else -> {
                Proxy(Proxy.Type.SOCKS, InetSocketAddress(hostName, port))
            }
        }
    }
}