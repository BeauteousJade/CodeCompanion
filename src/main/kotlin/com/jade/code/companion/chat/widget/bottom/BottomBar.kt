package com.jade.code.companion.chat.widget.bottom

import com.intellij.ui.components.JBTextArea
import com.jade.code.companion.chat.widget.ImageIconWrapper
import com.jade.code.companion.utils.applySize
import com.jade.code.companion.utils.bindSize
import java.awt.Dimension
import java.awt.Insets
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.border.EmptyBorder

class BottomBar : JPanel() {

    private val textArea = createTextField()
    private val button = createButton()
    private var actionListener: ActionListener? = null

    init {
        layout = BoxLayout(this, BoxLayout.X_AXIS)
        border = BorderFactory.createEmptyBorder(5, 5, 5, 5)

        add(textArea)
        add(Box.createRigidArea(Dimension(10, 0))) // 添加水平间距
        add(button)

        bindSize {
            val insets = getEmptyBorder()
            val useWidth = insets.left + insets.right
            val useHeight = insets.top + insets.bottom
            val buttonWidth = 50
            textArea.applySize(it.width - useWidth - buttonWidth - 10, it.height - useHeight)
            button.applySize(50, it.height - useHeight)
        }
    }


    private fun getEmptyBorder(): Insets {
        return (border as EmptyBorder).borderInsets
    }


    private fun createTextField(): JBTextArea {
        return JBTextArea().apply {
            lineWrap = true
            wrapStyleWord = true
        }
    }

    private fun createButton(): JButton {
        return JButton().apply {
            isOpaque = false
            val panel = JPanel().apply {
                isOpaque = false
                layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
                add(ImageIconWrapper().apply {
                    alignmentX = JComponent.CENTER_ALIGNMENT
                    alignmentY = JComponent.CENTER_ALIGNMENT
                    setImage("icons/send.png", Dimension(20, 20))
                })
            }
            bindSize {
                panel.applySize(it.width, it.height)
            }
            add(panel)
        }
    }

    fun setOnClickListener(listener: ActionListener) {
        actionListener?.let {
            button.removeActionListener(it)
        }
        actionListener = ActionListener {
            if (getText().trim().isNotEmpty()) {
                listener.actionPerformed(it)
                textArea.text = ""
            }
        }
        button.addActionListener(actionListener)
    }

    fun setEnable(enable: Boolean) {
        textArea.isEditable = enable
        button.isEnabled = enable
    }

    fun getText(): String {
        return textArea.text
    }
}