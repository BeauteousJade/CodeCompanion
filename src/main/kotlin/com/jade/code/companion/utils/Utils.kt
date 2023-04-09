package com.jade.code.companion.utils

import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil
import org.jetbrains.annotations.NotNull


object Utils {


    /**
     * 通知的action，可以点击同时直达配置项。
     */
    internal class OpenSettingAction : NotificationAction("go to configure") {
        override fun actionPerformed(@NotNull e: AnActionEvent, @NotNull notification: Notification) {
            // IntelliJ SDK 提供的一个工具类，可以通过配置项名字，直接显示对应的配置界面
            ShowSettingsUtil.getInstance().showSettingsDialog(e.project, "CodeCompanion")
            notification.expire()
        }
    }


    /**
     * 发送一个配置apiKey的通知。当没有配置apiKey会触发此通知。
     */
    fun notifyConfigKey() {
        notify("CodeCompanion", "no key configured", OpenSettingAction(), NotificationType.WARNING)
    }

    /**
     * 发送一个通知。
     */
    fun notify(title: String, message: String, action: NotificationAction? = null, type: NotificationType = NotificationType.INFORMATION) {
        val notification = Notification("Print", title, message, type)
        action?.let {
            notification.addAction(it)
        }
        Notifications.Bus.notify(notification)
    }

}