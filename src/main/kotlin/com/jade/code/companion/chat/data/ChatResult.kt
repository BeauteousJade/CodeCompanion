package com.jade.code.companion.chat.data

import com.google.gson.annotations.SerializedName

/**
 * chatgpt 的数据结构
 */
data class Choices(@SerializedName("message") val message: Message?)
data class Message(@SerializedName("role") val role: String?, @SerializedName("content") val content: String?) {}
data class ChatResult(@SerializedName("choices") val choices: List<Choices>)


