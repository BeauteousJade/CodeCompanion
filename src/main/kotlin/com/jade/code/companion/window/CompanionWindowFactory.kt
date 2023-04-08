package com.jade.code.companion.window

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class CompanionWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        val layout = CompanionWindow()

        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(layout, "", false)
        toolWindow.contentManager.addContent(content)
    }
}