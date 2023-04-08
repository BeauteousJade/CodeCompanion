package com.jade.code.companion.chat.widget.message

import com.intellij.ui.components.JBPanel
import com.intellij.util.ui.JBUI
import com.jade.code.companion.chat.data.MessageData
import com.jade.code.companion.chat.widget.ImageIconWrapper
import com.jade.code.companion.chat.utils.ItemContainer
import com.jade.code.companion.chat.utils.setup
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JEditorPane
import javax.swing.JPanel

class MessageItemContainer(val messageData: MessageData) : JBPanel<MessageItemContainer>(), ItemContainer {

    private val messageItem: MessageItemPanel = MessageItemPanel().apply {
        isEditable = false
        putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, java.lang.Boolean.TRUE)
        contentType = "text/html; charset=UTF-8"
        isOpaque = false
        border = null
        setMessage(messageData.content)
        isEditable = false
        if (caret != null) {
            caretPosition = 0
        }
        revalidate()
        repaint()
    }

    init {
        setup()
        layout = BorderLayout(JBUI.scale(7), 0)
        val imageIconWrapper = JPanel().apply {
            isOpaque = false
            layout = BorderLayout()
            val imageIcon = ImageIconWrapper().apply {
                setImage(getAvatar(), Dimension(20, 20))
            }
            add(imageIcon, BorderLayout.NORTH)
        }
        add(imageIconWrapper, BorderLayout.WEST)
        add(messageItem, BorderLayout.CENTER)
    }

    private fun getAvatar(): String {
        return if (messageData.isMe) "icons/me.png" else "icons/ai.png"
    }

}