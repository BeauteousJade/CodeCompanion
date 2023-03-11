package com.jade.code.companion.widget

import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.components.Label
import java.awt.Color
import javax.swing.JPanel

class CompanionLayout : JPanel {

    constructor() : super() {
        background = Color.BLUE


        val tabbedPane = JBTabbedPane()
        tabbedPane.add("main1", JPanel().apply {
            background = Color.CYAN
            add(Label("main1").apply {
                setBounds(0, 0, 200, 200)
            })
        })
        tabbedPane.add("main2", JPanel().apply {
            background = Color.RED
            add(Label("main2").apply {
                setBounds(0, 0, 200, 200)
            })
        })
        tabbedPane.add("main3", JPanel().apply {
            background = Color.GREEN
            add(Label("main3").apply {
                setBounds(0, 0, 200, 200)
            })
        })
        tabbedPane.setBounds(0, 0, 400, 200)

        tabbedPane.background = Color.MAGENTA

        add(tabbedPane)
    }
}