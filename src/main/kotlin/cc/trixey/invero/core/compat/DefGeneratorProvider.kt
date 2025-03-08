package cc.trixey.invero.core.compat

/**
 * Invero
 * cc.trixey.invero.core.compat.DefGeneratorProvider
 *
 * @author Arasple
 * @since 2023/2/24 15:47
 *
 * Please use Invero API to register your own GeneratorProvider
 * This is for internal hook ONLY
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DefGeneratorProvider(
    val id: String,
    val namespace: String = "Invero"
)