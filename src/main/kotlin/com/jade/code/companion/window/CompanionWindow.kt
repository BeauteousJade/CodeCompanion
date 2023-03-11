package com.jade.code.companion.window

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.jade.code.companion.widget.CompanionLayout
import javax.swing.JLabel

class CompanionWindow : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        val layout = CompanionLayout()

        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(layout, "", false)
        toolWindow.contentManager.addContent(content)
    }
}