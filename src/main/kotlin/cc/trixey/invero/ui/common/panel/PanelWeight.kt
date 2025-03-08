package cc.trixey.invero.ui.common.panel

/**
 * Invero
 * cc.trixey.invero.ui.common.panel.PanelWeight
 *
 * @author Arasple
 * @since 2022/12/22 14:52
 */
@JvmInline
value class PanelWeight(private val value: Int) : Comparable<PanelWeight> {

    companion object {

        val BACKGROUND = PanelWeight(Int.MIN_VALUE)

        val LOWEST = PanelWeight(-10)

        val LOW = PanelWeight(-1)

        val NORMAL = PanelWeight(0)

        val HIGH = PanelWeight(1)

        val HIGHEST = PanelWeight(10)

        val SURFACE = PanelWeight(Int.MAX_VALUE)

    }

    override fun compareTo(other: PanelWeight): Int {
        return value.compareTo(other.value)
    }

}