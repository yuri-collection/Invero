package cc.trixey.invero.ui.common

import java.util.concurrent.ConcurrentHashMap

/**
 * Invero
 * cc.trixey.invero.ui.common.Elements
 *
 * @author Arasple
 * @since 2022/12/29 13:51
 */
@JvmInline
value class Elements(val value: ConcurrentHashMap<Element, Positions> = ConcurrentHashMap()) {

    fun occupiedPositions(): Set<Pos> {
        return value.values.flatMap { it.values }.toSet()
    }

    fun forEach(block: Element.() -> Unit) {
        value.forEach { it.key.block() }
    }

    fun locateElement(element: Element): Positions? {
        return value[element]
    }

    fun findElement(pos: Pos): Element? {
        return value.entries.firstOrNull { pos in it.value }?.key
    }

    fun removeElement(element: Element) = value.remove(element)

    fun removeElement(pos: Pos) = findElement(pos)?.let { removeElement(it) }

    fun addElement(element: Element, pos: Pos) {
        value.computeIfAbsent(element) { Positions() } += pos
    }

    fun setElement(element: Element, positions: Positions): Set<Pos>? {
        val removable = locateElement(element)?.minus(positions)
        value[element] = positions
        return removable
    }

    fun hasElement(element: Element): Boolean {
        return value.containsKey(element)
    }

}