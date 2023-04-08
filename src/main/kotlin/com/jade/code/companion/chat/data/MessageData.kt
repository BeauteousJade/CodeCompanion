package com.jade.code.companion.chat.data

/**
 * chatgpt message 的数据结构。
 */
data class MessageData(
    /**
     * 是否是用户发送的。如果不是，则是chatgpt发送的
     */
    val isMe: Boolean,
    /**
     * 发送的内容
     */
    val content: String
)