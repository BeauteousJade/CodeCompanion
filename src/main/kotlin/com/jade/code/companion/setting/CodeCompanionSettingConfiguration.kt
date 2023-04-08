package com.jade.code.companion.setting

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.components.JBTabbedPane
import com.jade.code.companion.chat.setting.ChatSetting
import com.jade.code.companion.chat.setting.ChatSettingPanel
import com.jade.code.companion.chat.setting.getConfig
import com.jade.code.companion.chat.setting.setConfig
import com.jade.code.companion.chat.utils.RequestUtils
import javax.swing.JComponent

/**
 * 配置项。
 */
class CodeCompanionSettingConfiguration : Configurable {


    private val panel = SimpleToolWindowPanel(false)

    private val chatSettingPanel = ChatSettingPanel()

    private var isUpdate = false

    init {
        val tabbedPane = JBTabbedPane()
        // 添加chatgpt的配置
        tabbedPane.add("chatgpt", chatSettingPanel)
        panel.add(tabbedPane)

        // 加载缓存的配置。
        chatSettingPanel.setConfig(ChatSetting.getInstance().getConfig())
        chatSettingPanel.addUpdateListener {
            isUpdate = true
        }
    }

    override fun createComponent(): JComponent {
        return panel
    }

    override fun isModified(): Boolean {
        return isUpdate
    }

    /**
     * 当点击apply按钮。
     */
    override fun apply() {
        isUpdate = false
        ChatSetting.getInstance().setConfig(chatSettingPanel.getConfig())
        RequestUtils.apply()
    }

    override fun getDisplayName(): String {
        return "CodeCompanion"
    }
}