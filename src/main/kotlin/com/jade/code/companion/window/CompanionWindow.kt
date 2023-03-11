package com.jade.code.companion.window

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.JLabel

class CompanionWindow : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        val label = JLabel();
        label.text = "123";

        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(label, "", false)
        toolWindow.contentManager.addContent(content)
    }
}