package com.jade.code.companion.chat.setting

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.jade.code.companion.utils.safelyToInt
import java.awt.GridLayout
import java.awt.event.ItemEvent
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


class ChatSettingPanel : JBPanel<ChatSettingPanel>() {

    private val keyTextField = JBTextField()
    private val hostNameTextField = JBTextField()
    private val portTextField = JBTextField()
    private val enableProxyButton = JRadioButton("是否配置代理")

    init {
        layout = GridLayout(15, 1)
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(JBLabel("apiKey:"))
            add(keyTextField)
        })
        add(JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            enableProxyButton.addItemListener {
                val isSelect = it.stateChange == ItemEvent.SELECTED
                hostNameTextField.isEnabled = isSelect
                portTextField.isEnabled = isSelect
            }
            enableProxyButton.isSelected = false
            hostNameTextField.isEnabled = false
            portTextField.isEnabled = false
            add(enableProxyButton)
            add(Box.createVerticalGlue())
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

    fun getConfig(): Triple<String, String, Int> {
        return Triple(keyTextField.text.trim(), hostNameTextField.text.trim(), portTextField.text.trim().safelyToInt())
    }

    fun setConfig(config: Triple<String, String, Int>) {
        keyTextField.text = config.first.trim()
        hostNameTextField.text = config.second.trim()
        portTextField.text = config.third.takeIf {
            it > 0
        }?.toString() ?: ""

        val isNotEmpty = hostNameTextField.text.isNotEmpty() || portTextField.text.isNotEmpty()
        enableProxyButton.isSelected = isNotEmpty
        hostNameTextField.isEnabled = isNotEmpty
        portTextField.isEnabled = isNotEmpty
    }

}