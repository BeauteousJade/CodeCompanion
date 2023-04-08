package com.jade.code.companion.chat.widget

import com.intellij.openapi.application.invokeLater
import com.intellij.ui.components.JBPanel
import com.jade.code.companion.chat.data.MessageData
import com.jade.code.companion.chat.data.Tip
import com.jade.code.companion.chat.setting.ChatSetting
import com.jade.code.companion.chat.utils.RequestUtils
import com.jade.code.companion.chat.widget.bottom.BottomBar
import com.jade.code.companion.chat.widget.content.ContentLayout
import com.jade.code.companion.utils.*
import io.reactivex.schedulers.Schedulers
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BoxLayout

class ChatGptLayout : JBPanel<ChatGptLayout>() {

    private val contentLayout = createContent()
    private val bottomBar = createBottomBar()

    init {
        add(contentLayout)
        add(bottomBar)
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        bindSize {
            val bottomHeight = 80
            contentLayout.applySize(it.width, it.height - bottomHeight)
            bottomBar.applySize(it.width, bottomHeight)
        }
        bottomBar.setOnClickListener {
            val key = ChatSetting.getInstance().apiToken
            if (key.trim().isEmpty()) {
                Utils.notifyConfigKey()
                return@setOnClickListener
            }
            val text = bottomBar.getText()
            contentLayout.addItem(MessageData(true, text.wrapHtml()))
            request(text)
        }
    }


    private fun request(question: String) {
        val key = ChatSetting.getInstance().apiToken
        bottomBar.setEnable(false)
        contentLayout.removeTips(Tip.RETRY, Tip.WAIT)
        contentLayout.addTip(Tip.WAIT)
        println(question)
        RequestUtils.request(question, key).subscribeOn(Schedulers.io()).subscribe({
            println(it)
            it.choices[0].message?.content?.let { answer ->
                val html = answer.mdToHtml()
                invokeLater {
                    println(html)
                    println(answer)
                    contentLayout.removeTips(Tip.WAIT)
                    contentLayout.addItem(MessageData(false, html))
                    bottomBar.setEnable(true)
                }
            }
        }, {
            contentLayout.removeTips(Tip.WAIT)
            bottomBar.setEnable(true)
            val tipsLayout = contentLayout.addTip(Tip.RETRY)
            tipsLayout.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    retry()
                }
            })
            it.printStackTrace()
        })
    }

    private fun retry() {
        request(contentLayout.getLastQuestion())
    }

    private fun createBottomBar(): BottomBar {
        return BottomBar()
    }

    private fun createContent(): ContentLayout {
        return ContentLayout()
    }

}