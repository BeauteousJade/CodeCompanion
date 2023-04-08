package com.jade.code.companion.chat.widget

import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import java.awt.Component

class JadeJBScrollPane : JBScrollPane {

    constructor(view: Component?, vsbPolicy: Int, hsbPolicy: Int) : super(view, vsbPolicy, hsbPolicy)

    override fun updateUI() {
        border = JBUI.Borders.empty()
        super.updateUI()
    }

    override fun setCorner(key: String?, corner: Component?) {
        border = JBUI.Borders.empty()
        super.setCorner(key, corner)
    }

}