package cc.trixey.invero.ui.common

/**
 * Invero
 * cc.trixey.invero.ui.common.Window
 *
 * @author Arasple
 * @since 2022/12/20 20:04
 */
interface Window : Gridable {

    val type: ContainerType

    var title: String

    val viewer: Viewer

    val inventory: ProxyInventory

    val overridePlayerInventory: Boolean

    val hidePlayerInventory: Boolean

    fun open()

    fun render()

    fun close(doCloseInventory: Boolean = true, updateInventory: Boolean = true)

    fun isViewing(): Boolean

}