package cc.trixey.invero.ui.common.panel

import cc.trixey.invero.ui.common.Panel
import cc.trixey.invero.ui.common.Pos

/**
 * Invero
 * cc.trixey.invero.ui.common.panel.FreeformPanel
 *
 * @author Arasple
 * @since 2023/1/6 14:57
 *
 * Inspired by Apple's Freeform app
 */
interface FreeformPanel : Panel {

    var viewport: Pos

    val Pos.absolute: Pos
        get() = this + viewport

    fun toAbsolutePosition(slot: Int): Pos {
        return viewport + scale.convertToPosition(slot)
    }

    fun resetViewport() = setViewport(0, 0)

    fun setViewport(x: Int = 0, y: Int = 0) {
        viewport = Pos(x, y)
    }

    fun shift(x: Int = 0, y: Int = 0) {
        viewport += (x to y)
    }

    fun shiftLeft() = shift(-1)

    fun shiftRight() = shift(1)

    fun shiftUp() = shift(y = -1)

    fun shiftDown() = shift(y = 1)

    fun shiftUpLeft() = shift(-1, -1)

    fun shiftUpRight() = shift(1, -1)

    fun shiftDownLeft() = shift(-1, 1)

    fun shiftDownRight() = shift(1, 1)

    val viewArea: List<Pos>
        get() = scale.getArea(locate).map { it + viewport }

    override fun wipe() = wipe(viewArea)

    /*
    args = absolute positions on Freeform
     */
    override fun wipe(wiping: Collection<Pos>) = wiping
        .map { it - viewport }
        .filterNot { scale.isOutOfBounds(it.x, it.y) }
        .let { super.wipe(it) }

}