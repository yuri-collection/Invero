package cc.trixey.invero.ui.bukkit.element

import cc.trixey.invero.ui.common.event.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Invero
 * cc.trixey.invero.ui.common.Clickable
 *
 * @author Arasple
 * @since 2023/1/5 14:27
 */
interface Clickable<T> {

    /* 点击回调 */
    val clickCallback: CopyOnWriteArrayList<T.(ClickType, InventoryClickEvent?) -> Unit>

    /* 增加点击回调动作 */
    fun onClick(handler: T.(ClickType, InventoryClickEvent?) -> Unit): T {
        clickCallback += handler
        return getInstance()
    }

    /* 增加点击回调动作 */
    fun click(handler: (T) -> Unit): T {
        clickCallback += { _, _ -> handler(this) }
        return getInstance()
    }

    /* 调用 */
    fun runClickCallbacks(clickType: ClickType, e: InventoryClickEvent?) {
        clickCallback.forEach { it(getInstance(), clickType, e) }
    }

    /* 返回对象 */
    fun getInstance(): T

}