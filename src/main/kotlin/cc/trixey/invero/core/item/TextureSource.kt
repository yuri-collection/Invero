package cc.trixey.invero.core.item

import cc.trixey.invero.common.Invero
import cc.trixey.invero.core.Context
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bukkit.inventory.ItemStack

/**
 * Invero
 * cc.trixey.invero.core.item.TextureSource
 *
 * @author Arasple
 * @since 2023/1/29 15:29
 */
@Serializable
class TextureSource(val source: String, @SerialName("value") override val raw: String) : Texture() {

    private val provider by lazy {
        Invero.API.getRegistry().getItemSourceProvider(source) ?: error("Invalid source provider: $source")
    }

    @Transient
    override val lazyTexture: ItemStack? = run {
        if (containsPlaceholder || !provider.translateIdentifier()) null
        else provider.getItem(raw, null)
    }

    override fun generateItem(context: Context, block: (ItemStack) -> Unit) {
        block(lazyTexture ?: provider.getItem(context.parse(raw), context) ?: DEFAULT_TEXTURE)
    }

    override fun toString(): String {
        return super.toString() + " (${provider.javaClass.simpleName.removeSuffix("ItemProvider")})"
    }

    override fun clone() = TextureSource(source, raw)

}