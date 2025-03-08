package cc.trixey.invero.core.util

/**
 * Invero
 * cc.trixey.invero.core.util.Regex
 *
 * @author Arasple
 * @since 2023/1/15 22:37
 */
val MATERIAL_ID = Regex("([0-9]+):?([0-9]+)?")

val PLACEHOLDER_ANY = Regex("(%|\\{\\{)(.+)(%|}})")

val SPECIAL_GROUP = "`(.+?)`".toPattern()

val String.containsAnyPlaceholder: Boolean
    get() = contains(PLACEHOLDER_ANY)

inline val Any?.bool: Boolean
    get() = this.bool()

fun Any?.bool(preference: Boolean = true, ignoreCases: Boolean = false): Boolean {
    return if (this is Boolean) this
    else toString().let { raw ->
        val it = if (ignoreCases) raw.lowercase() else raw

        if (preference) it != "false" && it != "no"
        else it == "true" || it == "yes"
    }
}