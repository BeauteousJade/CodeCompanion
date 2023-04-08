package com.jade.code.companion.utils

import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import org.jetbrains.annotations.NotNull


object Utils {


    internal class OpenSettingAction : NotificationAction("open configure") {
        override fun actionPerformed(@NotNull e: AnActionEvent, @NotNull notification: Notification) {
            // IntelliJ SDK 提供的一个工具类，可以通过配置项名字，直接显示对应的配置界面
            ShowSettingsUtil.getInstance().showSettingsDialog(e.project, "CodeCompanion")
            notification.expire()
        }
    }


    fun notifyConfigKey() {
        notify("occur error", "No key configured", OpenSettingAction())
    }

    fun notify(title: String, message: String, action: NotificationAction? = null) {
        val notification = Notification("Print", title, message, NotificationType.INFORMATION)
        action?.let {
            notification.addAction(it)
        }
        Notifications.Bus.notify(notification)
    }

}