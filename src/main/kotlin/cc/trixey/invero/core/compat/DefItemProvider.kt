package cc.trixey.invero.core.compat

/**
 * Invero
 * cc.trixey.invero.core.compat.DefItemProvider
 *
 * @author Arasple
 * @since 2023/2/7 21:38
 *
 * Please use Invero API to register your own ItemProvider
 * This is for internal hook ONLY
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DefItemProvider(
    val namespaces: Array<String>
)