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
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.swing.BoxLayout

class ChatGptLayout : JBPanel<ChatGptLayout>() {

    private val contentLayout = createContent()
    private val bottomBar = createBottomBar()
    private var requestDisposable: Disposable? = null

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
            val key = ChatSetting.getInstance().apiKey
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
        val key = ChatSetting.getInstance().apiKey
        bottomBar.setEnable(false)
        cancel()
        contentLayout.addTip(Tip.WAIT).apply {
            setMouseClickListener {
                cancel()
                bottomBar.setEnable(true)
            }
        }
        println(question)
        requestDisposable = RequestUtils.request(question, key).subscribeOn(Schedulers.io()).subscribe({
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
            tipsLayout.setMouseClickListener {
                retry()
            }
            it.printStackTrace()
        })
    }

    private fun retry() {
        request(contentLayout.getLastQuestion())
    }

    private fun cancel() {
        contentLayout.removeTips(Tip.RETRY, Tip.WAIT)
        requestDisposable?.dispose()
    }

    private fun createBottomBar(): BottomBar {
        return BottomBar()
    }

    private fun createContent(): ContentLayout {
        return ContentLayout()
    }

}