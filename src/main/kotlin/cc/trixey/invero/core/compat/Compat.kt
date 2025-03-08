package cc.trixey.invero.core.compat

import cc.trixey.invero.common.ElementGenerator
import cc.trixey.invero.common.Invero
import cc.trixey.invero.common.ItemSourceProvider
import cc.trixey.invero.common.MenuActivator
import taboolib.common.LifeCycle
import taboolib.common.inject.ClassVisitor
import taboolib.common.platform.Awake
import taboolib.library.reflex.ReflexClass

/**
 * Invero
 * cc.trixey.invero.core.compat.Compat
 *
 * @author Arasple
 * @since 2023/1/29 15:43
 */
@Awake
class Compat : ClassVisitor(0) {

    override fun visitEnd(clazz: ReflexClass) {
        val registry = Invero.API.getRegistry()

        if (clazz.hasAnnotation(DefItemProvider::class.java)) {
            val annotation = clazz.getAnnotation(DefItemProvider::class.java)
            val provider = clazz.newInstance() as ItemSourceProvider
            if (provider is PluginHook && !provider.isHooked) return

            annotation.property("namespaces", arrayListOf<String>())
                .forEach { registry.registerItemSourceProvider(it, provider) }
        } else if (clazz.hasAnnotation(DefGeneratorProvider::class.java)) {
            val annotation = clazz.getAnnotation(DefGeneratorProvider::class.java)
            val generator = clazz.newInstance() as ElementGenerator
            val namespace = annotation.property<String>("namespace") ?: "Invero"
            val id = annotation.property<String>("id")

            if (id != null) {
                registry.registerElementGenerator(namespace, id, generator)
            }
        } else if (clazz.hasAnnotation(DefActivator::class.java)) {
            runCatching {
                val annotation = clazz.getAnnotation(DefActivator::class.java)
                val activator = clazz.newInstance() as MenuActivator<*>
                annotation
                    .property("names", arrayListOf<String>())
                    .forEach { registry.registerActivator(it, activator) }
            }.getOrNull()
        }
    }

    override fun getLifeCycle() = LifeCycle.ACTIVE

}