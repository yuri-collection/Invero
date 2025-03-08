package cc.trixey.invero.core.serialize.hocon

import taboolib.library.reflex.Reflex.Companion.setProperty
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File

/**
 * Invero
 * cc.trixey.invero.serialize.hocon.PatchedLoader
 *
 * @author Arasple
 * @since 2023/1/14 21:05
 */
object PatchedLoader {

    fun loadFromFile(file: File, destinationType: Type = Type.JSON): Configuration {
        val type = Configuration.getTypeFromExtension(file.extension)

        return if (type != Type.HOCON) {
            Configuration.loadFromFile(file)
        } else {
            val content = PatchIncluder.patchHoconFile(file)
            val config = Configuration.loadFromString(content, type)
            config.file = file
            config
        }.also {
            it.setProperty("name", file.nameWithoutExtension)
            if (destinationType != type) it.changeType(destinationType)
        }
    }

}