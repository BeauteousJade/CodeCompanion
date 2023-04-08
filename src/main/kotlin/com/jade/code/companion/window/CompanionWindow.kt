package com.jade.code.companion.window

import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.components.JBTabbedPane
import com.jade.code.companion.chat.widget.ChatGptLayout
import javax.swing.*

/**
 * 插件的窗口类。默认会出现在ide的右侧工具栏区域。
 */
class CompanionWindow() : SimpleToolWindowPanel(false) {

    init {
        val tabbedPane = JBTabbedPane()
        tabbedPane.add("chatgpt", createChatGptPanel())
        add(tabbedPane)
    }


    private fun createChatGptPanel(): JComponent {
        return ChatGptLayout()
    }
}