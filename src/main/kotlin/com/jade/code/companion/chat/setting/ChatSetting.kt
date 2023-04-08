package com.jade.code.companion.chat.setting

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage


fun ChatSetting.getConfig(): Triple<String, String, Int> {
    return Triple(apiKey, hostName, port)
}

fun ChatSetting.setConfig(config: Triple<String, String, Int>) {
    apiKey = config.first
    hostName = config.second
    port = config.third
}

/**
 * chatgpt的配置项。
 */
@State(name = "chatgpt", storages = [Storage(value = "chatgpt.xml")])
class ChatSetting : PersistentStateComponent<ChatSetting> {

    // 必须public，否则缓存不了。
    // chatgpt的key。
    var apiKey: String = ""

    // 代理服务器地址
    var hostName: String = ""

    // 代理服务器的端口
    var port: Int = 0

    companion object {
        fun getInstance(): ChatSetting {
            return ApplicationManager.getApplication().getService(ChatSetting::class.java)
        }
    }

    override fun getState(): ChatSetting {
        return this
    }

    override fun loadState(state: ChatSetting) {
        setConfig(Triple(state.apiKey, state.hostName, state.port))
    }

}