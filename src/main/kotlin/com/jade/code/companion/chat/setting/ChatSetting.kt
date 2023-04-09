package com.jade.code.companion.chat.setting

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage


fun ChatSetting.getConfig(): ChatSettingConfig {
    return ChatSettingConfig(apiKey, hostName, port, proxyType)
}

fun ChatSetting.setConfig(config: ChatSettingConfig) {
    apiKey = config.apiKey
    hostName = config.hostName
    port = config.port
    proxyType = config.proxyType
}

data class ChatSettingConfig(val apiKey: String, val hostName: String, val port: Int, val proxyType: Int)

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

    // 代理类型
    var proxyType: Int = PROXY_TYPE_NONE

    companion object {

        const val PROXY_TYPE_NONE = 0
        const val PROXY_TYPE_HTTP = 1
        const val PROXY_TYPE_SOCKS = 2


        fun getInstance(): ChatSetting {
            return ApplicationManager.getApplication().getService(ChatSetting::class.java)
        }
    }

    override fun getState(): ChatSetting {
        return this
    }

    override fun loadState(state: ChatSetting) {
        setConfig(ChatSettingConfig(state.apiKey, state.hostName, state.port, state.proxyType))
    }

}