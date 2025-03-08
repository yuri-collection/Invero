package cc.trixey.invero.ui.common.panel

import cc.trixey.invero.ui.common.Pos

/**
 * Invero
 * cc.trixey.invero.ui.common.panel.GeneratorPanel
 *
 * @author Arasple
 * @since 2023/1/11 15:56
 */
interface GeneratorPanel<T, R : Any> : ElementalPanel {

    var generatorSource: () -> List<T>

    var generatorOutput: (T) -> R?

    var filter: (T) -> Boolean

    var comparator: Comparator<T>

    /**
     * 指定元素集合产生器
     */
    fun generatorSource(block: () -> List<T>) {
        generatorSource = block
    }

    /**
     * 指定输出物品产生器
     */
    fun generatorOutput(block: (T) -> R?) {
        generatorOutput = block
    }

    /**
     * 指定排序比较器
     */
    fun sortWith(block: Comparator<T>) {
        this.comparator = block
    }

    /**
     * 指定过滤器
     */
    fun filterBy(block: (T) -> Boolean) {
        filter = block
    }

    /**
     * 取得有效的生成器区域
     */
    fun elementsPool(): List<Pos> {
        return (scale.getArea() - elements.occupiedPositions()).sorted()
    }

    /*
    重置缓存（二次修改过滤器/排序后需调用）
     */
    fun reset()

}