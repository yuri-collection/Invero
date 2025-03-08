package cc.trixey.invero.ui.common.panel

import cc.trixey.invero.ui.common.Element
import cc.trixey.invero.ui.common.Elements
import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.Pos

/**
 * Invero
 * cc.trixey.invero.ui.common.panel.ElementalPanel
 *
 * @author Arasple
 * @since 2022/12/30 12:39
 */
interface ElementalPanel : Panel {

    val elements: Elements

    fun getUnoccupiedPositions(): Set<Pos> {
        return scale.getArea() - elements.occupiedPositions()
    }

    override fun render() {
        elements.forEach { push() }
    }

    override fun isElementValid(element: Element): Boolean {
        return elements.hasElement(element)
    }

}