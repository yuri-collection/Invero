package cc.trixey.invero.ui.common.scroll

/**
 * Invero
 * cc.trixey.invero.ui.common.scroll.ScrollTail
 *
 * @author Arasple
 * @since 2023/1/11 13:38
 *
 * tailSize = 0, scroll stop at blank panel
 * tailSize < 0, scroll loop
 * tailSize > 0, scroll stop when visible colums size <= tailSize
 */
@JvmInline
value class ScrollTail(val size: Int = 0) {

    val isLoop: Boolean
        get() = size < 0

}