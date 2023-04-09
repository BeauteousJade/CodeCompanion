package com.jade.code.companion.chat.setting

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.jade.code.companion.utils.safelyToInt
import java.awt.GridLayout
import java.awt.event.ItemEvent
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


/**
 * chagpt配置项的布局。
 */
class ChatSettingPanel : JBPanel<ChatSettingPanel>() {

    // apiKey的输入框
    private val keyTextField = JBTextField()

    // 代理服务器地址的输入框
    private val hostNameTextField = JBTextField()

    // 代理服务器端口的输入框
    private val portTextField = JBTextField()
    private val noneProxyButton = JRadioButton("关闭代理")
    private val httpProxyButton = JRadioButton("http代理")
    private val socketProxyButton = JRadioButton("socket代理")
    private var proxyType: Int = ChatSetting.PROXY_TYPE_NONE

    private val proxyButtonMap = mapOf(Pair(ChatSetting.PROXY_TYPE_NONE, noneProxyButton), Pair(ChatSetting.PROXY_TYPE_HTTP, httpProxyButton), Pair(ChatSetting.PROXY_TYPE_SOCKET, socketProxyButton))

    private val buttonGroup = ButtonGroup().apply {
        add(noneProxyButton)
        add(httpProxyButton)
        add(socketProxyButton)
    }

    init {
        layout = GridLayout(15, 1)
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(JBLabel("apiKey:"))
            add(keyTextField)
        })
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            noneProxyButton.addItemListener {
                updateProxyType(it, ChatSetting.PROXY_TYPE_NONE)
            }
            httpProxyButton.addItemListener {
                updateProxyType(it, ChatSetting.PROXY_TYPE_HTTP)
            }
            socketProxyButton.addItemListener {
                updateProxyType(it, ChatSetting.PROXY_TYPE_SOCKET)
            }

            add(noneProxyButton)
            add(Box.createHorizontalStrut(5))
            add(httpProxyButton)
            add(Box.createHorizontalStrut(5))
            add(socketProxyButton)
        })

        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(JBLabel("代理服务器地址:"))
            add(hostNameTextField)
        })

        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(JBLabel("代理服务器端口:"))
            add(portTextField)
        })
        add(Box.createVerticalBox())
    }

    private fun updateProxyType(e: ItemEvent, targetType: Int) {
        val isSelect = e.stateChange == ItemEvent.SELECTED
        if (isSelect) {
            proxyType = targetType
        }
        hostNameTextField.isEnabled = proxyType != ChatSetting.PROXY_TYPE_NONE
        portTextField.isEnabled = proxyType != ChatSetting.PROXY_TYPE_NONE

        // 触发更新，保证apply按钮能够点击
        val oldText = portTextField.text
        portTextField.text = ""
        portTextField.text = oldText
    }

    /**
     * 添加配置项的更新监听。
     */
    fun addUpdateListener(runBlock: () -> Unit) {
        val updateList = object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent?) {
                runBlock()
            }

            override fun removeUpdate(e: DocumentEvent?) {
                runBlock()
            }

            override fun changedUpdate(e: DocumentEvent?) {
                runBlock()
            }

        }
        keyTextField.document.addDocumentListener(updateList)
        hostNameTextField.document.addDocumentListener(updateList)
        portTextField.document.addDocumentListener(updateList)
    }

    /**
     * 获取当前的所有配置
     */
    fun getConfig(): ChatSettingConfig {
        return ChatSettingConfig(keyTextField.text.trim(), hostNameTextField.text.trim(), portTextField.text.trim().safelyToInt(), proxyType)
    }

    /**
     * 更新当前的所有配置
     */
    fun setConfig(config: ChatSettingConfig) {
        keyTextField.text = config.apiKey.trim()
        hostNameTextField.text = config.hostName.trim()
        portTextField.text = config.port.takeIf {
            it > 0
        }?.toString() ?: ""
        proxyButtonMap[config.proxyType]?.isSelected = true
    }

}