package cc.trixey.invero.ui.common.scroll

/**
 * Invero
 * cc.trixey.invero.ui.common.scroll.ScrollDirection
 *
 * @author Arasple
 * @since 2023/1/11 13:35
 */
@JvmInline
value class ScrollDirection private constructor(private val vertical: Boolean) {

    companion object {

        val VERTICAL = ScrollDirection(true)

        val HORIZONTAL = ScrollDirection(false)

    }

    val isVertical: Boolean
        get() = vertical

    val isHorizontal: Boolean
        get() = !isVertical

}