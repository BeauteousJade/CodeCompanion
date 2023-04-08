package com.jade.code.companion.chat.data

import com.google.gson.annotations.SerializedName

data class Choices(@SerializedName("message") val message: Message?)
data class Message(@SerializedName("role") val role: String?, @SerializedName("content") val content: String?) {}
class ChatResult(@SerializedName("choices") val choices: List<Choices>)


