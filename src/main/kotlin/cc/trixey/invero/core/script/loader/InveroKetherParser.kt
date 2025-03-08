package cc.trixey.invero.core.script.loader

/**
 * Invero
 * cc.trixey.invero.core.script.loader.InveroKetherParser
 *
 * @author Arasple
 * @since 2023/3/3 19:16
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class InveroKetherParser(
    val name: Array<String>,
    val tags: Array<String> = []
)