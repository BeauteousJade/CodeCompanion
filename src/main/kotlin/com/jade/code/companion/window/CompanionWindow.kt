package com.jade.code.companion.window

import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.components.JBTabbedPane
import com.jade.code.companion.chat.widget.ChatGptLayout
import javax.swing.*

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