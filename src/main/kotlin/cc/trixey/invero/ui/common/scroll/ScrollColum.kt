package cc.trixey.invero.ui.common.scroll

import cc.trixey.invero.ui.common.Element

/**
 * Invero
 * cc.trixey.invero.ui.common.scroll.ScrollColum
 *
 * @author Arasple
 * @since 2023/1/7 21:42
 */
abstract class ScrollColum<T : Element>(val elements: Array<T?>) {

    operator fun set(index: Int, element: T?) {
        elements[index] = element
    }

    operator fun get(index: Int): T? {
        return elements[index]
    }

}