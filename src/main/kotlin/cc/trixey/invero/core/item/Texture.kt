package cc.trixey.invero.core.item

import cc.trixey.invero.core.Context
import cc.trixey.invero.core.serialize.SelectorTexture
import cc.trixey.invero.core.util.containsAnyPlaceholder
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial

/**
 * Invero
 * cc.trixey.invero.core.item.Texture
 *
 * @author Arasple
 * @since 2023/1/16 10:29
 */
@Serializable(with = SelectorTexture::class)
abstract class Texture : Cloneable {

    abstract val raw: String

    protected val containsPlaceholder by lazy { raw.containsAnyPlaceholder }

    abstract val lazyTexture: ItemStack?

    abstract fun generateItem(context: Context, block: ItemStack.() -> Unit = {})

    fun isStatic(): Boolean {
        return lazyTexture != null
    }

    override fun toString(): String {
        return javaClass.simpleName.removePrefix("Texture")
    }

    public override fun clone(): Any {
        return super.clone()
    }

    companion object {

        val DEFAULT_TEXTURE = XMaterial.BEDROCK.parseItem()!!
            get() = field.clone()

    }

}