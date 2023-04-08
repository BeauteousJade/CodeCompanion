package com.jade.code.companion.chat.widget

import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.Label
import com.jade.code.companion.chat.data.Tip
import com.jade.code.companion.chat.utils.ItemContainer
import com.jade.code.companion.chat.utils.setup
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JComponent

class BottomTipsLayout(val tip: Tip) : JBPanel<BottomTipsLayout>(), ItemContainer {

    init {
        setup()
        layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        add(Label(tip.content).apply {
            alignmentX = JComponent.CENTER_ALIGNMENT
            alignmentY = JComponent.CENTER_ALIGNMENT
        }, BorderLayout.WEST)
    }

}