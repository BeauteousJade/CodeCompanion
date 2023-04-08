package com.jade.code.companion.chat.widget.content

import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.JBUI
import com.jade.code.companion.chat.data.MessageData
import com.jade.code.companion.chat.data.Tip
import com.jade.code.companion.chat.widget.BottomTipsLayout
import com.jade.code.companion.chat.widget.ImageIconWrapper
import com.jade.code.companion.chat.widget.JadeJBScrollPane
import com.jade.code.companion.chat.widget.message.MessageItemContainer
import com.jade.code.companion.utils.*
import com.jetbrains.rd.util.forEachReversed
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.ScrollPaneConstants


class ContentLayout : JBPanel<ContentLayout>() {

    private val list = JPanel(VerticalLayout(JBUI.scale(10)))
    private val scrollPane: JadeJBScrollPane = JadeJBScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)

    init {
        layout = BorderLayout(JBUI.scale(7), 0)

        scrollPane.border = JBUI.Borders.empty()

        add(scrollPane, BorderLayout.CENTER)

        bindSize {
            scrollPane.applySize(it.width, it.height)
        }
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            border = JBUI.Borders.empty(5)
            val iconSize = 22
            add(ImageIconWrapper().apply {
                setImage("icons/scroll_top.png", Dimension(iconSize, iconSize))
                showTipsWhenMouseEntered("滑动到顶部")
                setOnClickListener { scrollPane.scrollToTop() }
            })
            add(ImageIconWrapper().apply {
                setImage("icons/scroll_bottom.png", Dimension(iconSize, iconSize))
                showTipsWhenMouseEntered("滑动到底部")
                setOnClickListener { scrollPane.scrollToBottom() }
            })
            add(ImageIconWrapper().apply {
                setImage("icons/clear.png", Dimension(iconSize, iconSize))
                showTipsWhenMouseEntered("清空所有数据")
                setOnClickListener {
                    list.removeAll()
                    list.updateUI()
                }
            })
        }, BorderLayout.NORTH)
    }

    fun addItem(messageData: MessageData) {
        list.add(MessageItemContainer(messageData))
        updateUI()
    }

    fun getLastQuestion(): String {
        val children = list.components
        children.forEachReversed {
            if (it is MessageItemContainer && it.messageData.isMe) {
                return it.messageData.content
            }
        }
        return ""
    }

    fun addTip(tip: Tip): BottomTipsLayout {
        val tipsLayout = BottomTipsLayout(tip)
        list.add(tipsLayout)
        updateUI()
        return tipsLayout
    }

    fun removeTips(vararg tips: Tip) {
        val children = list.components
        val removeChildList = mutableListOf<JComponent>()
        for (child in children) {
            if (child is BottomTipsLayout && tips.contains(child.tip)) {
                removeChildList.add(child)
            }
        }
        for (child in removeChildList) {
            list.remove(child)
        }
        updateUI()
    }

}