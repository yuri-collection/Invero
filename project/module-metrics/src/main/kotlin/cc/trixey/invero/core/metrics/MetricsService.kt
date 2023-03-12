package cc.trixey.invero.core.metrics

import taboolib.common.LifeCycle
import taboolib.common.inject.ClassVisitor
import taboolib.common.platform.Awake
import taboolib.common.platform.Platform
import taboolib.library.reflex.ClassMethod
import taboolib.module.metrics.CustomChart
import taboolib.module.metrics.Metrics
import taboolib.platform.util.bukkitPlugin
import java.util.function.Supplier

/**
 * Invero
 * cc.trixey.invero.core.metrics.MetricsService
 *
 * @author Arasple
 * @since 2023/2/6 18:12
 */
@Awake
class MetricsService : ClassVisitor(0) {

    override fun visit(method: ClassMethod, clazz: Class<*>, instance: Supplier<*>?) {
        if (clazz.isAnnotationPresent(MetricsChart::class.java)) {
            val chart = clazz.getConstructor().newInstance() as CustomChart
            metrics.addCustomChart(chart)
        }
        super.visit(method, clazz, instance)
    }

    override fun getLifeCycle() = LifeCycle.ENABLE

    companion object {

        lateinit var metrics: Metrics
            private set

        @Awake(LifeCycle.LOAD)
        fun init() {
            val pluginId = 17655
            metrics = Metrics(pluginId, bukkitPlugin.description.version, Platform.BUKKIT)
        }

    }

}