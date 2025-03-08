package cc.trixey.invero.common.chemdah

import taboolib.platform.type.BukkitProxyEvent

/**
 * Chemdah
 * cc.trixey.invero.common.chemdah.InferItemHookEvent
 *
 * @author sky
 * @since 2021/4/17 2:41 下午
 */
class InferItemHookEvent(val id: String, var itemClass: Class<out InferItem.Item>) : BukkitProxyEvent() {

    override val allowCancelled: Boolean
        get() = false

}