package com.jade.code.companion.utils

import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.JBScrollPane
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import java.awt.Dimension
import java.awt.Image
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.imageio.ImageIO
import javax.swing.JComponent
import javax.swing.JLabel


/**
 * 将String用html格式包装。
 */
fun String.wrapHtml(): String {
    val newString = replace("\n", "<br>")
    return "<html><body> $newString </html></body>"
}

/**
 * String安全转换int.
 */
fun String.safelyToInt(): Int {
    return this.toIntOrNull() ?: 0
}

/**
 * markdown格式的文案，转为Html.
 */
fun String.mdToHtml(): String {
    return Parser.builder().build().let {
        HtmlRenderer.builder().build().render(it.parse(this)).wrapHtml()
    }
}

/**
 * 用来监听一个View宽度和高度变化。
 */
fun JComponent.bindSize(runBlock: (size: Dimension) -> Unit) {
    addComponentListener(object : ComponentAdapter() {
        override fun componentResized(e: ComponentEvent?) {
            e?.let {
                runBlock(Dimension(it.component.width, it.component.height))
            }
        }
    })
}

/**
 * 设置一个View的宽度和高度。
 */
fun JComponent.applySize(width: Int, height: Int) {
    size = Dimension(width, height)
    preferredSize = Dimension(width, height)
    revalidate()
    repaint()
}

/**
 * 加载图片。
 */
fun Any.loadImage(url: String, size: Dimension): Image {
    return (ImageIO.read(javaClass.classLoader.getResource(url))).getScaledInstance(
        size.width,
        size.height,
        Image.SCALE_AREA_AVERAGING
    )
}

/**
 * 滚动到顶部。
 */
fun JBScrollPane.scrollToTop() {
    verticalScrollBar.value = 0
}

/**
 * 滚动到底部。
 */
fun JBScrollPane.scrollToBottom() {
    verticalScrollBar.value = verticalScrollBar.maximum
}

/**
 * 给任意View设置一个点击事件。
 */
fun JComponent.setMouseClickListener(runBlock: () -> Unit) {
    addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            runBlock()
        }
    })
}

/**
 * 当鼠标移动到View上，出现一个Tips提示。
 * 当鼠标点击或者移出该View，则隐藏该Tips提示。
 */
fun JComponent.showTipsWhenMouseEntered(content: String) {
    addMouseListener(object : MouseAdapter() {
        private var popup: Balloon? = null
        override fun mouseEntered(e: MouseEvent?) {
            val factory = JBPopupFactory.getInstance()
            popup = factory.createBalloonBuilder(JLabel(content)).createBalloon()
            popup?.show(RelativePoint.getCenterOf(this@showTipsWhenMouseEntered), Balloon.Position.above)
        }

        override fun mouseExited(e: MouseEvent?) {
            popup?.hide()
        }

        override fun mouseClicked(e: MouseEvent?) {
            popup?.hide()
        }
    })
}