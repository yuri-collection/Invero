package cc.trixey.invero.core.metrics

import cc.trixey.invero.common.Invero
import cc.trixey.invero.core.AgentPanel
import cc.trixey.invero.core.BaseMenu
import cc.trixey.invero.core.icon.Icon
import cc.trixey.invero.core.panel.IconContainer
import taboolib.module.metrics.charts.AdvancedPie
import taboolib.module.metrics.charts.SingleLineChart
import java.util.concurrent.Callable

/**
 * Invero
 * cc.trixey.invero.core.metrics.Charts
 *
 * @author Arasple
 * @since 2023/2/6 18:07
 */
private var statisticMenuOpenCounts: Int = 0
    get() {
        val count = field
        field = 0
        return count
    }

private val menus: List<BaseMenu>
    get() = Invero.API.getMenuManager().getMenus().map { it as BaseMenu }

private val panels: List<AgentPanel>
    get() = menus.flatMap { it.panels }

private val icons: List<Icon>
    get() = panels.flatMap {
        if (it is IconContainer) it.icons.values
        else emptyList()
    }

@MetricsChart
internal class ChartMenus : SingleLineChart("menus", Callable {
    menus.size
})

@MetricsChart
internal class ChartPanels : SingleLineChart("panels", Callable {
    panels.size
})

@MetricsChart
internal class ChartIcons : SingleLineChart("icons", Callable {
    icons.sumOf { 1 + (it.subIcons?.size ?: 0) }
})

@MetricsChart
internal class ChartContainerType : AdvancedPie("container_type", Callable {
    buildMap<String, Int> {
        menus.forEach {
            val type = it.settings.containerType.name
            put(type, computeIfAbsent(type) { 0 } + 1)
        }
    }
})

@MetricsChart
internal class ChartPanelType : AdvancedPie("panel_type", Callable {
    buildMap<String, Int> {
        panels.forEach {
            increase(it.javaClass.simpleName.removePrefix("Panel"))
        }
    }
})

@MetricsChart
internal class ChartVirtualWindow : AdvancedPie("virtual_window", Callable {
    buildMap<String, Int> {
        menus.forEach {
            val tag = if (it.settings.virtual) "Enabled" else "Disabled"
            put(tag, computeIfAbsent(tag) { 0 } + 1)
        }
    }
})

@MetricsChart
internal class ChartIconTexture : AdvancedPie("icon_texture", Callable {
    buildMap<String, Int> {
        icons.forEach {
            it.collectTextures(this)
            it.subIcons?.forEach { sub -> sub.collectTextures(this) }
        }
    }
})

@MetricsChart
internal class ChartIconFramed : AdvancedPie("icon_framed", Callable {
    buildMap<String, Int> {
        icons.forEach {
            increase(if (it.frames != null) "Enabled" else "Disabled")
        }
    }
})

private fun Icon.collectTextures(result: MutableMap<String, Int>) {
    defaultFrame.texture?.toString()?.let { result.increase(it) }
    frames?.forEach { it.texture?.toString()?.let { result.increase(it) } }
}

private fun MutableMap<String, Int>.increase(name: String, by: Int = 1) {
    put(name, computeIfAbsent(name) { 0 } + by)
}