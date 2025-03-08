package cc.trixey.invero.ui.bukkit.panel

import cc.trixey.invero.ui.bukkit.PanelContainer
import cc.trixey.invero.ui.bukkit.element.item.BaseItem
import cc.trixey.invero.ui.common.Pos
import cc.trixey.invero.ui.common.Scale
import cc.trixey.invero.ui.common.panel.GeneratorPanel
import cc.trixey.invero.ui.common.panel.PanelWeight

/**
 * Invero
 * cc.trixey.invero.ui.bukkit.panel.ScrollGeneratorPanel
 *
 * @author Arasple
 * @since 2023/1/11 17:16
 */
@Deprecated("refactor required")
class ScrollGeneratorPanel<T>(
    parent: PanelContainer,
    weight: PanelWeight,
    scale: Scale,
    locate: Pos
) : ScrollStandardPanel(parent, weight, scale, locate), GeneratorPanel<T, BaseItem<*>> {

    //    override var rawElements: List<T> = emptyList()
//
//    override var currentSource: List<T> = emptyList()
//
//    override var outputGenerator: (T) -> BaseItem<*>? = { null }
//
//    override var outputElements = arrayListOf<BaseItem<*>?>()
//
//    override var generatorPool = emptyList<Pos>()
//
//    private var initialized: Boolean = false
//
//    override fun reset() {
//        outputElements.clear()
//        outputElements = ArrayList(arrayOfNulls<BaseItem<*>?>(currentSource.size).toList())
//        generatorPool = (scale.getArea() - elements.occupiedPositions()).sorted()
//        initialized = false
//        resetColums()
//        resetViewport()
//    }
//
//    private fun initialize() {
//        if (initialized) return
//
//        currentSource
//            .windowed(columCapacity, columCapacity, true)
//            .forEach { columElements ->
//                insertColum {
//                    if (it in columElements.indices) {
//                        val element = columElements[it]
//                        val index = currentSource.indexOf(element)
//
//                        getOutput(index)
//                    } else null
//                }
//            }
//
//        initialized = true
//    }
//
//
//    override fun render() {
//        initialize()
//        super<ScrollStandardPanel>.render()
//    }
    override var generatorSource: () -> List<T>
        get() = TODO("Not yet implemented")
        set(value) {}
    override var generatorOutput: (T) -> BaseItem<*>?
        get() = TODO("Not yet implemented")
        set(value) {}
    override var filter: (T) -> Boolean
        get() = TODO("Not yet implemented")
        set(value) {}
    override var comparator: Comparator<T>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun reset() {
        TODO("Not yet implemented")
    }

}