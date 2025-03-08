package cc.trixey.invero.common

/**
 * Invero
 * cc.trixey.invero.common.ElementGenerator
 *
 * @author Arasple
 * @since 2023/2/1 16:45
 */
interface ElementGenerator {

    var generated: List<Object>?

    fun generate(context: Any? = null)

    fun filter(block: (Object) -> Boolean): ElementGenerator

    fun <R : Comparable<R>> sortBy(block: (Object) -> R): ElementGenerator

}