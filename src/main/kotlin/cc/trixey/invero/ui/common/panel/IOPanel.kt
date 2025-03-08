package cc.trixey.invero.ui.common.panel

import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.ProxyInventory

/**
 * Invero
 * cc.trixey.invero.ui.common.panel.IOPanel
 *
 * @author Arasple
 * @since 2023/1/11 17:46
 *
 * Support Input/Output ItemStack
 */
interface IOPanel : ElementalPanel {

    val freeSlots: MutableSet<Int>

    val inventory: ProxyInventory
        get() = window.inventory

    var callback: () -> Unit

    fun listener(block: () -> Unit) {
        callback = block
    }

    fun runCallback() = callback()

    fun delete(pos: Pos) = delete(pos.slot)

    fun lock(pos: Pos) = lock(pos.slot)

    fun free(pos: Pos) = free(pos.slot)

    fun delete(slot: Int)


    fun lock(slot: Int) {
        if (slot < 0) freeSlots.clear()
        else freeSlots -= slot
    }

    fun free(slot: Int) {
        freeSlots += slot
    }

    fun renderStorage()

    override fun render() {
        super.render()
        renderStorage()
    }

}