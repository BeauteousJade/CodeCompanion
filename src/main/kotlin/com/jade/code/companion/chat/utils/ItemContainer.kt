package com.jade.code.companion.chat.utils

import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import javax.swing.JComponent

interface ItemContainer {
}

fun ItemContainer.setup() {
    if (this !is JComponent) {
        return
    }
    isDoubleBuffered = true
    isOpaque = true
    background = JBColor(0xEAEEF7, 0x45494A)
    border = JBUI.Borders.empty(10, 20, 20, 10)
}