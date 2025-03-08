package cc.trixey.invero.core.script.loader

import cc.trixey.invero.common.api.InveroSettings
import taboolib.common.LifeCycle
import taboolib.common.inject.ClassVisitor
import taboolib.common.platform.Awake
import taboolib.library.reflex.ClassMethod
import taboolib.library.reflex.ReflexClass
import taboolib.module.kether.KetherLoader.Companion.registerParser
import taboolib.module.kether.ScriptActionParser

/**
 * Invero
 * cc.trixey.invero.core.script.kether.loader.KetherInveroLoader
 *
 * @author Arasple
 * @since 2023/3/3 18:59
 */
@Awake(LifeCycle.LOAD)
class KetherInveroLoader : ClassVisitor(0) {

    override fun visit(method: ClassMethod, owner: ReflexClass) {
        if (method.isAnnotationPresent(InveroKetherParser::class.java) && method.returnType == ScriptActionParser::class.java) {
            val instance = findInstance(owner)
            val parser = (if (instance == null) method.invokeStatic() else method.invoke(instance))
            val annotation = method.getAnnotation(InveroKetherParser::class.java)
            val value = annotation.property("name", arrayListOf<String>())
            val tags = annotation.property("tags", arrayListOf<String>())

            // Skip the auto placeholder translation
            if (tags.contains("kether-ext") && !InveroSettings.autoPlaceholderTranslate) {
                return
            }

            registerParser(parser as ScriptActionParser<*>, value.toTypedArray(), "invero", true)
        }
    }

    override fun getLifeCycle(): LifeCycle {
        return LifeCycle.LOAD
    }
}