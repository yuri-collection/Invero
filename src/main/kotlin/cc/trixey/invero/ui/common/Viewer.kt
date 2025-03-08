package cc.trixey.invero.ui.common

/**
 * Invero
 * cc.trixey.invero.ui.common.Viewer
 *
 * @author Arasple
 * @since 2022/12/20 20:35
 */
interface Viewer {

    val name: String

    fun isAvailable(): Boolean

    fun <T> get(): T?

    fun <T : Viewer> cast(): T {
        @Suppress("UNCHECKED_CAST")
        return this as T
    }
}