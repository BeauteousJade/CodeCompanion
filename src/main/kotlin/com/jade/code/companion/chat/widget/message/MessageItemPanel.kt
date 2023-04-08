package com.jade.code.companion.chat.widget.message

import com.intellij.util.ui.HtmlPanel
import com.intellij.util.ui.StartupUiUtil
import java.awt.Font

/**
 * 展示内容的View.
 */
class MessageItemPanel : HtmlPanel() {

    private var message: String? = "";

    override fun getBody(): String {
        return message?.let {
            it
        } ?: ""
    }

    override fun getBodyFont(): Font {
        return StartupUiUtil.getLabelFont()
    }

    fun setMessage(message: String) {
        this.message = message
        updateUI()
    }
}