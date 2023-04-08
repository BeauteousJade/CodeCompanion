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
import javax.swing.JScrollBar


fun String.wrapHtml(): String {
    val newString = replace("\n", "<br>")
    return "<html><body> $newString </html></body>"
}

fun String.safelyToInt(): Int {
    return this.toIntOrNull() ?: 0
}

fun String.mdToHtml(): String {
    return Parser.builder().build().let {
        HtmlRenderer.builder().build().render(it.parse(this)).wrapHtml()
    }
}

fun JComponent.bindSize(runBlock: (size: Dimension) -> Unit) {
    addComponentListener(object : ComponentAdapter() {
        override fun componentResized(e: ComponentEvent?) {
            e?.let {
                runBlock(Dimension(it.component.width, it.component.height))
            }
        }
    })
}

fun JComponent.applySize(width: Int, height: Int) {
    size = Dimension(width, height)
    preferredSize = Dimension(width, height)
    revalidate()
    repaint()
}

fun Any.loadImage(url: String, size: Dimension): Image {
    return (ImageIO.read(javaClass.classLoader.getResource(url))).getScaledInstance(size.width, size.height, Image.SCALE_AREA_AVERAGING)
}

fun JBScrollPane.scrollToTop() {
    verticalScrollBar.value = 0
}

fun JBScrollPane.scrollToBottom() {
    verticalScrollBar.value = verticalScrollBar.maximum
}

fun JComponent.setOnClickListener(runBlock: () -> Unit) {
    addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            runBlock()
        }
    })
}
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