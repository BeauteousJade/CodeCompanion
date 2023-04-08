package com.jade.code.companion.chat.data

/**
 * 在列表中，用来展示提示信息。
 */
enum class Tip(val content: String) {
    WAIT("请求中,请稍后..."),
    RETRY("请求失败,点我重试...")
}