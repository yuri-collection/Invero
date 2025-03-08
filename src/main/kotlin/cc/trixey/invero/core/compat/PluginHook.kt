package cc.trixey.invero.core.compat

import org.bukkit.Bukkit
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang

/**
 * Invero
 * cc.trixey.invero.core.compat.PluginHook
 *
 * @author Arasple
 * @since 2023/1/31 20:39
 */
abstract class PluginHook {

    open val pluginName: String? = null

    val isHooked: Boolean by lazy {
        if (pluginName != null && Bukkit.getPluginManager().getPlugin(pluginName!!) != null) {
            console().sendLang("plugin-hooked", pluginName!!)
            true
        } else false
    }

}