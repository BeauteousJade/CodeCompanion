package com.jade.code.companion.chat.widget

import com.intellij.util.ui.JBImageIcon
import com.jade.code.companion.utils.loadImage
import java.awt.Dimension
import java.awt.Image
import javax.swing.JLabel


/**
 * icon的包装View。
 */
class ImageIconWrapper : JLabel() {

    companion object {
        val cache = LinkedHashMap<String, Image>()
    }

    init {
        isOpaque = false
    }

    /**
     * 设置指定图片地址和大小。
     */
    fun setImage(url: String, size: Dimension) {
        val targetImage = cache[url] ?: loadImage(url, size).let {
            cache[url] = it
            it
        }
        icon = JBImageIcon(targetImage).apply {
            preferredSize = size
            isOpaque = false
        }
        revalidate()
        repaint()
    }

}