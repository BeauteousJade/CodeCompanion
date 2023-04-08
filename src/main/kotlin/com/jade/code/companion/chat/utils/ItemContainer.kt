package com.jade.code.companion.chat.utils

import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import javax.swing.JComponent

/**
 * MessageItem的接口，用来提供一些共有方法。
 */
interface ItemContainer {
}

/**
 * 初始化相关设置。
 */
fun ItemContainer.setup() {
    if (this !is JComponent) {
        return
    }
    isDoubleBuffered = true
    isOpaque = true
    background = JBColor(0xEAEEF7, 0x45494A)
    border = JBUI.Borders.empty(10, 20, 20, 10)
}