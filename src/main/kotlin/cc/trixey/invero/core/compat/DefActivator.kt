package cc.trixey.invero.core.compat

/**
 * Invero
 * cc.trixey.invero.core.compat.DefActivator
 *
 * @author Arasple
 * @since 2023/2/25 16:36
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DefActivator(val names: Array<String>)